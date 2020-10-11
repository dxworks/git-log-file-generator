package org.dxworks;

import org.dxworks.config.Config;
import org.dxworks.entity.FileAttribute;
import org.dxworks.entity.GitCommit;
import org.dxworks.gitLogBuilder.LogBuilder;
import org.dxworks.ignorer.Ignorer;
import org.dxworks.ignorer.IgnorerBuilder;
import org.dxworks.scanner.RecursiveScanner;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GitLogGetPaths {

    public static void main(String[] args) throws Exception {

        String projectPath = "/home/denisu/IdeaProjects/GitLogGenerator";

        IgnorerBuilder builder = new IgnorerBuilder(
                Path.of("/home/denisu/IdeaProjects/GitLogGenerator/src/main/resources/.globs1"));

        Ignorer ignorer = builder.compile();

        List<FileAttribute> list =
                RecursiveScanner.dirScanning(
                        Path.of(projectPath),
                        Optional.of(ignorer),
                        Optional.of(Path.of(projectPath)));

        Map<Long, List<FileAttribute>> sortedByLastModifiedTime =
                LogBuilder.sortedByLastModifiedTime(list, 300, 0, 0);

        List<GitCommit> gitCommits = new LinkedList<>();

        for (Map.Entry<Long, List<FileAttribute>> entry : sortedByLastModifiedTime.entrySet()) {
           gitCommits.add(new GitCommit(entry.getValue()));
        }

//        gitCommits.forEach(gitCommit -> System.out.println(gitCommit.stringCommit()));

        Config config = Config.createConfig(Path.of("/home/denisu/IdeaProjects/GitLogGenerator/src/main/resources/config"));

        System.out.println(config);

    }
}
