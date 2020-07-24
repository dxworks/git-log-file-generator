package com.denisfeier;

import com.denisfeier.entity.FileAttributes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GitLogGenerator {
    public static void main(String[] args) {
        String stringPath = args[0];

        Path path = Paths.get(stringPath);
        try {
            List<FileAttributes> fileAttributes = RecursiveScanner.dirScanning(path);
            for (FileAttributes entity: fileAttributes) {
                System.out.println(entity);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
