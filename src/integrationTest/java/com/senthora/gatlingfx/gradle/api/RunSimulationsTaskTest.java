package com.senthora.gatlingfx.gradle.api;

import com.senthora.gatlingfx.runtime.core.application.GatlingFx;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RunSimulationsTaskTest {

    private Project project;

    @BeforeEach
    void setupRunSimulationsTaskTest() {
        project = ProjectBuilder.builder().build();
    }

    @Nested
    @DisplayName("defaults")
    class DefaultConfigurationTests {

        @Test
        @DisplayName("Should use GatlingFx main class when task is created")
        void should_UseGatlingFxMainClass_when_TaskIsCreated() {
            var task = project.getTasks().register(
                    RunSimulationsTask.NAME,
                    RunSimulationsTask.class
            );
            var mainClass = task.get().getMainClass().get();
            assertThat(mainClass).isEqualTo(GatlingFx.class.getName());
        }
    }

    @Nested
    @DisplayName("arguments")
    class ArgumentProviderTests {

        @Test
        @DisplayName("Should add simulation argument when simulation is configured")
        void should_AddSimulationArgument_when_SimulationIsConfigured() {
            var taskProvider = project.getTasks().register(
                    RunSimulationsTask.NAME,
                    RunSimulationsTask.class
            );
            var task = taskProvider.get();

            var simulation = "com.example.MySimulation";
            task.setSimulation(simulation);

            var arguments = task.getArgumentProviders()
                    .getFirst()
                    .asArguments();

            assertThat(arguments).containsExactly(
                    "--simulation",
                    simulation
            );
        }

        @Test
        @DisplayName("Should add quiet argument when quiet is enabled")
        void should_AddQuietArgument_when_QuietIsEnabled() {
            var taskProvider = project.getTasks().register(
                    RunSimulationsTask.NAME,
                    RunSimulationsTask.class
            );
            var task = taskProvider.get();

            task.setQuiet(true);

            var arguments = task.getArgumentProviders()
                    .getFirst()
                    .asArguments();

            assertThat(arguments).containsExactly("--quiet");
        }

        @Test
        @DisplayName("Should add fail fast argument when fail fast is enabled")
        void should_AddFailFastArgument_when_FailFastIsEnabled() {
            var taskProvider = project.getTasks().register(
                    RunSimulationsTask.NAME,
                    RunSimulationsTask.class
            );
            var task = taskProvider.get();

            task.setFailFast(true);

            var arguments = task.getArgumentProviders()
                    .getFirst()
                    .asArguments();

            assertThat(arguments).containsExactly("--fail-fast");
        }
    }

    @Nested
    @DisplayName("configuration")
    class TaskConfigurationTests {

        @Test
        @DisplayName("Should use configured logs directory when task is configured")
        void should_UseConfiguredLogsDirectory_when_TaskIsConfigured() {
            var extension = project.getExtensions().create(
                    GatlingFxExtension.name(),
                    GatlingFxExtension.class
            );
            var logsDirectory = project.getLayout()
                    .getBuildDirectory()
                    .dir("custom-logs");

            extension.getLogsDirectory().set(logsDirectory);

            var runtimeClasspath = project
                    .getConfigurations()
                    .create("runtimeClasspath");

            var taskProvider = project.getTasks().register(
                    RunSimulationsTask.NAME,
                    RunSimulationsTask.class
            );
            var task = taskProvider.get();

            task.configure(
                    extension,
                    runtimeClasspath,
                    names -> Set.of()
            );
            var systemProperties = task.getSystemProperties();

            assertThat(systemProperties).containsKey("gatlingfx.logs.directory");

            assertThat(systemProperties.get("gatlingfx.logs.directory"))
                    .isEqualTo(logsDirectory.get().getAsFile().getAbsolutePath());
        }
    }
}
