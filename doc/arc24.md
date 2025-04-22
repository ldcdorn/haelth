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

The Meditation / Mindfullness feature requires a database of motivational quotes derived from an external source. Information given to the user about fitness may also be derived from an external source.
All other information is created within, derived from and stays within the context and scope of the app.

# Solution Strategy

The solution architecture of Project Haelth is centered around privacy, openness, and modern Android development best practices. The architecture leverages a modular, layered approach to ensure maintainability, testability, and scalability. Key strategies include:

- MVVM Architecture: The app is built using the Model-View-ViewModel (MVVM) pattern to support Jetpack Compose and separate concerns. This enables reactive UI updates, testable business logic, and clean separation between UI and data handling layers.

- Jetpack Libraries: Project Haelth uses the Android Jetpack suite (including Room, WorkManager, DataStore, and Navigation components) to simplify development and enforce modern, lifecycle-aware patterns.

- On-Device Storage: All user-generated data (such as fitness metrics, goals, and logs) is stored locally using Room (SQLite) or Jetpack DataStore for lightweight key-value storage. Backups and exports are provided via open and standardized file formats (e.g., JSON, CSV).

- Security and Privacy by Design: The app avoids third-party analytics and trackers. Android's Scoped Storage and encrypted local data storage are employed to secure user information. Permissions are requested in-context and only when strictly necessary.

Accessibility and UX: Following the Material Design system ensures consistency and accessibility. Special attention is given to font scaling, contrast, and touch target sizes.
