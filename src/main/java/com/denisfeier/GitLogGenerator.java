package com.denisfeier;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GitLogGenerator {
    public static void main(String[] args) {
        String stringPath = "/home/denisu/IdeaProjects/GitLogGenerator";

        Path path = Paths.get(stringPath);
        try {
            System.out.println(RecursiveScanner.dirScanning(path));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}