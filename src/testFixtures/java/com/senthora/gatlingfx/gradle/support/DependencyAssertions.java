package com.senthora.gatlingfx.gradle.support;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;

import static org.assertj.core.api.Assertions.assertThat;

public final class DependencyAssertions {

    private DependencyAssertions() {}

    public static void assertContains(
            Configuration configuration,
            DependencyCoordinates expected
    ) {
        var dependencies = configuration.getDependencies();
        var actual = dependencies.iterator().next();

        assertDependency(actual, expected);
    }

    public static void assertDependency(
            Dependency actual,
            DependencyCoordinates expected
    ) {
        assertThat(actual.getGroup()).isEqualTo(expected.group());
        assertThat(actual.getName()).isEqualTo(expected.name());
        assertThat(actual.getVersion()).isEqualTo(expected.version());
    }
}
