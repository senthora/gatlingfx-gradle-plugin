package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.api.GatlingFxExtension;

import org.gradle.api.Project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultPluginConfigurerTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when project is null")
    void should_ThrowNullPointerException_when_ProjectIsNull() {
        GatlingFxExtension extension = Mockito.mock(GatlingFxExtension.class);

        assertThatThrownBy(() -> new DefaultPluginConfigurer(null, extension))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when extension is null")
    void should_ThrowNullPointerException_when_ExtensionIsNull() {
        Project project = Mockito.mock(Project.class);

        assertThatThrownBy(() -> new DefaultPluginConfigurer(project, null))
                .isInstanceOf(NullPointerException.class);
    }
}
