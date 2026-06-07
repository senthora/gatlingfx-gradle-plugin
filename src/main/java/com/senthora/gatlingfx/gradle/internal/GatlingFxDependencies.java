package com.senthora.gatlingfx.gradle.internal;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.tasks.SourceSet;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Registers GatlingFx dependencies with Gradle configurations.
 */
final class GatlingFxDependencies {

    private final DependencyHandler delegate;
    private final String runtimeVersion;

    /**
     * Creates a new dependency registrar.
     *
     * @param project target project
     * @param properties GatlingFx properties
     *
     * @throws NullPointerException if any argument is null
     */
    GatlingFxDependencies(Project project, GatlingFxProperties properties) {
        Objects.requireNonNull(project, "project must not be null");
        Objects.requireNonNull(properties, "properties must not be null");

        this.delegate = project.getDependencies();
        this.runtimeVersion = properties.runtimeVersion();
    }

    /**
     * Adds the specified dependency to the configuration.
     *
     * @param configuration target configuration
     * @param dependency dependency to add
     *
     * @throws NullPointerException if any argument is null
     */
    void add(Configuration configuration, GatlingFxDependency dependency) {
        delegate.add(configuration.getName(), dependency.resolve(runtimeVersion));
    }

    /**
     * Adds the specified dependencies to
     * implementation configurations of the source sets.
     *
     * @param sourceSets target source sets
     * @param dependencies dependencies to add
     *
     * @throws NullPointerException if any argument is null
     */
    void addImplementation(Set<SourceSet> sourceSets, GatlingFxDependency... dependencies) {
        add(sourceSets, SourceSet::getImplementationConfigurationName, dependencies);
    }

    /**
     * Adds the specified dependencies to
     * runtime-only configurations of the source sets.
     *
     * @param sourceSets target source sets
     * @param dependencies dependencies to add
     *
     * @throws NullPointerException if any argument is null
     */
    void addRuntimeOnly(Set<SourceSet> sourceSets, GatlingFxDependency... dependencies) {
        add(sourceSets, SourceSet::getRuntimeOnlyConfigurationName, dependencies);
    }

    private void add(
            Set<SourceSet> sourceSets,
            Function<SourceSet, String> configNameResolver,
            GatlingFxDependency... dependencies
    ) {
        sourceSets.forEach(sourceSet -> {
            var name = configNameResolver.apply(sourceSet);

            for (GatlingFxDependency dependency : dependencies) {
                add(name, dependency);
            }
        });
    }

    private void add(String configurationName, GatlingFxDependency dependency) {
        delegate.add(configurationName, dependency.resolve(runtimeVersion));
    }
}
