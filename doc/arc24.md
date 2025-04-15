# Introduction and Goals
Project Haelth is a health and fitness tracking app for Android.
Project Haelth is intended to provide a a privacy respecting alternative to
other fitness trackers on the market, by storing as much data on device as possible,
only connecting to online services with explicit consent by the user.

Furthermore, Project Haelth aditionally gives back control to the user over his
personal health data by using open formats, allowing users to view, change, and migrate
data as they see fit.

## Quality Goals
- **Privacy**: limit sending personal data over the network, do as much processing on
  the device as possible.
- **Openness**: Use open formats, giving the user the ability to easily alter introspect
  and alter their data with commonly available tools.
- **UX/UI**: Provide good user experience by adhering to the _Material Design_ system.

# Architecture Constraints

- App must target Android as it's primary target platform.
  - Main logic implemented in _Kotlin_, as per Android development best practices.
  - Follow _Material Design_ system, as this is the primary Android design system.
  - Dependency on _Android Platform SDK_.
- Allow for full on-device storage of app data, network transactions being optional
  **AND** opt-in.
- Use Jetpack Compose UI Toolkit, the prefered way to implement UI for modern Android apps.
  - Necessitates the use of the MVVM patern, as this is used by Jetpack Compose's UI pipeline.

# Context and Scope
**TBD**

# Solution Strategy
**TBD**
