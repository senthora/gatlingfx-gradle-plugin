package com.senthora.gatlingfx.gradle.internal;

/**
 * Supported GatlingFx module dependencies.
 */
enum GatlingFxDependency {

    RUNTIME("com.senthora.gatlingfx:gatlingfx-runtime"),
    RUNTIME_API("com.senthora.gatlingfx:gatlingfx-runtime-api");

    private final String module;

    GatlingFxDependency(String module) {
        this.module = module;
    }

    /**
     * Resolves dependency notation using the specified version.
     *
     * @param version dependency version
     *
     * @return resolved dependency notation
     * @throws NullPointerException if {@code version} is null
     */
    String resolve(String version) {
        return module + ':' + version;
    }
}
