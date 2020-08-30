package com.denisfeier;

import com.denisfeier.entity.FileAttributes;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RecursiveScanner {

    private final List<FileAttributes> paths = new ArrayList<>();

    private List<FileAttributes> walk(Path path) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    walk(entry);
                } else {
                    this.paths.add(new FileAttributes(entry));
                }
            }
        }
        return this.paths;
    }

    public static List<FileAttributes> dirScanning(Path path) throws IOException {
        RecursiveScanner scanner = new RecursiveScanner();
        return scanner.walk(path);
    }

}
