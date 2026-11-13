---
name: compose-theme
description: Create or adapt a Jetpack Compose or Compose Multiplatform theme with typed tokens, a Theme object, CompositionLocals, themed Text and Icon wrappers, and a ScreenScaffold. Use when establishing or refactoring the shared theme layer, including ripple, custom fonts, and system bar appearance; not for routine screen changes that already have a suitable theme.
---

# Compose Theme

Build a small theme layer from the bundled templates, in the project's own packages and source sets (`commonMain` for shared code).

Read the existing theme and app host first. Adapt a compatible theme instead of adding a second set of CompositionLocals. Take values from the design, explicit instructions, or the existing UI. Without a design, keep existing values or the neutral starter values. Never add branding or custom fonts on your own.

## Files

- [assets/Theme.kt](assets/Theme.kt): color, typography, and shape tokens, their CompositionLocals, spacing, the `Theme` object and provider, `ProvideContentColor`, `ProvideTextStyle`, ripple, and fonts.
- [assets/Text.kt](assets/Text.kt) and [assets/Icon.kt](assets/Icon.kt): theme-aware wrappers in a sibling `components` package.
- [assets/ScreenScaffold.kt](assets/ScreenScaffold.kt): the screen root.

Replace the `theme.template.*` packages. Keep everything theme-related in `Theme.kt` and each component in its own file. Keep this access pattern:

```kotlin
Theme.colors.surface
Theme.typography.headline
Theme.shapes.medium
Space * 4
```

## Tokens

- Pair every background with its content color: `background`/`onBackground`, `surface`/`onSurface`, `primary`/`onPrimary`. Two roles with the same value are still different roles.
- Add only the tokens the design uses. Remove unused starter colors, text styles, and shapes, and skip `Icon.kt` if nothing shows icons.
- Leave text-style colors unspecified so content colors can recolor text.
- `Text` uses its explicit color, then its style's color, then the inherited content color. `Icon` inherits the content color unless tinted; pass `null` as the description for decorative icons.

## Libraries

Keep versions the project already uses.

- **[Ripple Indication](https://github.com/composablehorizons/compose-ripple-indication)** (`com.composables:ripple-indication`): `Theme` provides it through `LocalIndication`. A control with its own `indication` parameter must be passed `LocalIndication.current`. Clip it to the control's shape and share the control's interaction source.
- **[Google Fonts](https://github.com/composablehorizons/google-fonts)** (`com.composables:google-fonts`): only when a custom font is requested. Use `rememberThemeTypography(familyName = …)` and request only the weights you use. The library handles loading and fallback. Without a requested font, drop the dependency and keep the system font.
- **[System Bars](https://github.com/composablehorizons/systembars)** (`com.composables:systembars`): `SystemBarContent.Dark` means dark icons, `Light` means light icons. Pick them for the surface behind each bar. On Android, enable edge-to-edge in the Activity. On iOS, host the app with `SystemBarsViewController { App() }`. On desktop and web it does nothing.

## ScreenScaffold

Apply `Theme` once, above the screens, usually around the app or navigation host. `ScreenScaffold` is each screen's root and never reapplies the theme or takes theme settings.

It fills the screen, paints `Theme.colors.background`, provides `Theme.colors.onBackground` as the content color, and sets the system bar icons for that background. Everything else stays inherited.

Content draws behind the system bars:

- Never pad the whole scaffold or a whole scrolling list with safe insets.
- Pad fixed controls with the safe insets they need.
- Give lazy lists and grids inset content padding, so the first and last items are reachable while content scrolls behind the bars. When bars overlay content, include their full height.
- Count keyboard and cutout insets once.

## Finish

Compile the affected targets. Check that the scaffold doesn't reinstall the theme, that every background provides its content color, that controls use the ripple, and that no unused tokens or dependencies remain. Don't build a component library or restyle unrelated screens.
