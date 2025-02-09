package ch.fhnw.thga.gradleplugins.internal;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import ch.fhnw.thga.gradleplugins.SharedTaskLogic;

public abstract class DependencyFregeTask extends DefaultTask {
    @InputFile
    public abstract RegularFileProperty getFregeCompilerJar();

    @Input
    public abstract Property<String> getFregeDependencies();

    @InputDirectory
    public abstract DirectoryProperty getFregeOutputDir();

    @Input
    @Option(option     = "replSource",
           description = "The filename which you want to load into the repl, e.g. 'myFregeFile.fr'")
    public abstract Property<String> getReplSource();


    @TaskAction
    public void fregeDependencies() {
        System.out.println(
            SharedTaskLogic.setupClasspath(
                getProject(),
                getFregeDependencies(),
                getFregeCompilerJar(),
                getFregeOutputDir())
            .get().getAsPath());
    }
}
