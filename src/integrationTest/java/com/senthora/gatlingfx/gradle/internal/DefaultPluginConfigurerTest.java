package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.api.GatlingFxExtension;
import com.senthora.gatlingfx.gradle.api.RunSimulationsTask;
import com.senthora.gatlingfx.gradle.support.SourceSets;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPluginConfigurerTest {

    private Project project;
    private GatlingFxExtension extension;
    private DefaultPluginConfigurer configurer;

    @BeforeEach
    void setupDefaultPluginConfigurerTest() {
        project = ProjectBuilder.builder().build();
        extension = project.getExtensions().create(
                GatlingFxExtension.name(),
                GatlingFxExtension.class
        );
        configurer = new DefaultPluginConfigurer(
                project,
                extension,
                () -> new GatlingFxProperties("1.2.3")
        );
        SourceSets.create(project);
    }

    @Nested
    @DisplayName("conventions")
    class DefaultConventionsTests {

        @Test
        @DisplayName("Should use test source set when source sets are not configured")
        void should_UseTestSourceSet_when_SourceSetsAreNotConfigured() {
            configurer.configure();

            var sourceSets = extension.getSourceSets().get();
            assertThat(sourceSets).containsExactly("test");
        }

        @Test
        @DisplayName("Should use build gatlingfx directory when logs directory is not configured")
        void should_UseBuildGatlingFxDirectory_when_LogsDirectoryIsNotConfigured() {
            configurer.configure();

            var logsDirectory = extension.getLogsDirectory().get().getAsFile();
            assertThat(logsDirectory).isEqualTo(project.getLayout()
                    .getBuildDirectory()
                    .dir("gatlingfx")
                    .get()
                    .getAsFile());
        }
    }

    @Nested
    @DisplayName("registration")
    class TaskRegistrationTests {

        @Test
        @DisplayName("Should register RunSimulationsTask when plugin is configured")
        void should_RegisterRunSimulationsTask_when_PluginIsConfigured() {
            configurer.configure();

            assertThat(project.getTasks().findByName(RunSimulationsTask.NAME))
                    .isInstanceOf(RunSimulationsTask.class);
        }
    }
}
