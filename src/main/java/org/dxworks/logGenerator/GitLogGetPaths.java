package org.dxworks.logGenerator;

import org.dxworks.logGenerator.config.Config;
import org.dxworks.logGenerator.config.interpreter.ConfigInterpreter;
import org.dxworks.logGenerator.entity.FileAttribute;
import org.dxworks.logGenerator.entity.GitCommit;
import org.dxworks.logGenerator.exception.EmptyAttributesListException;
import org.dxworks.logGenerator.exception.NoProjectPathException;
import org.dxworks.logGenerator.gitLogBuilder.LogBuilder;
import org.dxworks.logGenerator.ignorer.Ignorer;
import org.dxworks.logGenerator.ignorer.IgnorerBuilder;
import org.dxworks.logGenerator.scanner.RecursiveScanner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
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
                        relativeTo(projectPath));

        outputCommits(listOfFiles, config);
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

    static private Optional<Path> relativeTo(String basePath) {
        return !basePath.isEmpty() ?
                Optional.of(Paths.get(basePath))
                :
                Optional.empty();
    }

    static private void outputCommits(List<FileAttribute> listOfFiles, Config config) throws EmptyAttributesListException, IOException {

        List<FileAttribute> listOfModified = LogBuilder.getModifiedFilesOnly(listOfFiles);
        List<GitCommit> gitCommitsAdded = LogBuilder.createGitCommitLogs(listOfFiles, config, true);
        List<GitCommit> gitCommitsModified = LogBuilder.createGitCommitLogs(listOfModified, config, false);
        List<GitCommit> allCommits = new LinkedList<>();
        allCommits.addAll(gitCommitsAdded);
        allCommits.addAll(gitCommitsModified);

        allCommits.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        if (System.getProperty("outputFile", "").equals("")) {
            LogBuilder.showCommits(allCommits);
        } else {
            Path outputPath = Paths.get(System.getProperty("outputFile"));
            LogBuilder.outputInFile(outputPath, allCommits);
        }
    }
}
