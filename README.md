## App
This app makes a query in a mock api where it lists some recipes and when entering each one it is possible to see ingredients, 
details and the location where that recipe was created. it's an app of study to demonstrate my knowledge with android development.

## Screens
- Screen to list the recipes and you can search with name or ingredient.
- Screen to show de recipe detail how the name, image, ingredients and description
- In recipe detail screen it have a button to open app map to show the location from recipe.

## Used
- MVVM with clean architecture - The MVVM architecture help us to improve and maintain the app. The architecture have a Model, View and ViewModel. Model its the models that you get from api. View
is the screens and values that show to user (UI). ViewModel its the layer that do the communication between the data layer and ui layer.

- LiveData - Its the easy feature to communicate the view when you receive any data from data layer.

- Retrofit + Gson for requisitions - Its the easy feature to get data from REST api and converter to models.

- Navigation and NavArgs - Its the easy feature to navigate between fragments and pass required parameters.

- Koin for dependency injection - Its a Dependency Injection library that help us to inject values how parameter to viewModel, repositories, datasources, etc. Soo you not need create instance to some class or objects.

- Robolectric and mockk for unit tests - Its the easy feature to create Unit tests to test the viewModel, repository, dataSources, etc. And mock data objects.

- Glide for show images - Its the image library that show image from url but the most benefit is the cache to image.

- Espresso - I'm use the library to help me create UI tests.