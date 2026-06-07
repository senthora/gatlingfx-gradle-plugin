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
}
