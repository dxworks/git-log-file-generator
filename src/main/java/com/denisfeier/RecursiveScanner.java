package com.denisfeier;

import com.denisfeier.entity.Directory;
import com.denisfeier.entity.File;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class RecursiveScanner {

    private Directory walk(Directory directory) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory.getBasePath())) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    Directory dir = new Directory(entry);
                    walk(dir);
                    directory.addSubFile(dir);
                } else {
                    directory.addSubFile(new File(entry));
                }

            }
        }
        return directory;
    }

    public static Directory dirScanning(Path path) throws IOException {
        RecursiveScanner scanner = new RecursiveScanner();
        Directory dir = new Directory(path);
        return scanner.walk(dir);
    }

}
