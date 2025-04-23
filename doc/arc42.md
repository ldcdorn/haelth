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

# Risks and Technical Debt

Identified Risks:

Platform Evolution
Android is a rapidly evolving platform. Changes in APIs, Jetpack components, or behavior across Android versions could cause compatibility issues or require frequent updates.

Security of On-Device Data
While the app avoids cloud storage, it must still protect data at rest. Improper encryption, weak permissions, or incorrect use of Android’s storage APIs could compromise user data.

Jetpack Compose Maturity
While Jetpack Compose is the recommended modern UI toolkit, it is still evolving. Some features may be unstable, undocumented, or incompatible with certain Android versions, especially on older devices.

Technical Debt:

Lack of Cross-Platform Abstraction
The app is currently tightly coupled to the Android platform. If a future iOS or desktop version is desired, significant refactoring would be needed to separate core logic from platform-specific code.

Insufficient Test Coverage
Initial development may prioritize feature delivery over comprehensive unit and UI testing. This can lead to regressions or fragile code, especially in ViewModels and data handling logic.

Data Migration Logic
Storing data in open formats is beneficial, but ensuring forward and backward compatibility (e.g., after schema changes) will require careful versioning and migration logic that may not be in place early on.

Manual Privacy Auditing
With no automatic enforcement of privacy rules, compliance depends on disciplined development and code reviews. This leaves room for human error, especially when introducing new features or libraries.

# Package diagram & description
![](https://github.com/ldcdorn/haelth/blob/main/doc/ArchitectureDesignChoices/package-diagram.svg)
## Explanation of package-diagram

The package diagram of the HÆLTH app outlines the modular structure of the application. 
It provides a high-level view of the logical separation of concerns and the relationships between components based on a clean MVVM architecture (Model-View-ViewModel) for Android, using Kotlin.

## Structure
The diagram includes the following main components:

### 1. User Interface (UI)
Contains all packages responsible for displaying and handling user interactions.

Sub-packages:

Main: Handles navigation and app entry points.
Theme: Contains color schemes, typography, and visual styling definitions.
Fitness, Nutrition, Mindfulness: Dedicated UI components for each area of the app.

Relationships:
All UI components use the Theme package (<\<provide>>) to ensure consistent visual design.
Each UI package accesses its corresponding ViewModel (<\<access>>) to fetch and display data.
The UI also utilizes helper classes from Util (<\<use>>), e.g., for formatting.

### 2. ViewModel
Contains all ViewModel classes that serve as the link between the UI and data/model layers.
They encapsulate presentation logic and prepare data for display.

Relationships:
ViewModels use data sources from the Data package (<\<use>>) and structured data classes from the Model package (<\<access>>).
They may also call functions from the Util package (<\<use>>) for reusable logic.

### 3. Model
Holds data classes (e.g., Meal, User, MindfulnessSession) that represent the app's core information structures.

Relationship:
Used by both ViewModel and Data (<\<access>>) to ensure consistent data representation.

### 4. Data
Includes everything related to data management, such as repositories, local database access (e.g., Room DAOs), or future API clients.

Relationship:
Used by the ViewModel layer to retrieve or store data (<\<use>>).
Uses the Model layer for representing stored data (<\<depend>>).

### 5. Util
A helper package for reusable logic, formatting, converters, etc.

Relationships:
Used by both UI and ViewModel layers (<\<use>>) to keep code clean and maintainable.

## Summary
With this modular architecture we focus on a clear separation of concerns:
The UI displays data, the ViewModel processes it, and the Data layer delivers it.
The separation of Fitness, Nutrition, and Mindfulness into dedicated domains makes the app scalable and maintainable.
The use of shared resources like Theme and Util promotes consistency and reusability.

# Deployment diagram
![](https://github.com/ldcdorn/haelth/blob/main/doc/ArchitectureDesignChoices/deployment-diagram.svg)

## Explanation
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

# Sequence diagrams
![](https://github.com/ldcdorn/haelth/blob/main/doc/sequence_diagram/sequence_diagram_addMeal.svg)
![](https://github.com/ldcdorn/haelth/blob/main/doc/sequence_diagram/sequence_diagram_viewNutrition.svg)
## Explanation for the sequence diagrams
### View Nutrition Sequence Diagram (sequence-diagram-viewNutrition)

This diagram illustrates the process a user follows to view their nutrition information:
- User initiates the request by interacting with the App GUI.
- The GUI sends a viewNutrition() request to the NutritionController.
- The controller calls getUserNutrition(userId) on the NutritionService.
- The service, in turn, retrieves data by calling getNutritionData(userId) on the NutritionRepository.
- The repository fetches the data from the Database.
- The nutrition data is passed back through the repository, service, and controller layers.
- Finally, the App GUI receives and displays the nutrition information to the user.

Summary: This diagram shows the sequence of fetching and returning the user's nutrition data from the database to the app interface.
### Add Meal Sequence Diagram (sequence-diagram-addMeal)

This diagram shows the sequence for a user adding a meal:
- The User inputs meal details through the App GUI.
- The GUI calls addMeal(mealData) on the MealController.
- The controller forwards this to the MealService via saveMeal(mealData).
- The MealService sends the data to the MealRepository using storeMeal(mealData).
- The repository writes the meal data into the Database.
- Upon successful storage, a confirmation response is sent back up the chain to the GUI.
- The App GUI displays a success message to the user.

Summary: This diagram reflects the user-driven action of logging a meal, passing through the application layers, and storing it in the database.
