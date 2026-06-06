package com.senthora.gatlingfx.gradle.api;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.SetProperty;

/**
 * GatlingFx plugin configuration.
 */
public abstract class GatlingFxExtension {

    public static String name() {
        return "gatlingfx";
    }

    /**
     * Source sets that contain GatlingFx simulations.
     */
    public abstract SetProperty<String> getSourceSets();

    /**
     * Directory where GatlingFx execution logs are written.
     */
    public abstract DirectoryProperty getLogsDirectory();
}
