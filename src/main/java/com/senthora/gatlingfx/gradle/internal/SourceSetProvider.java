package com.senthora.gatlingfx.gradle.internal;

import org.gradle.api.tasks.SourceSet;

import java.util.Objects;
import java.util.Set;

/**
 * Resolves source sets by name.
 */
@FunctionalInterface
public interface SourceSetProvider {

    /**
     * Resolves source sets with the specified names.
     * <p>
     * Source sets that do not exist are ignored.
     *
     * @param names source set names
     *
     * @return resolved source sets
     * @throws NullPointerException if {@code names} is null
     */
    Set<SourceSet> get(Set<String> names);

    /**
     * Resolves source sets with the specified names.
     * <p>
     * Falls back to source sets with the specified
     * default names when no source sets are resolved.
     *
     * @param names source set names
     * @param defaultNames default source set names
     *
     * @return resolved source sets
     *
     * @throws NullPointerException if any argument is null
     */
    default Set<SourceSet> getOrDefault(Set<String> names, Set<String> defaultNames) {
        Objects.requireNonNull(names, "names must not be null");
        Objects.requireNonNull(defaultNames, "defaultNames must not be null");

        var sourceSets = get(names);
        return sourceSets.isEmpty() ? get(defaultNames) : sourceSets;
    }
}
