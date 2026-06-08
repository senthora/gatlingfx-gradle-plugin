package com.senthora.gatlingfx.gradle.plugin;

import com.senthora.gatlingfx.gradle.api.GatlingFxExtension;
import com.senthora.gatlingfx.gradle.api.GatlingFxPluginConfigurer;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Gradle plugin for GatlingFx.
 */
@SuppressWarnings("unused")
public final class GatlingFxPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        var logger = project.getLogger();

        logger.info("Applying GatlingFx plugin to project '{}'", project.getPath());

        var extension = project.getExtensions().create(
                GatlingFxExtension.name(),
                GatlingFxExtension.class
        );
        logger.debug("Created {} extension", GatlingFxExtension.name());

        GatlingFxPluginConfigurer
                .create(project, extension)
                .configure();

        logger.info("GatlingFx plugin configured for project '{}'", project.getPath());
    }
}
