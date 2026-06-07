package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.support.Configurations;
import com.senthora.gatlingfx.gradle.support.DependencyCoordinates;
import com.senthora.gatlingfx.gradle.support.SourceSets;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.testfixtures.ProjectBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GatlingFxDependenciesTest {

    private Project project;
    private GatlingFxProperties properties;

    @BeforeEach
    void setupGatlingFxDependenciesTest() {
        project = ProjectBuilder.builder().build();
        properties = new GatlingFxProperties("1.2.3");
    }

    @Test
    @DisplayName("Should add dependency to configuration when dependency is added")
    void should_AddDependencyToConfiguration_when_DependencyIsAdded() {
        var configuration = project.getConfigurations().create("test");

        var dependencies = new GatlingFxDependencies(project, properties);
        dependencies.add(configuration, GatlingFxDependency.RUNTIME);

        assertContainsDependency(configuration, DependencyCoordinates.RUNTIME);
    }

    @Test
    @DisplayName("Should add dependencies to implementation configurations when source sets are provided")
    void should_AddDependenciesToImplementationConfigurations_when_SourceSetsAreProvided() {
        var sourceSets = SourceSets.create(project);
        var configurations = Configurations.create(project, sourceSets);

        var dependencies = new GatlingFxDependencies(project, properties);
        dependencies.addImplementation(
                sourceSets.all(),
                GatlingFxDependency.RUNTIME
        );
        assertContainsDependency(
                configurations.mainImplementation(),
                DependencyCoordinates.RUNTIME
        );
        assertContainsDependency(
                configurations.testImplementation(),
                DependencyCoordinates.RUNTIME
        );
    }

    @Test
    @DisplayName("Should add dependencies to runtime-only configurations when source sets are provided")
    void should_AddDependenciesToRuntimeOnlyConfigurations_when_SourceSetsAreProvided() {
        var sourceSets = SourceSets.create(project);
        var configurations = Configurations.create(project, sourceSets);

        var dependencies = new GatlingFxDependencies(project, properties);
        dependencies.addRuntimeOnly(
                sourceSets.all(),
                GatlingFxDependency.RUNTIME
        );
        assertContainsDependency(
                configurations.mainRuntimeOnly(),
                DependencyCoordinates.RUNTIME
        );
        assertContainsDependency(
                configurations.testRuntimeOnly(),
                DependencyCoordinates.RUNTIME
        );
    }

    private static void assertDependency(Dependency actual, DependencyCoordinates expected) {
        assertThat(actual.getGroup()).isEqualTo(expected.group());
        assertThat(actual.getName()).isEqualTo(expected.name());
        assertThat(actual.getVersion()).isEqualTo(expected.version());
    }

    private static void assertContainsDependency(
            Configuration configuration,
            DependencyCoordinates expected
    ) {
        var dependencies = configuration.getDependencies();
        var actual = dependencies.iterator().next();

        assertDependency(actual, expected);
    }
}
