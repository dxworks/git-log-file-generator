package com.denisfeier;

import com.denisfeier.config.Config;
import com.denisfeier.entity.FileAttribute;
import com.denisfeier.entity.GitCommit;
import com.denisfeier.gitLogBuilder.LogBuilder;
import com.denisfeier.ignorer.Ignorer;
import com.denisfeier.ignorer.IgnorerBuilder;
import com.denisfeier.scanner.RecursiveScanner;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalTime;
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
