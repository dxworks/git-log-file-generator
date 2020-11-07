package org.dxworks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public final class FileAttribute {

    private Path basePath;
    private LocalDateTime lastModifiedTime;
    private LocalDateTime lastAccessTime;
    private LocalDateTime creationTime;
    private Long size;
    private Boolean isRegularFile;
    private Boolean isDirectory;
    private Boolean isSymbolicLink;
    private Boolean isOther;
    private UserPrincipal owner;
    private Optional<Path> relativePath;
    private long lines;

    public FileAttribute(Path filePath) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
        this.basePath = filePath;

        this.creationTime = LocalDateTime.ofInstant(
                attr.creationTime().toInstant(),
                ZoneId.systemDefault());

        this.lastAccessTime = LocalDateTime.ofInstant(
                attr.lastAccessTime().toInstant(),
                ZoneId.systemDefault());

        this.lastModifiedTime = LocalDateTime.ofInstant(
                attr.lastModifiedTime().toInstant(),
                ZoneId.systemDefault());

        this.isDirectory = attr.isDirectory();
        this.isOther = attr.isOther();
        this.isRegularFile = attr.isRegularFile();
        this.isSymbolicLink = attr.isSymbolicLink();
        this.size = attr.size();
        this.owner = Files.getOwner(filePath);
        this.relativePath = Optional.empty();
        this.lines = countLines(this.basePath);
    }

    public FileAttribute(Path filePath, Path fileRelativePath) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
        this.basePath = filePath;

        this.creationTime = LocalDateTime.ofInstant(
                attr.creationTime().toInstant(),
                ZoneId.systemDefault());

        this.lastAccessTime = LocalDateTime.ofInstant(
                attr.lastAccessTime().toInstant(),
                ZoneId.systemDefault());

        this.lastModifiedTime = LocalDateTime.ofInstant(
                attr.lastModifiedTime().toInstant(),
                ZoneId.systemDefault());

        this.isDirectory = attr.isDirectory();
        this.isOther = attr.isOther();
        this.isRegularFile = attr.isRegularFile();
        this.isSymbolicLink = attr.isSymbolicLink();
        this.size = attr.size();
        this.owner = Files.getOwner(filePath);
        this.relativePath = Optional.of(fileRelativePath);
        this.lines = countLines(this.basePath);
    }

    private static long countLines(Path fileName) throws IOException {
        long lines = 0;

        InputStream is = new BufferedInputStream(new FileInputStream(fileName.toString()));

        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        boolean endsWithoutNewLine = false;

        while ((readChars = is.read(c)) != -1) {
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n')
                    ++count;
            }
            endsWithoutNewLine = (c[readChars - 1] != '\n');
        }

        if (endsWithoutNewLine) {
            ++count;
        }
        lines = count;

        return lines;
    }
}
