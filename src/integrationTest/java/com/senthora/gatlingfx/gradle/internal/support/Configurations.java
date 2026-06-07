package com.senthora.gatlingfx.gradle.internal.support;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

public record Configurations(
        Configuration mainImplementation,
        Configuration testImplementation,
        Configuration mainRuntimeOnly,
        Configuration testRuntimeOnly
) {
    public static Configurations create(Project project, SourceSets sourceSets) {
        var configurations = project.getConfigurations();
        var implementation = configurations.getByName(
                sourceSets.main().getImplementationConfigurationName()
        );
        var testImplementation = configurations.getByName(
                sourceSets.test().getImplementationConfigurationName()
        );
        var runtimeOnly = configurations.getByName(
                sourceSets.main().getRuntimeOnlyConfigurationName()
        );
        var testRuntimeOnly = configurations.getByName(
                sourceSets.test().getRuntimeOnlyConfigurationName()
        );
        return new Configurations(
                implementation,
                testImplementation,
                runtimeOnly,
                testRuntimeOnly
        );
    }
}
