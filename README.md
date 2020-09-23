Trending Git Repo Sample.

MVVM + FLOW + COROUTINES + LIVE DATA + ROOM + Unit Testing + Instrumentation Testing

Functionality:
● Each time Trending Repo Data is fetched first from RoomDatabase and if 
data in DB is empty or Stale, then Fresh data is retrieved from Remote Backend Api.
● Remote Api Data is saved in Room DB after Retrofit Network gives successful response.
● The app supports minimum Android API level 19.
● The app is fetching the trending repositories from the provided public API and display it to the
users.
● While the data is being fetched, the app is showing a loading state. 
● If the app is not able to fetch the data, then it is showing an error state to the user with an
option to retry again.
● All the items in the list is in their collapsed state by default and can be expanded on
being tapped.
● Tapping any item will expand it to show more details and collapse any other expanded item.
Tapping the same item in expanded state will collapse it.
● The app is able to handle configuration changes (like rotation)
● The app is having 100% offline support. Once the data is fetched successfully from remote, it
is stored locally and served from cache until the cache expires.
● The cached data is valid for a duration of 2 hour. After that the app attempts
to refresh the data from remote and purge the cache only after successful data fetch.
● The app is giving a pull-to-refresh option to the user to force fetch data from remote.

Coding Standard:
● It follows granular, meaningful Git commit history 
● Kotlin is used with latest jetpack components
● Covers unit and UI test coverage
● Following reactive paradigm using latest reactive stream -(Flow, LiveData)
● Has proper abstraction for Dependency Injection using latest DI component- (Hilt)
● Uses Espresso and MockWebserver for UI testing

Architecture Components Used:
●  Room Database for Offline Support of Data
●  Flow for Reactive Programing
●  MVVM Design Pattern Using ViewModels  
●  Hilt for Dependency Injection
●  Glide for image Loading
●  Mockito and Espresso used for Unit testing and Instrumentation testing
●  Data Binding for UI handling
●  Retrofit for Rest Api Communication
●  Coroutines for handling async api calls callbacks
●  LiveData for observing ViewModels data on UI




