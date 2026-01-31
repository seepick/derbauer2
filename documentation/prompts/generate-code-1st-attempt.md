# Strategy Game Application Generation

## Requirements

* the application is a strategy game exactly like the current implementation is, but with more features and complexity
  as described
    * it has an identical user interface, using jetpack compose as a text rendering engine, simulating a CLI, and simple
      keyboard interactions
* use the markdown files in the `/documentation` directory as the **requirements** to generate a whole application.

# Generation Instructions

* generate code into the `/src/generated/kotlin` folder
    * to be specific, into the existing package `com.github.seepick.derbauer2gen`
* if needed, adjust other project files
    * most likely the `build.gradle.kts` file.
* use the code under the `/src/main/kotlin` folder as a template
    * build upon it, follow it's style and principles

## Best Practices

* adhere to **clean code** principles:
    * small files, classes, functions
    * single responsibility principle
    * modularity, separation of concerns, low coupling (use interfaces where appropriate)
* each package has a **koin module** to wire beans.
* use **logging** where appropriate.
  *Use meaningful, short names for variables, functions, classes, and packages; capture its essence concisely.
