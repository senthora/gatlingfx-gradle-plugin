package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.api.GatlingFxExtension;
import com.senthora.gatlingfx.gradle.api.GatlingFxPropertiesLoader;

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
        var extension = Mockito.mock(GatlingFxExtension.class);
        var loader = Mockito.mock(GatlingFxPropertiesLoader.class);

        assertThatThrownBy(() -> new DefaultPluginConfigurer(null, extension, loader))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when extension is null")
    void should_ThrowNullPointerException_when_ExtensionIsNull() {
        var project = Mockito.mock(Project.class);
        var loader = Mockito.mock(GatlingFxPropertiesLoader.class);

        assertThatThrownBy(() -> new DefaultPluginConfigurer(project, null, loader))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when properties loader is null")
    void should_throw_NullPointerException_when_PropertiesLoaderIsNull() {
        var project = Mockito.mock(Project.class);
        var extension = Mockito.mock(GatlingFxExtension.class);

        assertThatThrownBy(() -> new DefaultPluginConfigurer(project, extension, null))
                .isInstanceOf(NullPointerException.class);
    }
}
