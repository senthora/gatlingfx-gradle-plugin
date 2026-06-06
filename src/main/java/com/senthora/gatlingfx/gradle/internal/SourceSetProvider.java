package com.senthora.gatlingfx.gradle.internal;

import org.gradle.api.tasks.SourceSet;

import java.util.Set;

/**
 * Resolves source sets by name.
 */
@FunctionalInterface
public interface SourceSetProvider {

    /**
     * Resolves source sets with the specified names.
     *
     * @param names source set names
     *
     * @return resolved source sets
     * @throws NullPointerException if {@code names} is null
     */
    Set<SourceSet> get(Set<String> names);
}