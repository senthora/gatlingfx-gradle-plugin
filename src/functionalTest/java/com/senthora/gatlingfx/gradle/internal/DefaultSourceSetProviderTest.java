package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.support.TestProject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static com.senthora.gatlingfx.gradle.support.BuildResultAssertions.*;

class DefaultSourceSetProviderTest {

    @TempDir
    Path directory;

    @Test
    @DisplayName("Should ignore source set names that do not exist when project is evaluated")
    void should_IgnoreSourceSetNamesThatDoNotExist_when_ProjectIsEvaluated() {
        var project = new TestProject(
                "missing-source-set",
                directory.resolve("missing-source-set")
        );

        var result = project.run("printDependencies");

        assertContainsImplementationDependency(result, GatlingFxDependency.RUNTIME_API);
        assertContainsRuntimeDependency(result, GatlingFxDependency.RUNTIME);
    }

    @Test
    @DisplayName("Should use default source set when configured source sets do not exist")
    void should_UseDefaultSourceSet_when_ConfiguredSourceSetsDoNotExist() {
        var project = new TestProject(
                "all-source-sets-missing",
                directory.resolve("all-source-sets-missing")
        );
        var result = project.run("printDependencies");

        assertContainsImplementationDependency(result, GatlingFxDependency.RUNTIME_API);
        assertContainsRuntimeDependency(result, GatlingFxDependency.RUNTIME);
    }
}
