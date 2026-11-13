---
name: compose-architecture
description: Design, implement, or review Jetpack Compose and Compose Multiplatform app architecture, including screen state, user actions, one-shot UI events, and side effects. Use for Compose app screens, flows, and features; not for non-Compose apps, websites, or reusable Compose library primitives.
---

# Compose Architecture

Build each screen from three parts. Use the codebase's names when it already has a clear convention.

- `State`: what the screen shows.
- `Action`: what the user did.
- `UiEvent`: one-shot output, such as navigation or a snackbar.

## ViewModel

Each screen's state holder is a `ViewModel` named after the screen, such as `SignupViewModel`. In Compose Multiplatform, use `org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose`.

- Create the ViewModel inside the screen composable with `viewModel { }`. Never pass a ViewModel into a composable as a parameter.
- Create the ViewModel's dependencies inside that factory lambda, at the moment the ViewModel is created. Don't create them earlier or pass them through the screen.
- Run its work in `viewModelScope`.

```kotlin
@Composable
fun SignupScreen(exit: () -> Unit) {
    val viewModel = viewModel { SignupViewModel(api = NewsletterApi()) }
    val state by viewModel.state.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                SignupUiEvent.Exit -> exit()
            }
        }
    }
    SignupContent(state, viewModel::onAction)
}
```

## Boundaries

- Composables render state and send actions through callbacks.
- A screen's navigation parameters name where the user goes, such as `exit`, `navigateBack`, or `navigateToSettings`. Never name them after the UI that triggered them, such as `onClose` or `onDoneClick`.
- The ViewModel makes decisions, changes state, and calls external operations. Never trigger these from recomposition. Use Compose effect APIs only for work whose lifetime belongs to the composition.
- State holds typed outcomes, not UI strings. Composables choose the text and controls.
- Actions describe intent. Keep widgets, storage, transport, and async details out of them.

## One Sealed State

A screen must never show UI from two states at once, such as a success check next to an error message.

- Model mutually exclusive outcomes as one sealed state, not as flags or nullable fields that drift apart.
- Replace the whole state on each transition. Never patch a single field.
- Render with an exhaustive `when`, so every state decides every piece of UI: what it shows and what it hides.
- Scope an outcome to what it describes, such as one item, one dialog, or one request. Clear it when that item changes, the dialog closes, or the action is retried.
- If an outcome only needs to be seen once, emit a `UiEvent` instead of keeping it in state.
- Keep UI-owned input that several states show, such as a `TextFieldState`, above the `when` and start it from the current state. Inside a branch it resets whenever the state changes.
- Ignore actions that change nothing, such as the same email again. A rebuilt screen reports its starting input, and that must not clear an outcome.

## Tests

Unit tests construct the ViewModel directly with fake dependencies, assert state, and cover every transition, including the ones that clear a previous outcome. `viewModelScope` runs on `Dispatchers.Main`, so tests replace it with `Dispatchers.setMain(StandardTestDispatcher())` and reset it afterwards. Check rendering and interaction separately with the project's UI tooling.

## Defaults

- Keep screen state private to the screen unless it is genuinely shared.
- Prefer sealed types or plain classes over enums, unless callers need to list every option.
