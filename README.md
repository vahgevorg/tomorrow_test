# Weather App

**Project Overview**

The Weather App is a user-friendly application designed to provide real-time weather information for the user's current location. The app fetches the weather data from the Open-meteo.com API using the coordinates provided in the input list.

**Architecture**  

Weather App follows the principles of clean architecture, with a modularized approach consisting of three layers:

1. Presentation layer (app module): This layer contains the user interface, including the activities, fragments, and view models. The app uses the MVVM pattern, with Android Architecture Components such as ViewModel and LiveData to manage the presentation layer.

2. Domain layer (domain module): This layer contains the business logic of the app, including the use cases and domain models. The domain layer uses the repository pattern to abstract the data layer.

3. Data layer (data module): This layer contains the implementation of the repository interface, as well as the data sources such as network or database. The data layer uses Coroutines for asynchronous programming.

The app also uses the Koin library for dependency injection, providing a scalable and testable solution.

**Technical Design**

1. User Interface (UI) Development:

   - Activity/Fragment: Create an single activity pattern with multiple fragments to serve app navigation.
   - Layout XML: Design the UI using XML layout files, utilizing views such as TextViews, RecyclerViews, etc., to display weather information and location details.
   - RecyclerView: Implement a RecyclerView to display the list of weather forecast. Use an adapter to bind the data.
   - UI Updates: Update the UI periodically (every 10 seconds) to refresh the current location and weather information.
   
2. Networking and API Integration:

   - Retrofit Library: Use the Retrofit library to handle network requests and API integration.
   - Weather API: Define a data model to parse the response from the Open-meteo.com API. Create an API interface with methods to fetch weather data using the provided coordinates.
   - Background Task: Execute the API calls on a background thread using android service.
   - Error Handling: Implement error handling mechanisms for API failures or network errors. Display appropriate error messages to the user.

3. Refresh and Looping:

   - Use a CoroutineScope with Dispatchers.IO to handle the periodic location updates.
   - Emit the currentLocation using a Flow or any other suitable mechanism to notify the app's UI about the updated location.

4. Testing:

   - Unit Testing: Unit tests cover the critical functionalities of the app, such as API integration, location updates, data parsing, and error handling. These tests validate the behavior of individual units of code in isolation, ensuring their correctness and robustness. It's recommended to write unit tests for all public functions, especially in ViewModels and Use Cases.