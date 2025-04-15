# Explanation of package-diagram

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
This modular architecture supports clear separation of concerns:
The UI displays data, the ViewModel processes it, and the Data layer delivers it.
The separation of Fitness, Nutrition, and Mindfulness into dedicated domains makes the app scalable and maintainable.
The use of shared resources like Theme and Util promotes consistency and reusability.
