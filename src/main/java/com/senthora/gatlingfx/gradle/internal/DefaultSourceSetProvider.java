package com.senthora.gatlingfx.gradle.internal;


import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default {@link SourceSetProvider} implementation.
 */
public final class DefaultSourceSetProvider implements SourceSetProvider {

    private final SourceSetContainer delegate;

    public DefaultSourceSetProvider(Project project) {
        this.delegate = project.getExtensions()
                .getByType(SourceSetContainer.class);
    }

    @Override
    public Set<SourceSet> get(Set<String> names) {
        return names.stream()
                .map(delegate::getByName)
                .collect(Collectors.toUnmodifiableSet());
    }
}
