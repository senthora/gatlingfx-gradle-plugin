package com.senthora.gatlingfx.gradle.support;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;

public final class TestProject {

    private static final String ROOT = "/projects/";

    private final Path directory;

    public TestProject(String name, Path directory) {
        this.directory = copy(name, directory);
    }

    public Path directory() {
        return directory;
    }

    public GradleRunner gradleRunner() {
        return GradleRunner.create()
                .withProjectDir(directory.toFile())
                .withPluginClasspath();
    }

    public BuildResult run(String... arguments) {
        return gradleRunner()
                .withArguments(arguments)
                .forwardOutput()
                .build();
    }

    private static Path copy(String name, Path directory) {
        try {
            var resource = TestProject.class.getResource(ROOT + name);

            if (resource == null) {
                throw new IllegalArgumentException("Unknown test project: " + name);
            }
            var source = Path.of(resource.toURI());

            Files.createDirectories(directory);

            try (var files = Files.walk(source)) {
                files.forEach(path -> copy(path, source, directory));
            }
            return directory;
        }
        catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Failed to copy test project: " + name, e);
        }
    }

    private static void copy(Path path, Path source, Path target) {
        try {
            var relative = source.relativize(path);
            var destination = target.resolve(relative);

            if (Files.isDirectory(path)) {
                Files.createDirectories(destination);
            }
            else {
                Files.copy(path, destination);
            }
        }
        catch (IOException e) {
            throw new IllegalStateException("Failed copying " + path, e);
        }
    }
}
