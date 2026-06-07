package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.support.Configurations;
import com.senthora.gatlingfx.gradle.support.DependencyAssertions;
import com.senthora.gatlingfx.gradle.support.DependencyCoordinates;
import com.senthora.gatlingfx.gradle.support.SourceSets;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        DependencyAssertions.assertContains(
                configuration,
                DependencyCoordinates.RUNTIME
        );
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
        DependencyAssertions.assertContains(
                configurations.mainImplementation(),
                DependencyCoordinates.RUNTIME
        );
        DependencyAssertions.assertContains(
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
        DependencyAssertions.assertContains(
                configurations.mainRuntimeOnly(),
                DependencyCoordinates.RUNTIME
        );
        DependencyAssertions.assertContains(
                configurations.testRuntimeOnly(),
                DependencyCoordinates.RUNTIME
        );
    }
}
