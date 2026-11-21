---
name: "Krishna Labs Robotics AI"
description: "A calm, precision-engineered dark-first design system for an Android tablet robotics brain and AI companion."
---

## Overview

Krishna Labs Robotics AI is a serious, production-grade Android tablet application that serves as the cognitive core, vision processor, voice companion, and expressive face of a personal or research robot. The interface is built for a 1280 × 800 landscape tablet workstation, balancing Apple-level interaction restraint with modern robotics telemetry clarity.

The aesthetic is **technical minimalism**: deep graphite and charcoal surfaces establish a calm, non-fatiguing studio environment, while cool-white typography and subtle robotics blue accents direct attention to live AI state, camera feeds, and physical controls. Every surface, control, and animated facial expression is designed to feel intelligent, deliberate, and trustworthy—never gamified, neon-saturated, or cluttered.

## Colors

The palette is derived from a cool robotics blue seed (`#3B82F6`) anchored by deep graphite-charcoal neutrals. Dark mode is the primary operating environment for the tablet robot brain; light mode is provided as a high-legibility alternative for bright studio or daylight environments.

- **Primary & Primary Container:** Subtle robotics blue (`#ADC6FF` in dark mode, `#0058BE` in light mode) reserved for active navigation states, primary actions, voice listening rings, and the robot's expressive eyes.
- **Surfaces & Tonal Hierarchy:** Graphite-to-charcoal stepping (`#0B0E15` → `#10131A` → `#191B23` → `#1D2027` → `#272A31` → `#32353C`) separates the persistent navigation rail, main canvas, and elevated control cards without heavy drop shadows or glassmorphism.
- **Semantic Status Colors:** Emerald (`#34D399`), Amber (`#FBBF24`), and Coral Red (`#FFB4AB` / `#BA1A1A`) are strictly reserved for hardware, permission, AI connection, and diagnostic states—never used decoratively.

```yaml
colors:
  success: "#34D399"
  on-success: "#042F1E"
  warning: "#FBBF24"
  on-warning: "#3B2300"
  light:
    primary: "#0058BE"
    on-primary: "#FFFFFF"
    primary-container: "#2170E4"
    on-primary-container: "#FEFCFF"
    secondary: "#495E8A"
    on-secondary: "#FFFFFF"
    secondary-container: "#B6CCFF"
    on-secondary-container: "#405682"
    tertiary: "#924700"
    on-tertiary: "#FFFFFF"
    tertiary-container: "#B75B00"
    on-tertiary-container: "#FFFBFF"
    error: "#BA1A1A"
    on-error: "#FFFFFF"
    error-container: "#FFDAD6"
    on-error-container: "#93000A"
    background: "#F9F9FF"
    on-background: "#191B23"
    surface: "#F9F9FF"
    surface-dim: "#D8D9E3"
    surface-bright: "#F9F9FF"
    surface-container-lowest: "#FFFFFF"
    surface-container-low: "#F2F3FD"
    surface-container: "#ECEDF7"
    surface-container-high: "#E6E7F2"
    surface-container-highest: "#E1E2EC"
    on-surface: "#191B23"
    surface-variant: "#DEE2F2"
    on-surface-variant: "#424754"
    outline: "#727785"
    outline-variant: "#C2C6D6"
    inverse-surface: "#2E3038"
    inverse-on-surface: "#EFF0FA"
    inverse-primary: "#ADC6FF"
  dark:
    primary: "#ADC6FF"
    on-primary: "#002E6A"
    primary-container: "#4D8EFF"
    on-primary-container: "#001C46"
    secondary: "#B1C6F9"
    on-secondary: "#182F59"
    secondary-container: "#304671"
    on-secondary-container: "#9FB5E7"
    tertiary: "#FFB786"
    on-tertiary: "#502400"
    tertiary-container: "#DF7412"
    on-tertiary-container: "#351600"
    error: "#FFB4AB"
    on-error: "#690005"
    error-container: "#93000A"
    on-error-container: "#FFDAD6"
    background: "#10131A"
    on-background: "#E1E2EC"
    surface: "#10131A"
    surface-dim: "#10131A"
    surface-bright: "#363941"
    surface-container-lowest: "#0B0E15"
    surface-container-low: "#191B23"
    surface-container: "#1D2027"
    surface-container-high: "#272A31"
    surface-container-highest: "#32353C"
    on-surface: "#E1E2EC"
    surface-variant: "#424754"
    on-surface-variant: "#C2C6D6"
    outline: "#8C909F"
    outline-variant: "#424754"
    inverse-surface: "#E1E2EC"
    inverse-on-surface: "#2E3038"
    inverse-primary: "#005AC2"
```

## Typography

Typography pairs **Afacad Flux** for clean, geometric headings with **Instrument Sans** for calm, highly legible UI copy and **Spline Sans Mono** for timestamps, telemetry readouts, camera resolutions, and hardware identifiers. Numeric readouts use `tabular-nums` so live counters and timestamps never jitter.

```yaml
typography:
  display-lg:
    fontFamily: "Afacad Flux"
    fontSize: 40px
    fontWeight: 600
    lineHeight: 46px
    letterSpacing: -0.5px
  display-lg-mobile:
    fontFamily: "Afacad Flux"
    fontSize: 30px
    fontWeight: 600
    lineHeight: 36px
    letterSpacing: -0.25px
  headline-lg:
    fontFamily: "Afacad Flux"
    fontSize: 28px
    fontWeight: 600
    lineHeight: 34px
    letterSpacing: -0.25px
  headline-md:
    fontFamily: "Afacad Flux"
    fontSize: 22px
    fontWeight: 600
    lineHeight: 28px
    letterSpacing: -0.15px
  headline-sm:
    fontFamily: "Afacad Flux"
    fontSize: 18px
    fontWeight: 600
    lineHeight: 24px
    letterSpacing: 0px
  title-md:
    fontFamily: "Instrument Sans"
    fontSize: 16px
    fontWeight: 600
    lineHeight: 22px
    letterSpacing: 0px
  body-lg:
    fontFamily: "Instrument Sans"
    fontSize: 16px
    fontWeight: 400
    lineHeight: 24px
    letterSpacing: 0px
  body-md:
    fontFamily: "Instrument Sans"
    fontSize: 14px
    fontWeight: 400
    lineHeight: 20px
    letterSpacing: 0px
  body-sm:
    fontFamily: "Instrument Sans"
    fontSize: 12px
    fontWeight: 400
    lineHeight: 16px
    letterSpacing: 0.1px
  label-lg:
    fontFamily: "Instrument Sans"
    fontSize: 14px
    fontWeight: 500
    lineHeight: 20px
    letterSpacing: 0.1px
  label-md:
    fontFamily: "Instrument Sans"
    fontSize: 12px
    fontWeight: 500
    lineHeight: 16px
    letterSpacing: 0.2px
  label-sm:
    fontFamily: "Instrument Sans"
    fontSize: 11px
    fontWeight: 500
    lineHeight: 14px
    letterSpacing: 0.3px
  mono-md:
    fontFamily: "Spline Sans Mono"
    fontSize: 13px
    fontWeight: 500
    lineHeight: 18px
    letterSpacing: 0px
  mono-sm:
    fontFamily: "Spline Sans Mono"
    fontSize: 11px
    fontWeight: 400
    lineHeight: 16px
    letterSpacing: 0.1px
```

## Layout & Spacing

The application is engineered for a **1280 × 800 Android tablet landscape** primary layout, scaling cleanly across 10-inch, 11-inch, and 12-inch tablets and adapting to portrait tablet orientations.

- **Persistent Shell:** A compact 220px collapsible left navigation rail (`72px` when collapsed) sits beside a fluid main workspace with a 56px top status bar. All touch targets meet or exceed `48px × 48px` for comfortable tablet ergonomics.
- **Responsive Grid:** Content uses a 12-column CSS Grid with `20px` gutters (`gutter-md` / `gutter-lg`) and `24px` outer page padding. On portrait tablets (`< 960px`), the 12-column workspace reflows to 6 or 1 column, and the sidebar collapses into a compact rail or drawer.
- **Spacing Scale:** Built on a strict 4px/8px base unit so nested cards, status rows, and controls align predictably.

```yaml
spacing:
  gutter-xs: 4px
  gutter-sm: 8px
  gutter-md: 16px
  gutter-lg: 24px
  gutter-xl: 32px
  section: 40px
  page-margin: 24px
```

## Elevation & Depth

Hierarchy is achieved through **tonal surface layering** and subtle `1px` hairline borders (`outline-variant` at low opacity), never heavy drop shadows, neon outer glows, or frosted-glass `backdrop-blur` effects.

- **Layer 0 (Shell Ground):** `surface-container-lowest` (`#0B0E15`) for the persistent sidebar and system frame.
- **Layer 1 (Workspace Canvas):** `surface` (`#10131A`) for the main scrollable viewport.
- **Layer 2 (Primary Modules):** `surface-container-low` (`#191B23`) and `surface-container` (`#1D2027`) for dashboard cards, chat containers, and settings groups.
- **Layer 3 (Interactive Controls & Active States):** `surface-container-high` (`#272A31`) and `surface-container-highest` (`#32353C`) for buttons, toggles, and selected rows.

## Shapes

The shape language balances precision hardware corners with approachable tablet ergonomics. Primary containers use generous `16px`–`24px` radii, interactive controls use `10px`–`12px`, and status indicators use full pill rounding. Inner nested elements step down their corner radius (`inner ≈ outer - padding`).

```yaml
rounded:
  sm: 6px
  DEFAULT: 10px
  md: 12px
  lg: 16px
  xl: 24px
  full: 9999px
```

## Motion & Micro-interaction

Motion communicates robot state and tactile responsiveness without distraction. UI transitions animate only `transform`, `opacity`, and color—never layout geometry.

- **Interactive Feedback:** Button presses, toggle switches, and navigation selections settle in `120ms` (`ease-out`).
- **Surface & Panel Transitions:** Contextual drawers, modals, and expression changes enter over `240ms` with a decelerating curve and exit in `160ms`.
- **Robot Face Choreography:** Idle blinking occurs naturally every 3.5–5.5 seconds (`140ms` close/open); pupil tracking and idle breathing (`4000ms` subtle cycle) feel organic and calm, never jittery. All animations honor `prefers-reduced-motion`.

```yaml
motion:
  state-change:
    duration: 120ms
    delay: 0ms
    timingFunction: ease-out
  surface-enter:
    duration: 240ms
    delay: 0ms
    timingFunction: cubic-bezier(0.2, 0, 0, 1)
  surface-exit:
    duration: 160ms
    delay: 0ms
    timingFunction: cubic-bezier(0.3, 0, 1, 1)
  face-blink:
    duration: 140ms
    delay: 0ms
    timingFunction: cubic-bezier(0.4, 0, 0.2, 1)
  idle-breath:
    duration: 4000ms
    delay: 0ms
    timingFunction: ease-in-out
```

## Voice & Content Strategy

Krishna Labs Robotics AI speaks like a calm, capable engineering partner—clear to a first-time user and precise enough for a robotics developer.

- **Tone:** Direct, reassuring, and jargon-free. Avoid marketing fluff, exclamation marks, or playful slang.
- **Capitalisation:** Sentence case for headings, descriptions, and settings rows; concise Title Case only for top-level navigation destinations and product name.
- **Action Labels:** Use clear verb-object pairs (`Start conversation`, `Open full-screen face`, `Test Gemini connection`, `Clear history`) rather than generic `OK` or `Submit`.
- **Hardware & Permission Honesty:** Never imply unimplemented hardware (e.g., external motors, ROS 2 bridge) or denied permissions (camera, microphone, flashlight) are active. Clearly state availability, permission status, and the exact next step to enable them.
- **Timestamps & Telemetry:** Use `tabular-nums` monospace formatting (`06:42 AM`, `1280 × 720 · 30 fps`, `42 ms`) consistently across every screen.

## Components

Component implementations live as individual HTML sticker sheet files in
`components/`. Each file contains the canonical, styled HTML components with
`<!-- COMPONENT: name -->` comment markers. Read each file before use.

- **Badge** → `@components/badge.html`: `badge-numeric`, `badge-dot`
- **Button** → `@components/button.html`: `button-primary`, `button-icon`, `button-with-icon`
- **Card** → `@components/card.html`: `card-elevated`, `card-action`, `card-interactive`, `card-interactive-2`
- **List** → `@components/list.html`: `list-single`, `list-two-line`, `list-leading-icon`, `list-trailing-action`, `list-interactive`, `list-interactive-2`
- **Toggle** → `@components/toggle.html`: `toggle-standard`, `toggle-label`, `toggle-description`
