# Skills

This repository contains Alex Styl's personal skills for building Compose apps.

| Skill | What it does |
| --- | --- |
| [compose-architecture](skills/compose-architecture/SKILL.md) | Structures screens as state, user actions and one-shot events, with a screen-owned `ViewModel` and one sealed state rendered exhaustively. |
| [compose-theme](skills/compose-theme/SKILL.md) | Builds a theme layer with typed tokens, `Text` and `Icon` wrappers and a `ScreenScaffold`, from the bundled Kotlin templates. |

## Installation

Install the skills with the [skills CLI](https://skills.sh):

```sh
bunx skills add alexstyl/skills
```

Install only the ones you want by naming them with `--skill`, and add `-g` to install them for every project:

```sh
bunx skills add alexstyl/skills --skill compose-architecture -g
```
