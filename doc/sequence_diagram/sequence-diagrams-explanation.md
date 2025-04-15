# Explanation for the sequence diagrams
## View Nutrition Sequence Diagram (sequence-diagram-viewNutrition)

This diagram illustrates the process a user follows to view their nutrition information:
- User initiates the request by interacting with the App GUI.
- The GUI sends a viewNutrition() request to the NutritionController.
- The controller calls getUserNutrition(userId) on the NutritionService.
- The service, in turn, retrieves data by calling getNutritionData(userId) on the NutritionRepository.
- The repository fetches the data from the Database.
- The nutrition data is passed back through the repository, service, and controller layers.
- Finally, the App GUI receives and displays the nutrition information to the user.

Summary: This diagram shows the sequence of fetching and returning the user's nutrition data from the database to the app interface.
## Add Meal Sequence Diagram (sequence-diagram-addMeal)

This diagram shows the sequence for a user adding a meal:
- The User inputs meal details through the App GUI.
- The GUI calls addMeal(mealData) on the MealController.
- The controller forwards this to the MealService via saveMeal(mealData).
- The MealService sends the data to the MealRepository using storeMeal(mealData).
- The repository writes the meal data into the Database.
- Upon successful storage, a confirmation response is sent back up the chain to the GUI.
- The App GUI displays a success message to the user.

Summary: This diagram reflects the user-driven action of logging a meal, passing through the application layers, and storing it in the database.
