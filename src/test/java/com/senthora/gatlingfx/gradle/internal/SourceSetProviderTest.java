package com.senthora.gatlingfx.gradle.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("DataFlowIssue")
class SourceSetProviderTest {

    @Test
    @DisplayName("Should throw NullPointerException when source set names are null")
    void should_ThrowNullPointerException_when_SourceSetNamesAreNull() {
        SourceSetProvider provider = names -> Set.of();
        var thrown = catchThrowable(() -> provider.getOrDefault(
                null,
                Set.of("test")
        ));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw NullPointerException when default source set names are null")
    void should_ThrowNullPointerException_when_DefaultSourceSetNamesAreNull() {
        SourceSetProvider provider = names -> Set.of();
        var thrown = catchThrowable(() -> provider.getOrDefault(
                Set.of("test"),
                null
        ));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
