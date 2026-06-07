package com.senthora.gatlingfx.gradle.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClasspathPropertiesLoaderTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when class loader is null")
    void should_ThrowNullPointerException_when_ClassLoaderIsNull() {
        assertThatThrownBy(() -> new ClasspathPropertiesLoader(null))
                .isInstanceOf(NullPointerException.class);
    }
}
