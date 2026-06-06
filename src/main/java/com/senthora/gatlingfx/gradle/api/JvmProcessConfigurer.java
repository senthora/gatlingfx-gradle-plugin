package com.senthora.gatlingfx.gradle.api;

import org.gradle.process.JavaForkOptions;

/**
 * Internal utility that applies GatlingFx
 * runtime requirements to forked JVM processes.
 */
public final class JvmProcessConfigurer {

    private JvmProcessConfigurer() {}

    /**
     * Configures JVM module access required for GatlingFx execution.
     *
     * @param task JVM process to configure
     */
    public static void configureModuleAccess(JavaForkOptions task) {
        task.jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED");
    }
}
