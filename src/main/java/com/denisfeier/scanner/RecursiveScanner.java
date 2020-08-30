package com.denisfeier.scanner;

import com.denisfeier.entity.FileAttribute;
import com.denisfeier.ignorer.Ignorer;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RecursiveScanner {

    private final List<FileAttribute> paths = new ArrayList<>();
    private final Ignorer ignorer;

    private RecursiveScanner(Ignorer ignorer) {
        this.ignorer = ignorer;
    }

    private RecursiveScanner() {
        this.ignorer = new Ignorer();
    }

    private List<FileAttribute> walk(Path path) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    walk(entry);
                } else {
                    if (this.ignorer.accept(entry.toString()))
                        this.paths.add(new FileAttribute(entry));
                }
            }
        }
        return this.paths;
    }

    public static List<FileAttribute> dirScanning(Path path) throws IOException {
        RecursiveScanner scanner = new RecursiveScanner();
        return scanner.walk(path);
    }

    public static List<FileAttribute> dirScanning(Path path, Ignorer ignorer) throws IOException {
        RecursiveScanner scanner = new RecursiveScanner(ignorer);
        return scanner.walk(path);
    }

}
