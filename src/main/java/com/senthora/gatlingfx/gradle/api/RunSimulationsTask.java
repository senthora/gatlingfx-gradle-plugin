package com.senthora.gatlingfx.gradle.api;

import com.senthora.gatlingfx.gradle.internal.SourceSetProvider;
import com.senthora.gatlingfx.runtime.core.application.GatlingFx;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.tasks.*;
import org.gradle.api.tasks.options.Option;
import org.gradle.work.DisableCachingByDefault;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Set;

/**
 * Executes GatlingFx simulations.
 */
@DisableCachingByDefault
public abstract class RunSimulationsTask extends JavaExec {

    private static final String LOGS_DIRECTORY_PROPERTY = "gatlingfx.logs.directory";
    public static final String NAME = "gatlingfxRun";

    private final Property<String> simulation;
    private final Property<Boolean> quiet;
    private final Property<Boolean> failFast;

    @Inject
    public RunSimulationsTask(ObjectFactory objects) {
        this.simulation = objects.property(String.class);
        this.quiet = objects.property(Boolean.class);
        this.failFast = objects.property(Boolean.class);

        getMainClass().set(GatlingFx.class.getName());

        JvmProcessConfigurer.configureModuleAccess(this);
        classpath(getSimulationClasspath());

        getArgumentProviders().add(() -> {
            var args = new ArrayList<String>();

            if (getSimulation().isPresent()) {
                args.add("--simulation");
                args.add(getSimulation().get());
            }
            if (getQuiet().getOrElse(false)) {
                args.add("--quiet");
            }
            if (getFailFast().getOrElse(false)) {
                args.add("--fail-fast");
            }
            return args;
        });
    }

    @Input
    @Optional
    public Property<String> getSimulation() {
        return simulation;
    }

    @Option(
            option = "simulation",
            description = "Fully qualified simulation class name"
    )
    public void setSimulation(String simulation) {
        this.simulation.set(simulation);
    }

    @Input
    @Optional
    public Property<Boolean> getQuiet() {
        return quiet;
    }

    @Option(
            option = "quiet",
            description = "Suppress runtime console logging"
    )
    public void setQuiet(boolean quiet) {
        this.quiet.set(quiet);
    }

    @Input
    @Optional
    public Property<Boolean> getFailFast() {
        return failFast;
    }

    @Option(
            option = "fail-fast",
            description = "Stop execution after the first failed simulation"
    )
    public void setFailFast(boolean failFast) {
        this.failFast.set(failFast);
    }

    @Classpath
    public abstract ConfigurableFileCollection getSimulationClasspath();

    public void configure(
            GatlingFxExtension extension,
            Configuration runtimeClasspath,
            SourceSetProvider sourceSetProvider
    ) {
        var defaultSourceSets = Set.of(SourceSet.TEST_SOURCE_SET_NAME);
        getSimulationClasspath().from(
                extension.getSourceSets().map(names ->
                        sourceSetProvider.getOrDefault(names, defaultSourceSets)
                                .stream()
                                .map(SourceSet::getRuntimeClasspath)
                                .toList()
        ));
        classpath(runtimeClasspath, getSimulationClasspath());
        systemProperty(
                LOGS_DIRECTORY_PROPERTY,
                logsDirPath(extension)
        );
    }

    private static String logsDirPath(GatlingFxExtension extension) {
        return extension.getLogsDirectory()
                .get()
                .getAsFile()
                .getAbsolutePath();
    }
}
