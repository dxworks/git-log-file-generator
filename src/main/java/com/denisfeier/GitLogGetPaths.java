package com.denisfeier;

import com.denisfeier.entity.FileAttribute;
import com.denisfeier.entity.GitCommit;
import com.denisfeier.ignorer.Ignorer;
import com.denisfeier.ignorer.IgnorerBuilder;
import com.denisfeier.scanner.RecursiveScanner;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class GitLogGetPaths {

    public static void main(String[] args) throws IOException {

        String projectPath = "/home/denisu/IdeaProjects/GitLogGenerator";

        IgnorerBuilder builder = new IgnorerBuilder(
                Path.of("/home/denisu/IdeaProjects/GitLogGenerator/src/main/resources/.globs1"));


        System.out.println(builder.getGlobs());
        Ignorer ignorer = builder.compile();

        System.out.println(ignorer.getBlackMatchersGlobs());
        System.out.println(ignorer.getWhiteMatchersGlobs());

        List<FileAttribute> list =
                RecursiveScanner.dirScanning(
                        Path.of(projectPath),
                        Optional.of(ignorer),
                        Optional.of(Path.of(projectPath)));

//        for (FileAttribute f: list) {
//            System.out.println(f);
//        }

        GitCommit commit = new GitCommit(list);

        System.out.println(commit.stringCommit());

    }
}
