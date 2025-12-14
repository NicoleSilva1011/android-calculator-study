# Android Calculator Study
A simple Android calculator app implemented in Java as a college project. The app demonstrates a basic calculator UI, simple expression evaluation, and a small history feature.

## Project at a glance

- ApplicationId / namespace: `com.calculadora`
- Language: Java
- Android Gradle Plugin: configured via Gradle version catalog (`libs.versions.toml`)
- Compile SDK: 35
- Target SDK: 35
- Min SDK: 29
- Java compatibility: 11

## Features

- Basic arithmetic: addition (+), subtraction (-), multiplication (displayed as `x`), and division (/).
- Decimal input (using a comma in the UI, converted to dot for evaluation).
- Simple calculation history: results are stored in memory while the app runs and shown in a separate History screen.
- Lightweight expression evaluator implemented in `MainActivity` (left-to-right evaluation; no operator precedence or parentheses).

## Known limitations

- The evaluator computes expressions strictly left-to-right; it does NOT respect the standard operator precedence rules (e.g., `2 + 3 * 4` will be evaluated as `(2 + 3) * 4`).
- There is no persistent storage for history; history is kept only in memory for the current session.
- No support for parentheses, functions, or scientific notation.

## Project structure

- `app/` - Android application module
	- `src/main/java/com/calculadora/` - app source
		- `MainActivity.java` - main calculator UI and evaluator
		- `HistoryActivity.java` - displays calculation history
	- `src/main/res/` - layouts, strings, drawables, and other resources
	- `build.gradle.kts` - module Gradle configuration
- `build.gradle.kts` - top-level Gradle configuration
- `gradle/` - Gradle wrapper and version catalog (`libs.versions.toml`)

## Requirements

- JDK 11
- Android SDK (with platforms for API 29 through 35 recommended)
- Android Studio (recommended) or command-line Gradle (wrapper included)

## Build and run

1. Open the project in Android Studio (recommended) or use the included Gradle wrapper from a terminal.

To build an APK from PowerShell on Windows (from the repository root):

```
.\gradlew assembleDebug
```

To install and run on a connected device or emulator:

```
.\gradlew installDebug
```

Or simply use the Run/Debug actions in Android Studio to launch the app on an emulator or device.

## Usage

- Use the numeric buttons to compose numbers. The UI uses a comma for decimals; input is converted to a dot internally for calculation.
- Use operator buttons (`+`, `-`, `x`, `/`) to build expressions.
- Press `=` (equals) to evaluate the current expression. Results appear on the main display and are appended to the in-memory history.
- Tap the History button to open the `HistoryActivity` which lists previous calculations for the current session.