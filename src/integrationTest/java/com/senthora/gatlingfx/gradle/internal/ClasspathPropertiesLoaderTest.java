package com.senthora.gatlingfx.gradle.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.net.URLClassLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClasspathPropertiesLoaderTest {

    @Test
    @DisplayName("Should load properties when properties file exists")
    void should_LoadProperties_when_PropertiesFileExists() {
        var loader = new ClasspathPropertiesLoader(
                getClass().getClassLoader()
        );
        var properties = loader.load();

        assertThat(properties.runtimeVersion())
                .isEqualTo("1.2.3");
    }

    @Test
    @DisplayName("Should throw IllegalStateException when required configuration is unavailable")
    void should_ThrowIllegalStateException_when_RequiredConfigurationIsUnavailable() {
        var classLoader = new URLClassLoader(new URL[0], null);
        var loader = new ClasspathPropertiesLoader(classLoader);

        assertThatThrownBy(loader::load)
                .isInstanceOf(IllegalStateException.class);
    }
}
