package org.dxworks.logGenerator;

import org.dxworks.logGenerator.config.Config;
import org.dxworks.logGenerator.config.interpreter.ConfigInterpreter;
import org.dxworks.logGenerator.entity.FileAttribute;
import org.dxworks.logGenerator.exception.NoProjectPathException;
import org.dxworks.logGenerator.gitLogBuilder.LogBuilder;
import org.dxworks.logGenerator.ignorer.Ignorer;
import org.dxworks.logGenerator.ignorer.IgnorerBuilder;
import org.dxworks.logGenerator.scanner.RecursiveScanner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class GitLogGetPaths {

    public static void main(String[] args) throws Exception {

        System.out.println("projectPath: " + System.getProperty("projectPath"));
        System.out.println("outputFile: " + System.getProperty("outputFile"));
        System.out.println("fileConfig: " + System.getProperty("fileConfig"));
        System.out.println("ignorer: " + System.getProperty("ignorer"));

        if (System.getProperty("projectPath", "").equals("")) {
            throw new NoProjectPathException("We couldn't find the path of your project");
        }

        Config config = ConfigInterpreter.getFullConfig();

        String projectPath = System.getProperty("projectPath");

        List<FileAttribute> listOfFiles =
                RecursiveScanner.dirScanning(
                        Paths.get(projectPath),
                        getIgnorer(),
                        relativeTo(config));

        List<FileAttribute> listOfModified = LogBuilder.getModifiedFilesOnly(listOfFiles);

        if (System.getProperty("outputFile", "").equals("")) {
            LogBuilder.showCommits(listOfFiles, config, true);
            LogBuilder.showCommits(listOfModified, config, false);
        } else {
            Path outputPath = Paths.get(System.getProperty("outputFile"));
            LogBuilder.outputInFile(outputPath, listOfFiles, config, true);
            LogBuilder.outputInFile(outputPath, listOfModified, config, false);
        }
    }

    static private Optional<Ignorer> getIgnorer() {

        if (System.getProperty("ignorer", "").equals("")) {
            return Optional.empty();
        } else {
            IgnorerBuilder builder = new IgnorerBuilder(
                    Paths.get(System.getProperty("ignorer")));

            Ignorer ignorer = builder.compile();

            return Optional.of(ignorer);
        }
    }

    static private Optional<Path> relativeTo(Config config) {
        return config.rootDir != null ?
                Optional.of(Paths.get(config.rootDir))
                :
                Optional.empty();
    }

}
