# Weather App

**Project Overview**

The Weather App is a user-friendly application designed to provide real-time weather information for the user's current location. The app fetches the weather data from the Open-meteo.com API using the coordinates provided in the input list.

**Technologies Used**

Kotlin programming language<br/>
Android Studio IDE<br/>
Android SDK (Software Development Kit)<br/>
Android Architecture Components (ViewModel, LiveData)<br/>
Coroutines for asynchronous programming<br/>
Mockito for testing<br/>

**Installation**

To use the Weather App on your device follow these steps to install it manually:

1. Clone this repository to your local machine using git clone https://github.com/vahgevorg/tomorrow_test.git
2. Open the project in Android Studio
3. Build the project by selecting Build > Make Project from the menu bar
4. Connect your Android device to your computer
5. Run the app on your device by selecting Run > Run 'app' from the menu bar

**Prerequisites**

Android Studio 4.0 or higher<br/>
Android SDK 23 or higher<br/>
Gradle 7.3.0 or higher<br/>

**Key Features**

1. Automatic Location Updates: The Weather App periodically updates the user's current location every 10 seconds. It retrieves the coordinates from the input list and displays the corresponding weather information.
2. Real-time Weather Information: The app connects to the Open-meteo.com API endpoint to fetch accurate and up-to-date weather data for the current location. The API provides a wide range of weather parameters, including temperature, humidity, wind speed, precipitation, and more.
3. User-Friendly Interface: The app presents the weather information in a clear and intuitive manner, making it easy for users to understand and interpret the data. The interface may include elements such as a visual representation of weather conditions (e.g., icons for sunny, rainy, cloudy), temperature display, wind speed and direction, and other relevant details.
4. Automatic Refresh: The app automatically refreshes the location list to ensure that all locations are cycled through continuously. Once the last location is reached, it loops back to the first location, creating a seamless transition between different locations.
5. Responsiveness: The Weather App is designed to be responsive and adaptable to different device screen sizes, providing a consistent user experience across various smartphones, tablets, and desktops.
6. Minimalistic Design: The app follows a clean and minimalistic design approach, focusing on essential weather information without overwhelming the user with excessive details. The emphasis is on clarity, readability, and ease of use.

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