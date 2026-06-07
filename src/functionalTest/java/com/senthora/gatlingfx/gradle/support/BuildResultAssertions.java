package com.senthora.gatlingfx.gradle.support;

import com.senthora.gatlingfx.gradle.internal.GatlingFxDependency;

import static org.assertj.core.api.Assertions.assertThat;

import org.gradle.testkit.runner.BuildResult;

public final class BuildResultAssertions {

    private BuildResultAssertions() {}

    public static void assertOutputContains(BuildResult result, String... values) {
        assertThat(result.getOutput()).contains(values);
    }

    public static void assertContainsImplementationDependency(
            BuildResult result,
            GatlingFxDependency dependency
    ) {
        assertOutputContains(result, "IMPLEMENTATION:" + dependency.resolve(""));
    }

    public static void assertContainsRuntimeDependency(
            BuildResult result,
            GatlingFxDependency dependency
    ) {
        assertOutputContains(result, "RUNTIME:" + dependency.resolve(""));
    }
}
