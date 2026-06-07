package com.senthora.gatlingfx.gradle.internal;

import java.util.Objects;

/**
 * Metadata required to configure GatlingFx plugin.
 */
public record GatlingFxProperties(String runtimeVersion) {

    /**
     * Creates a new properties instance.
     *
     * @param runtimeVersion GatlingFx runtime version
     *
     * @throws NullPointerException if {@code runtimeVersion} is null
     * @throws IllegalArgumentException if {@code runtimeVersion} is blank
     */
    public GatlingFxProperties {
        Objects.requireNonNull(runtimeVersion, "runtimeVersion must not be null");

        if (runtimeVersion.isBlank()) {
            throw new IllegalArgumentException("runtimeVersion must not be blank");
        }
    }
}
