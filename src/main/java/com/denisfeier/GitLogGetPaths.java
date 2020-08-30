package com.denisfeier;

import com.denisfeier.entity.FileAttribute;
import com.denisfeier.ignorer.Ignorer;
import com.denisfeier.ignorer.IgnorerBuilder;
import com.denisfeier.scanner.RecursiveScanner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class GitLogGetPaths {

    public static void main(String[] args) throws IOException {
        IgnorerBuilder builder = new IgnorerBuilder(Path.of("/home/denisu/IdeaProjects/GitLogGenerator/src/main/resources/.globs1"));

        System.out.println(builder.getGlobs());
        Ignorer ignorer = builder.compile();

        System.out.println(ignorer.getBlackMatchersGlobs());
        System.out.println(ignorer.getWhiteMatchersGlobs());

        List<FileAttribute> list = RecursiveScanner.dirScanning(
                Path.of("/home/denisu/IdeaProjects/GitLogGenerator"), ignorer);
//
        for (FileAttribute f: list) {
            System.out.println(f);
        }
    }
}
