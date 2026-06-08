package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.api.GatlingFxPropertiesLoader;

import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Loads GatlingFx properties from the classpath.
 */
public final class ClasspathPropertiesLoader implements GatlingFxPropertiesLoader {

    private static final Logger LOGGER = Logging.getLogger(ClasspathPropertiesLoader.class);
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
        LOGGER.debug("Loading {}", PROPERTIES_FILE);
        var properties = new Properties();

        try (var stream = classLoader.getResourceAsStream(PROPERTIES_FILE)) {
            if (stream == null) {
                throw new IllegalStateException("Missing " + PROPERTIES_FILE);
            }
            properties.load(stream);

            var runtimeVersion = requiredProperty(properties, "runtime.version");
            LOGGER.debug("Loaded runtime.version={}", runtimeVersion);

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
