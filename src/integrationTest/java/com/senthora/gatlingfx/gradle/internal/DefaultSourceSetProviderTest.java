package com.senthora.gatlingfx.gradle.internal;

import com.senthora.gatlingfx.gradle.support.SourceSets;

import org.gradle.api.Project;
import org.gradle.api.UnknownDomainObjectException;
import org.gradle.api.tasks.SourceSet;
import org.gradle.testfixtures.ProjectBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultSourceSetProviderTest {

    private Project project;

    @BeforeEach
    void setupDefaultSourceSetProviderTest() {
        project = ProjectBuilder.builder().build();
    }

    @Test
    @DisplayName("Should return source sets when source set names exist")
    void should_ReturnSourceSets_when_SourceSetNamesExist() {
        var sourceSets = SourceSets.create(project);

        var provider = new DefaultSourceSetProvider(project);
        var actual = provider.get(Set.of(
                SourceSet.MAIN_SOURCE_SET_NAME,
                SourceSet.TEST_SOURCE_SET_NAME
        ));
        assertThat(actual).containsExactlyInAnyOrder(
                sourceSets.main(),
                sourceSets.test()
        );
    }

    @Test
    @DisplayName("Should ignore source set names that do not exist")
    void should_IgnoreSourceSetNames_when_SourceSetDoesNotExist() {
        var sourceSets = SourceSets.create(project);

        var provider = new DefaultSourceSetProvider(project);
        var actual = provider.get(Set.of(
                SourceSet.MAIN_SOURCE_SET_NAME,
                "missing"
        ));
        assertThat(actual).containsExactly(sourceSets.main());
    }
}
