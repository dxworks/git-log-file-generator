package org.dxworks.logGenerator.scanner;

import org.dxworks.logGenerator.entity.FileAttribute;
import org.dxworks.logGenerator.ignorer.Ignorer;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecursiveScanner {

    private final List<FileAttribute> paths = new ArrayList<>();
    private final Ignorer ignorer;

    private RecursiveScanner(Ignorer ignorer) {
        this.ignorer = ignorer;
    }

    private RecursiveScanner() {
        this.ignorer = new Ignorer();
    }

    private List<FileAttribute> walk(Path path, Optional<Path> rootPath) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    walk(entry, rootPath);
                } else {
                    if (rootPath.isPresent()) {
                        Path relativizedPath = rootPath.get().relativize(entry);
//                        System.out.println(relativizedPath);
                        if (this.ignorer.accept(relativizedPath.toString())) {
                            this.paths.add(new FileAttribute(entry, relativizedPath));
                        }
                    } else {
                        if (this.ignorer.accept(entry.toString()))
                            this.paths.add(new FileAttribute(entry));
                    }
                }
            }
        }
        return this.paths;
    }

    public static List<FileAttribute> dirScanning(Path path, Optional<Ignorer> ignorer, Optional<Path> rootPath) throws IOException {
        RecursiveScanner scanner = ignorer
                .map(RecursiveScanner::new)
                .orElseGet(RecursiveScanner::new);
        return scanner.walk(path, rootPath);
    }

}
