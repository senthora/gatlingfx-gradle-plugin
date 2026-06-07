package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.support.TestProject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static com.senthora.gatlingfx.gradle.support.BuildResultAssertions.*;

class DefaultPluginConfigurerTest {

    @TempDir
    Path directory;

    @Test
    @DisplayName("Should add dependencies to configured source sets when project is evaluated")
    void should_AddDependenciesToConfiguredSourceSets_when_ProjectIsEvaluated() {
        var project = new TestProject(
                "configured-source-sets",
                directory.resolve("configured-source-sets")
        );
        var result = project.run("printDependencies");

        assertContainsImplementationDependency(result, GatlingFxDependency.RUNTIME_API);
        assertContainsRuntimeDependency(result, GatlingFxDependency.RUNTIME);
    }

    @Test
    @DisplayName("Should honor configured source sets when project is evaluated")
    void should_HonorConfiguredSourceSets_when_ProjectIsEvaluated() {
        var project = new TestProject(
                "custom-source-set",
                directory.resolve("custom-source-set")
        );
        var result = project.run("printDependencies");

        assertContainsImplementationDependency(result, GatlingFxDependency.RUNTIME_API);
        assertContainsRuntimeDependency(result, GatlingFxDependency.RUNTIME);
    }
}
