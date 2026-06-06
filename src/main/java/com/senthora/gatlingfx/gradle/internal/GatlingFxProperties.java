package com.senthora.gatlingfx.gradle.internal;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Metadata required to configure GatlingFx plugin.
 */
record GatlingFxProperties(String runtimeVersion) {

    private static final String PROPERTIES_FILE = "/gatlingfx.properties";

    /**
     * Creates a new properties instance.
     *
     * @param runtimeVersion GatlingFx runtime version
     *
     * @throws NullPointerException if {@code runtimeVersion} is null
     * @throws IllegalArgumentException if {@code runtimeVersion} is blank
     */
    GatlingFxProperties {
        Objects.requireNonNull(runtimeVersion, "runtimeVersion must not be null");

        if (runtimeVersion.isBlank()) {
            throw new IllegalArgumentException("runtimeVersion must not be blank");
        }
    }

    /**
     * Loads GatlingFx properties from the classpath.
     *
     * @throws IllegalStateException if the properties file
     * is missing or required properties are missing
     * @throws UncheckedIOException if the properties file cannot be read
     */
    static GatlingFxProperties load() {
        var properties = new Properties();

        try (var stream = GatlingFxProperties.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (stream == null) {
                throw new IllegalStateException("Missing " + PROPERTIES_FILE);
            }
            properties.load(stream);

            var runtimeVersion = requiredProperty(properties, "runtime.version");

            return new GatlingFxProperties(runtimeVersion);
        }
        catch (IOException e) {
            throw new UncheckedIOException("Failed reading " + PROPERTIES_FILE, e);
        }
    }

    private static String requiredProperty(Properties properties, String key) {
        var value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing property: " + key);
        }
        return value;
    }
}
