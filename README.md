# Gomoku Royale 🉐

> This document describes the implementation details of the Gomoku Royale mobile application.

## Table of Contents

- [Introduction](#introduction)
- [Architecture](#architecture)
- [Application](#application)
    - [Screens](#screens)
    - [Language, theme and screen orientation](#language-theme-and-screen-orientation)
    - [Mockup](#mockup)
- [Connection to the API](#connection-to-the-api)
- [Critical Analysis](#critical-analysis)
- [Further Improvements](#further-improvements)
- [Authors](#authors)

## Introduction

This is a gomoku game for Android, which is a client-server application, where the server
is implemented in Kotlin using the Spring framework in a separate project, and the client is
implemented in Kotlin using Android. The game is multiplayer and each player plays
on a different device.

The application has the following features:

- Login and Register
- Leaderboard
- Individual user statistics view
- Game lobby
- Game match

### Architecture

The application is implemented using Android with Kotlin
and [Jetpack Compose](https://developer.android.com/jetpack/compose).

The architecture of the application is based on the **MVVM pattern**, which is a
Model-View-ViewModel pattern. The MVVM pattern is a variation of the MVC pattern, where the
controller is replaced by the ViewModel.

The [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) is responsible
for exposing data to the view and handle the user interactions. The ViewModel is also responsible
for handling the communication with the service layer of the application and ensure is not tied to
the Activity lifecycle.

The [app/src/main/java/gomoku](./app/src/main/java/gomoku) folder contains the source code of the
application, which is divided into the following packages:

- `domain`: contains the domain models of the application, as well as services, repositories and
  storage abstractions. Is responsible for the business logic of the application and exposes
  an [IOState](./app/src/main/java/gomoku/domain/IOState.kt) sealed class, which is used to
  represent the state of the IO operations;
- `http`: contains the HTTP API models of the application and service implementations;
- `infrastructure`: contains the infrastructure models of the application, such as storage
  implementations and serializers;
- `ui`: contains the user interface of the application, which is divided into the following
  packages:
    - `{screenName}`: represents a screen of the application package, which is divided into the
      following packages and files:
        - `components`: contains the components of the screen;
        - `{screenName}Activity.kt`: contains the activity of the screen;
        - `{screenName}ViewModel.kt`: contains the ViewModel of the screen;
        - `{screenName}Screen.kt`: contains the screen (Jetpack Compose UI);
        - `{screenName}State.kt>`: contains the state of the screen, which is represented by a
          sealed class.
    - `shared`: represents the shared components of the application, which is divided into the
      following packages and files:
        - `components`: contains the generic components of the application;
        - `theme`: contains the theme colors and schemas of the application;
        - `dialogs`: contains the dialogs and popups of the application;
        - `background`: contains the background model and component of the application;
        - `BaseViewModel.kt`: contains the base ViewModel of the application.
- `GomokuApplication.kt`: contains the application class of the application which implements
  the `Application` interface and the `GomokuDependencyProvider` interface;
- `GomokuDependencyProvider.kt`: responsible for providing the dependencies of the application;

In the development process of the application, fake services were created to simulate the
communication with the API.

### Application

#### Screens

The application has the following screens:

* Home Screen
* About Screen
* Login Screen
* Register Screen
* Leaderboard Screen
* Variants Screen
* Game Screen

#### Language, theme and screen orientation

The game is available in three languages: `English`, `Portuguese` and `German`.

The application has a `light ☀️` and `dark theme 🌑`, which can be changed in the `settings` menu,
which is available in most screen's navigation drawer.

The application is only available in `portrait mode`.
The `landscape mode` is not supported, has it was not implemented during the development of the
application. Even though the application does not **block it**, users are advised to use the
application in `portrait mode` for a better experience.

#### Mockup

In the development of the application, a mockup was created to help visualize and structure the
application's screens. The mockup was created in [Draw.io](https://app.diagrams.net/).

Below is a visual representation of the latest version:

| ![Mockup](./docs/diagrams/gomoku-mockup.png) |
|:--------------------------------------------:|
|               *Gomoku Mockup*                |

### Connection to the API

The application communicates with the API using the [OkHttp](https://square.github.io/okhttp/)
library, which is an `HTTP client` for Android and Java.

The service layer of the application is responsible for communicating with the API and is divided
into the following packages:

TODO()

---

### Critical Analysis

Our application boasts a well-organized structure, ensuring code clarity and ease of comprehension.
While we acknowledge room for enhancement in the documentation, we are confident it currently serves
its purpose for this project.

The primary challenge we faced centered on implementing communication with the API, particularly as
it marked our first encounter with the `Siren Hypermedia` specification.

### Further Improvements

What can be improved/added in the future:

- Implement the landscape (horizontal) mode;
- Improve the documentation;
- Improve dark theme colors;
- Add login with external services (Google, Facebook, etc.);
- Add more tests.

#### Authors

- Diogo Rodrigues - 49513
- Tiago Frazão - 48666
- Francisco Engenheiro - 49428

---

Instituto Superior de Engenharia de Lisboa<br>
Programming in Mobile Devices<br>
Winter Semester of 2023/2024