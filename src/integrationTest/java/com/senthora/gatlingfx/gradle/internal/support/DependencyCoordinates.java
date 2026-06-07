package com.senthora.gatlingfx.gradle.internal.support;

public record DependencyCoordinates(String group, String name, String version) {

    public static final DependencyCoordinates RUNTIME = new DependencyCoordinates(
            "com.senthora.gatlingfx",
            "gatlingfx-runtime",
            "1.2.3"
    );
}
