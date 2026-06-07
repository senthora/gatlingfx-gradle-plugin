package com.senthora.gatlingfx.gradle.internal.support;

import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.util.Set;

public record SourceSets(SourceSet main, SourceSet test) {

    public static SourceSets create(Project project) {
        project.getPluginManager().apply("java");

        var extension = project.getExtensions();
        var sourceSets = extension.getByType(SourceSetContainer.class);

        return new SourceSets(
                sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME),
                sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME)
        );
    }

    public Set<SourceSet> all() {
        return Set.of(main, test);
    }
}
