# Chelas Multi-Player Poker Dice

> 🏆 **Academic Achievement:** Awarded 20/20 for architectural excellence and implementation in the Mobile Devices Programming course (ISEL).

## Overview

A real-time, multi-player Android application where users can join lobbies, configure match parameters, and compete in state-driven Poker Dice rounds. The application relies on a custom HTTP Restful API for real-time synchronization and leverages a modern Android development stack to ensure a fluid, crash-free user experience.

## Demo Video
Watch the demonstration video showcasing the application's functionality, including starting a match, playing rounds, viewing results, and navigating all screens:  

https://github.com/user-attachments/assets/c5fe14d6-ed2b-438b-a5a9-041a95dff5c6

## 🏗 Architecture & Technologies

The application was built emphasizing strict separation of concerns, utilizing a modern **MVVM Architecture** combined with **Unidirectional Data Flow (UDF)** to manage complex, real-time game states.

**Core Stack:**
* **UI:** Kotlin + Jetpack Compose
* **Concurrency:** Kotlin Coroutines & Flows
* **Networking:** Ktor HTTP Client

**Key Engineering Concepts Implemented:**
* **State Hoisting & UDF:** Ensuring UI consistency across multiple device screens during active gameplay.
* **Smart Routing:** Handling complex navigation graphs and back-stack management.
* **Debouncing:** Optimizing network calls and preventing race conditions on rapid user inputs.
* **Manual Dependency Injection:** Decoupling network layers to allow seamless switching between mocked dependencies (for UI testing) and live production servers.

## Milestones and Tags

The project is divided into milestones:

- **Milestone 1** (`chepd_c_1`): Title and About screens.
- **Milestone 2** (`chepd_c_2`): Match setup screens (Lobbies, Lobby Creation, Lobby) with fake services.
- **Milestone 3** (`chepd_c_3`): Login screen with fake authentication.
- **Milestone 4** (`chepd_c_4`): Game screen with fake services.
- **Milestone 5** (`chepd_c_5`): Integration with DAW's HTTP API for real-time synchronization.
- **Milestone 6** (`chepd_c_6`): Player Profile screen with statistics.
- **Final Milestone** (`chepd_c_f`): Complete app with demo video.

## Group 28 Members

- [51618] : Francisco Duarte Tavares
- [51619] : Martim Silva Seriz Monteiro
- [51620] : Radesh Ilesh Gamanbhai Govind


