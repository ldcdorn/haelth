# Deployment Diagram (Specification Level): HÆLTH App
This deployment diagram shows how the components of the HÆLTH mobile app are deployed on a local Android device.
Since the app runs fully offline, all components are packaged and executed directly on the user's smartphone, with no reliance on external servers or cloud infrastructure.

## Structure
### 1. Device: Android Smartphone
<\<device>> Android Phone:
Represents the physical mobile device running the app.

### 2. Execution Environment: Android Runtime (ART)
<\<executionEnvironment>> Android Runtime:
The runtime environment for executing Android applications on the device.

### 3. Execution Environment: Kotlin Virtual Environment
<\<executionEnvironment>> Kotlin Environment:
The execution context for Kotlin-specific components, such as Jetpack Compose and ViewModels.

Deployed Artifacts:

<\<artifact>> haelth_app.apk: The installed app package containing all functionality.

<\<artifact>> viewmodels: Handles business logic and connects UI with the database.

<\<artifact>> util_library.aar: Shared utility classes and helpers.

<\<artifact>> local_database.db: SQLite or Room database used to store meals, exercise logs, and mindfulness data locally.

Communication
All components communicate locally and directly within the device.
No external network or cloud access is required.
The main app interacts with the database and libraries through standard in-process method calls.
