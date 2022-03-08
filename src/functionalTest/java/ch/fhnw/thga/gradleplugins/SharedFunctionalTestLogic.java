package ch.fhnw.thga.gradleplugins;

import static ch.fhnw.thga.gradleplugins.FregeExtension.DEFAULT_RELATIVE_SOURCE_DIR;
import static ch.fhnw.thga.gradleplugins.FregePlugin.FREGE_EXTENSION_NAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ch.fhnw.thga.gradleplugins.SharedTaskLogic.NEW_LINE;

import java.io.File;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;

import ch.fhnw.thga.gradleplugins.fregeproject.FregeSourceFile;

public class SharedFunctionalTestLogic
{
    public static final String MINIMAL_BUILD_FILE_CONFIG = createFregeSection(
        FregeDTOBuilder
        .builder()
        .version("'3.25.84'")
        .release("'3.25alpha'")
        .build()
    );
    public static final FregeSourceFile COMPLETION_FR = new FregeSourceFile(
        String.format("%s/%s",
            DEFAULT_RELATIVE_SOURCE_DIR,
            "ch/fhnw/thga/Completion.fr"),
        String.join
        (
            NEW_LINE,
            "module ch.fhnw.thga.Completion where",
            NEW_LINE,
            NEW_LINE,
            "  complete :: Int -> (Int, String)",
            NEW_LINE,
            "  complete i = (i, \"Frege rocks\")",
            NEW_LINE
        )
    );

    public static final boolean fileExists(
        File testProjectDir,
        String relativeFilePath)
    {
        return testProjectDir
        .toPath()
        .resolve(relativeFilePath)
        .toFile()
        .exists();
    }

    public static final void assertFileExists(
        File testProjectDir,
        String relativeFilePath)
    {
        assertTrue(fileExists(testProjectDir, relativeFilePath));
    }


    public static final void assertFileDoesNotExist(
        File testProjectDir,
        String relativeFilePath)
    {
        assertFalse(fileExists(testProjectDir, relativeFilePath));
    }
    
    static String createFregeSection(FregeDTO fregeDTO) 
   {
        return String.format(
            "%s {%s  %s%s}",
            FREGE_EXTENSION_NAME,
            System.lineSeparator(),
            fregeDTO.toBuildFile(),
            System.lineSeparator());
    }

    static BuildResult runGradleTask(File testProjectDir, String... args)
    {
        return GradleRunner
            .create()
            .withProjectDir(testProjectDir)
            .withPluginClasspath()
            .withArguments(args)
            .build();
    }
    
    static BuildResult runAndFailGradleTask(File testProjectDir, String... args)
    {
        return GradleRunner
            .create()
            .withProjectDir(testProjectDir)
            .withPluginClasspath()
            .withArguments(args)
            .buildAndFail();
    }
}
