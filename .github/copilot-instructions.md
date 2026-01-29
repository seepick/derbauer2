# Project Overview

This project is a turn-based strategy game that allows users to manage a kingdom; think of economy, resources, trading,
building, citizens, military, technology, etc. It is built using Kotlin and Koin, and the use of a custom textengine
simulating a CLI with a simple keyboard interface.

## Folder Structure

- `/src`: Contains the source code for the game.
- `/documentation`: Contains documentation for the project, including business and technical requirements.

## Libraries and Frameworks

- Kotlin as the main programming language.
- Koin for dependency injection.
- Custom textengine for rendering the CLI-like interface.
- Jetpack Compose for UI rendering.
- Gradle as the build tool.
- kotlin-logging and logback for logging.
- Kotest and mockk for testing.

## Coding Standards

- Adhere the official Kotlin coding conventions.
- Adhere the configured detekt rules for static code analysis; see `/config/detekt.yml`.

## UI guidelines

- A toggle is provided to switch between light and dark mode.
- Application should have a modern and clean design.