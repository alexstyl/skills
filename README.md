![A coding agent building a Compose screen with a ViewModel and a theme]()

_Demo idea: ask an agent for a sign-up screen and show the finished screen moving through its editing, error and success states on an Android emulator._

# Skills

Agent skills that make coding agents build Jetpack Compose and Compose Multiplatform screens the way an experienced Compose developer would. They work with Claude Code, Codex, Cursor and any agent that reads `SKILL.md` files.

## Features

- **Screens that can't contradict themselves.** Each screen has one sealed state rendered with an exhaustive `when`, so an error and a success check never show up together.
- **ViewModels done right.** The screen creates its own `ViewModel`, the ViewModel creates its own dependencies, and navigation callbacks are named after where they go, such as `exit` or `navigateToSettings`.
- **Tested without a UI.** Every state change is covered by plain unit tests against the ViewModel.
- **A theme layer in minutes.** Typed color, typography and shape tokens, `Text` and `Icon` wrappers that follow the content color, and a `ScreenScaffold` that paints the background and sets the system bar icons.
- **Built on small, focused libraries.** Ripple feedback, Google Fonts and system bar icon colors through Composable Horizons libraries, with no Material dependency.

## Installation

Install the skills with the [skills CLI](https://skills.sh) for the coding agents in your project:

```sh
bunx skills add alexstyl/skills
```

Install only the ones you want by naming them with `--skill`:

```sh
bunx skills add alexstyl/skills --skill compose-architecture
```

Add `-g` to install them for every project instead of the current one. Run the command again to update.

You can also copy a skill's folder from `skills/` into your agent's skills directory. Copy the whole folder, including `assets/` and `agents/`, not just `SKILL.md`.

## Quick start

Build a new screen by asking your agent to use `$compose-architecture`:

```text
Use $compose-architecture to build a newsletter sign-up screen that validates the email and shows
an error when the address is already subscribed.
```

The agent writes a `SignupViewModel` with one sealed state, a screen that creates the ViewModel itself and renders every state, and unit tests for each state change.

## Skills

| Skill | What it does |
| --- | --- |
| [compose-architecture](skills/compose-architecture/SKILL.md) | Structures screens as state, user actions and one-shot events, with a screen-owned `ViewModel` and one sealed state rendered exhaustively. |
| [compose-theme](skills/compose-theme/SKILL.md) | Builds a theme layer with typed tokens, `Text` and `Icon` wrappers and a `ScreenScaffold`, from the bundled Kotlin templates. |

## Examples

### Add a ViewModel to an existing screen

Move a screen's logic out of its composables by asking for `$compose-architecture` on that screen:

```text
Use $compose-architecture to move the logic in SettingsScreen into a ViewModel.
```

The agent keeps your names and conventions, models the screen's outcomes as one sealed state, and replaces flags such as `isLoading` and `error` that can disagree with each other.

### Create a theme for a Compose app

Give your app shared colors, text styles and a screen root by asking for `$compose-theme` with your design:

```text
Use $compose-theme to set up our theme from this design: white background, #1F6FEB primary,
Inter for all text.
```

The agent adds `Theme.kt`, the `Text` wrapper and `ScreenScaffold` to your project, applies `Theme` around your app, and loads Inter through Google Fonts. Without a design, it keeps your existing values or uses neutral defaults.

### Draw a Compose app edge to edge

Make screens draw behind the status and navigation bars by asking for `$compose-theme`:

```text
Use $compose-theme to make our screens edge to edge with the right status bar icon colors.
```

Each screen gets a `ScreenScaffold` that paints the background behind the bars and picks dark or light system bar icons for it. Lists get inset content padding so their first and last items stay reachable.
