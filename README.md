# GatlingFx Gradle Plugin

GatlingFx Gradle Plugin makes it easier to use [GatlingFx](https://github.com/senthora/gatlingfx) in Gradle projects.  
It handles dependency setup, simulation discovery, and execution so you can focus on writing simulations.

## Quick Start

Apply the plugin:

```groovy
plugins {
    id 'com.senthora.gatlingfx' version '0.1.0'
}
```

Run all simulations:

```shell
./gradlew gatlingfxRun
```

Run a specific simulation:

```shell
./gradlew gatlingfxRun --simulation=com.example.ExampleSimulation
```

## Usage

### Extension

The plugin exposes configuration through `gatlingfx` extension.

```groovy
gatlingfx {
    sourceSets = ['integrationTest']
    logsDirectory = layout.buildDirectory.dir('gatlingfx')
}
```

| Property        | Description                                          | Default           |
|-----------------|------------------------------------------------------|-------------------|
| `sourceSets`    | Source sets that contain GatlingFx simulations       | `['test']`        |
| `logsDirectory` | Directory where GatlingFx execution logs are written | `build/gatlingfx` |

### Conventions

For each configured source set, the plugin:

- Automatically discovers and executes simulations as JUnit tests
- Adds GatlingFx runtime dependencies
- Adds required Gatling dependencies

By default, the plugin configures the `test` source set.

### Tasks

The plugin registers a single task:

| Task           | Description                    |
|----------------|--------------------------------|
| `gatlingfxRun` | Executes GatlingFx simulations |

**Options**

| Option         | Description                                             |
|----------------|---------------------------------------------------------|
| `--simulation` | Run a specific simulation by fully qualified class name |
| `--quiet`      | Suppress runtime console logging                        |
| `--fail-fast`  | Stop execution after the first failed simulation        |

## Development

### Requirements

- Java 21
- Gradle

### Build

Build the plugin:

```shell
./gradlew build
```

### Testing

Run unit tests:

```shell
./gradlew test
```

Run integration tests:

```shell
./gradlew integrationTest
```

Run functional tests:

```shell
./gradlew functionalTest
```

Generate code coverage report:

```shell
./gradlew jacocoTestReport
```

## License

This project is licensed under Apache License 2.0.
