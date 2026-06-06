package com.senthora.gatlingfx.gradle.api;

import com.senthora.gatlingfx.gradle.internal.DefaultPluginConfigurer;

import org.gradle.api.Project;

/**
 * Configures GatlingFx Gradle plugin components.
 */
public interface GatlingFxPluginConfigurer {

    /**
     * Creates a new plugin configurer instance.
     *
     * @param project target project
     * @param extension GatlingFx extension
     *
     * @throws NullPointerException if any argument is null
     */
    static GatlingFxPluginConfigurer create(Project project, GatlingFxExtension extension) {
        return new DefaultPluginConfigurer(project, extension);
    }

    /**
     * Applies GatlingFx plugin configuration.
     */
    void configure();
}
