package com.senthora.gatlingfx.gradle.api;

import com.senthora.gatlingfx.gradle.internal.ClasspathPropertiesLoader;
import com.senthora.gatlingfx.gradle.internal.GatlingFxProperties;

import java.io.UncheckedIOException;

/**
 * Loads GatlingFx properties.
 */
@FunctionalInterface
public interface GatlingFxPropertiesLoader {

    /**
     * Creates a loader that reads GatlingFx properties
     * from the classpath using the specified class loader.
     *
     * @param classLoader class loader used to locate the properties file
     *
     * @throws NullPointerException if {@code classLoader} is null
     */
    static GatlingFxPropertiesLoader classpath(ClassLoader classLoader) {
        return new ClasspathPropertiesLoader(classLoader);
    }

    /**
     * Loads GatlingFx properties.
     *
     * @return loaded properties
     *
     * @throws IllegalStateException if required properties are unavailable
     * @throws UncheckedIOException if properties cannot be read
     */
    GatlingFxProperties load();
}
