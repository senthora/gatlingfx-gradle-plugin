package com.senthora.gatlingfx.gradle.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GatlingFxPropertiesTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when runtime version is null")
    void should_ThrowNullPointerException_when_RuntimeVersionIsNull() {
        assertThatThrownBy(() -> new GatlingFxProperties(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when runtime version is blank")
    void should_ThrowIllegalArgumentException_when_RuntimeVersionIsBlank() {
        assertThatThrownBy(() -> new GatlingFxProperties(" "))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
