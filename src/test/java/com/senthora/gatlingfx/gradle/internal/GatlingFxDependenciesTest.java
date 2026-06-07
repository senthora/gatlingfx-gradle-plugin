package com.senthora.gatlingfx.gradle.internal;

import org.gradle.api.Project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GatlingFxDependenciesTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when project is null")
    void should_ThrowNullPointerException_when_ProjectIsNull() {
        var properties = Mockito.mock(GatlingFxProperties.class);

        assertThatThrownBy(() -> new GatlingFxDependencies(null, properties))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when properties is null")
    void should_ThrowNullPointerException_when_PropertiesIsNull() {
        var project = Mockito.mock(Project.class);

        assertThatThrownBy(() -> new GatlingFxDependencies(project, null))
                .isInstanceOf(NullPointerException.class);
    }
}
