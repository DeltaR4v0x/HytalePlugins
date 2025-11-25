# Hytale plugins by FancyInnovations

This repository contains a collection of Hytale plugins developed by FancyInnovations.

## Plugins

### FancyCore

A core plugin that provides essential functionalities and utilities.

Documentation: https://docs.fancyinnovations.com/fancycore/

## Gradle plugins

### Run Hytale Server

A Gradle plugin to download and run a Hytale server for development and testing purposes.
The server files will be located in the `run/` directory of the project.
Before starting the server it will compile (shadowJar task) and copy the plugin jar to the server's `plugins/` folder.

Usage:

```kts
runHytale {
    jarUrl = "url to hytale server jar"
}
```

Run the server with: `$ ./gradlew runServer`