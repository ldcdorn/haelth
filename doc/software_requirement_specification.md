# Project HÆLTH
## Software Requirements

### 1. Introduction

#### 1.1 Overview
Project HÆLTH is about creating an app that helps its users track their overall health, focusing on the areas of exercise, nutrition, and mindfulness.

#### 1.2 Scope
This document covers the essential and most crucial requirements of project HÆLTH (functional and non-functional). It provides a overview of the UI Mockup and the different Use-Case-Diagrams.

#### 1.3 Definitions, Acronyms and Abbreviations
UI: User Interface

### 2. Functional Requirements

#### 2.1 Overview & Vision
Our app, HÆLTH, is designed to provide a comprehensive platform for users to effortlessly track their daily habits, establish meaningful wellness goals, and receive tailored feedback to support their journey. By integrating these essential elements of health management, HÆLTH empowers individuals to achieve a well-rounded lifestyle. It places equal emphasis on fostering mindfulness of both physical and mental well-being, ensuring users are equipped with the tools and insights needed to live healthier, more balanced lives.

#### 2.2 Use Case: Meditation exercises
- The user wants to improve his/her mindfulness through (meditation) exercises.
- Part of the [UI Mockup](https://github.com/ldcdorn/haelth/blob/main/doc/ui_mockup.fig) (Right side column)
- Use case diagram: ![image](https://github.com/user-attachments/assets/5d74702f-3f3b-4157-a539-b2890fc027dc)
- Activity diagram: ![screenshot_aktivitatsdiagramm_mindfullness](https://github.com/user-attachments/assets/a786e8f1-9b2c-40b7-8137-fa66496c7d5f)

#### 2.3 Use Case: Diet tracking
- The user would like to able to track and view his diet thoughout the day.
- Part of the [UI Mockup](https://github.com/ldcdorn/haelth/blob/main/doc/ui_mockup.fig) (Middle column)
- Use case diagram: ![image](https://github.com/user-attachments/assets/66c6c042-3ac8-45be-a852-2639b8b28137)
- Activity diagram: ![screenshot_aktivitatsdiagram_nutrition](https://github.com/user-attachments/assets/b10a9897-0dda-4621-b270-767cf089326b)
- Sequence diagram for adding a meal:

![sequence_diagram_addMeal](https://github.com/user-attachments/assets/fc01d358-8f64-4f9b-bb74-09f4788ed9ce)
- Sequence diagram for viewing today's nutrition:

![sequence_diagram_viewNutrition](https://github.com/user-attachments/assets/6efea154-47c3-4c01-b593-54b08bb165ac)

#### 2.4 Use Case: Files export
- The user would like to export his files locally
- Eg. as .csv
- Use case diagram: ![image](https://github.com/user-attachments/assets/d726d248-7e46-46d8-81b0-1665a89eb4d1)

#### 2.5 Use Case: Workout tracking
- The user would like to able to track and view his exercises and workouts.
- Part of the [UI Mockup](https://github.com/ldcdorn/haelth/blob/main/doc/ui_mockup.fig) (Left column)
- Activity Diagram: ![screenshot_aktivitatsdiagramm_workout](https://github.com/user-attachments/assets/5d12d1b4-f274-4d66-b858-e22f54f5bee5)
  
### 3. Non-functional Requirements
Since our team is heavily working with user stories, the non-functional requirements are mostly covered by said stories:  
- Performace: [User story "Performace requirement"](https://github.com/ldcdorn/haelth/issues/24)
- Usability: [User story "Create UI"](https://github.com/ldcdorn/haelth/issues/6)
- Flexibility: [User story "Offline functions"](https://github.com/ldcdorn/haelth/issues/25)
- Reliability: [User story "App stability"](https://github.com/ldcdorn/haelth/issues/26)


