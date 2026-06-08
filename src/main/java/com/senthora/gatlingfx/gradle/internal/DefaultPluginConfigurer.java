package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.api.*;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.testing.Test;

import java.util.Objects;
import java.util.Set;

/**
 * Default {@link GatlingFxPluginConfigurer} implementation.
 * <p>
 * <strong>Conventions</strong>:
 * <ul>
 *     <li>Uses {@code test} source set when no source sets are configured.</li>
 *     <li>Writes execution logs to {@code build/gatlingfx}
 *     when no logs directory is configured.</li>
 * </ul>
 */
public final class DefaultPluginConfigurer implements GatlingFxPluginConfigurer {

    private static final String RUNTIME_CLASSPATH = "gatlingfxRuntimeClasspath";
    private static final String DEFAULT_SOURCE_SET = "test";

    private final Project project;
    private final GatlingFxExtension extension;
    private final GatlingFxProperties properties;

    public DefaultPluginConfigurer(
            Project project,
            GatlingFxExtension extension,
            GatlingFxPropertiesLoader propertiesLoader
    ) {
        Objects.requireNonNull(project, "project must not be null");
        Objects.requireNonNull(extension, "extension must not be null");
        Objects.requireNonNull(propertiesLoader, "propertiesLoader must not be null");

        this.project = project;
        this.extension = extension;
        this.properties = propertiesLoader.load();
    }

    /**
     * Configures the plugin.
     * <p>
     * <strong>Implementation Note:</strong>
     * Source set dependent configuration is
     * deferred until after project evaluation
     * to honor user-defined extension values.
     */
    @Override
    public void configure() {
        var logger = project.getLogger();
        logger.debug("Applying GatlingFx conventions");

        applyConventions();

        var sourceSetProvider = new DefaultSourceSetProvider(project);
        var dependencies = new GatlingFxDependencies(project, properties);
        var runtimeClasspath = createRuntimeClasspath();

        logger.debug("Created {} configuration", RUNTIME_CLASSPATH);

        dependencies.add(runtimeClasspath, GatlingFxDependency.RUNTIME);

        logger.debug("Added {} dependency to {}",
                GatlingFxDependency.RUNTIME,
                RUNTIME_CLASSPATH
        );
        project.afterEvaluate(ignored -> {
            var sourceSets = sourceSetProvider.getOrDefault(
                    extension.getSourceSets().get(),
                    Set.of(DEFAULT_SOURCE_SET)
            );
            if (logger.isDebugEnabled()) {
                var sourceSetNames = sourceSets.stream()
                        .map(SourceSet::getName)
                        .toList();

                logger.debug("Resolved source sets: {}", sourceSetNames);
            }
            dependencies.addImplementation(
                    sourceSets,
                    GatlingFxDependency.RUNTIME_API
            );
            dependencies.addRuntimeOnly(
                    sourceSets,
                    GatlingFxDependency.RUNTIME
            );
            var testTasks = project.getTasks().withType(Test.class);
            sourceSets.forEach(sourceSet -> testTasks
                    .matching(task -> task.getName().equals(sourceSet.getName()))
                    .configureEach(JvmProcessConfigurer::configureModuleAccess)
            );
        });
        logger.debug("Registering {} task", RunSimulationsTask.NAME);

        project.getTasks().register(
                RunSimulationsTask.NAME,
                RunSimulationsTask.class,
                t -> t.configure(
                        extension,
                        runtimeClasspath,
                        sourceSetProvider
                )
        );
    }

    private void applyConventions() {
        var buildDir = project.getLayout().getBuildDirectory();

        extension.getLogsDirectory().convention(
                buildDir.dir("gatlingfx")
        );
        extension.getSourceSets().convention(
                Set.of(DEFAULT_SOURCE_SET)
        );
    }

    private Configuration createRuntimeClasspath() {
        var configuration = project.getConfigurations().create(RUNTIME_CLASSPATH);

        configuration.setCanBeConsumed(false);
        configuration.setCanBeResolved(true);

        return configuration;
    }
}
