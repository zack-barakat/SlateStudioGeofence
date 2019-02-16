# SlateStudioGeofence

## 1 App Architecture
#### 1.1 General App Architecture
The application uses [Dagger2](https://google.github.io/dagger/) to inject `repositories` (whom maintain data layer and business logic) into different components of the app (fragments, activities, views, services, test cases, presenters, etc...).

**Important Note:** All presenters and classes interacts with the `repositories` **interfaces** only and does not interact with Preference Helper directly.
#### 1.2 Types of Repsotitoeies:
Repositories interfaces can be found under [`data.repositories`](https://github.com/zack-barakat/SlateStudioGeofence/tree/master/app/src/main/java/com/android/slatestudio/test/data/repositories) package. Each repository is responsible for handling its side of the business logic.
Dagger2 should maintain **only one copy** of each repository per app session (Singlton behaviour).

* **GeofenceRepository**: Responsible for all geofence data such as (adding geofences, fetching geofences, removing geofences,and etc...).

#### 1.3 UI componentes architecture
Model View Preseneter known as MVP is the the architecture pattern used to develop SlateStudio Geofence TEst application UI.
**Model:** It is responsible for handling the data part of the application.
**View:** It is responsible for laying out the views with specific data on the screen.
**Presenter:** It is a bridge that connects a Model and a View. It also acts as an instructor to the View.
To read more about MVP Architecture you may refer to these links:
- [MVP Architecture](https://blog.mindorks.com/essential-guide-for-designing-your-android-app-architecture-mvp-part-1-74efaf1cda40#.lkml1yggq)
- [MVP Android Gudelines](https://medium.com/@cervonefrancesco/model-view-presenter-android-guidelines-94970b430ddf)

## 2 Language used
#### 2.1 Kotlin
#### 2.2 Java

## 3 Main Libraries used

* [Dagger2](https://google.github.io/dagger/android.html) - Dependency injection framework
* [Anko](https://github.com/Kotlin/anko) - Set of Kotlin extensions to make android development faster
* [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid) - Reactive programming, simplifies work with threading and concurrency in java and android.
* [Mockito](https://github.com/mockito/mockito) and [Robolectric](https://github.com/robolectric/robolectric) - Unit test framework and mocking tools.

## 4 Unit Test

#### All presenters have equivalent test presenets to test mvp intercations and ensure logic is done properly

## 5 App UI flow and functionality
#### There are two screens in the app, Main screen where user can view geofences and check the status whether he is inside or outside of a geofence, and create geofence screen where user can create new geofence screen.
### Main Screen
![screenshot_1550294041](https://user-images.githubusercontent.com/13542406/52894925-dd847880-31ec-11e9-8674-7b611ebb41e6.png)

### Create Geofence Screen
![screenshot_1550294058](https://user-images.githubusercontent.com/13542406/52894926-de1d0f00-31ec-11e9-98de-01f5374af1e7.png)

### To test the the whole flow you can download the apk using this link from bitrise: https://app.bitrise.io/artifact/12046500/p/5b0d31e273f54e06f0e8a94ce3e82adc  

