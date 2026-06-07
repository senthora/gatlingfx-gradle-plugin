package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.api.GatlingFxPropertiesLoader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Loads GatlingFx properties from the classpath.
 */
public final class ClasspathPropertiesLoader implements GatlingFxPropertiesLoader {

    static final String PROPERTIES_FILE = "gatlingfx.properties";

    private final ClassLoader classLoader;

    /**
     * Creates a new loader.
     *
     * @throws NullPointerException if {@code classLoader} is null
     */
    public ClasspathPropertiesLoader(ClassLoader classLoader) {
        this.classLoader = Objects.requireNonNull(classLoader, "classLoader must not be null");
    }

    @Override
    public GatlingFxProperties load() {
        var properties = new Properties();

        try (var stream = classLoader.getResourceAsStream(PROPERTIES_FILE)) {
            if (stream == null) {
                throw new IllegalStateException("Missing " + PROPERTIES_FILE);
            }
            properties.load(stream);

            return new GatlingFxProperties(
                    requiredProperty(properties, "runtime.version")
            );
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
