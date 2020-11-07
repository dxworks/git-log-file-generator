package org.dxworks;

import org.dxworks.config.Config;
import org.dxworks.config.interpreter.ConfigInterpreter;
import org.dxworks.entity.FileAttribute;
import org.dxworks.exception.NoProjectPathException;
import org.dxworks.gitLogBuilder.LogBuilder;
import org.dxworks.ignorer.Ignorer;
import org.dxworks.ignorer.IgnorerBuilder;
import org.dxworks.scanner.RecursiveScanner;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class GitLogGetPaths {

    public static void main(String[] args) throws Exception {

        if (System.getProperty("projectPath", "").equals("")) {
            throw new NoProjectPathException("We couldn't find the path of your project");
        }

        Config config = ConfigInterpreter.getFullConfig();

        String projectPath = System.getProperty("projectPath");

        List<FileAttribute> listOfFiles =
                RecursiveScanner.dirScanning(
                        Path.of(projectPath),
                        getIgnorer(),
                        relativeTo(config));

        List<FileAttribute> listOfModified = LogBuilder.getModifiedFilesOnly(listOfFiles);

        if (System.getProperty("outputFile", "").equals("")) {
            LogBuilder.showCommits(listOfFiles, config, true);
            LogBuilder.showCommits(listOfModified, config, false);
        } else {
            Path outputPath = Path.of(System.getProperty("outputFile"));
            LogBuilder.outputInFile(outputPath, listOfFiles, config, true);
            LogBuilder.outputInFile(outputPath, listOfModified, config, false);
        }
    }

    static private Optional<Ignorer> getIgnorer() {

        if (System.getProperty("ignorer", "").equals("")) {
            return Optional.empty();
        } else {
            IgnorerBuilder builder = new IgnorerBuilder(
                    Path.of(System.getProperty("ignorer")));

            Ignorer ignorer = builder.compile();

            return Optional.of(ignorer);
        }
    }

    static private Optional<Path> relativeTo(Config config) {
        return config.rootDir != null ?
                Optional.of(Path.of(config.rootDir))
                :
                Optional.empty();
    }

}
