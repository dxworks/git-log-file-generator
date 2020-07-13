package com.denisfeier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public final class FileAttributes {
    private Path filePath;
    private FileTime lastModifiedTime;
    private FileTime lastAccessTime;
    private FileTime creationTime;
    private Long size;
    private Boolean isRegularFile;
    private Boolean isDirectory;
    private Boolean isSymbolicLink;
    private Boolean isOther;
    private Object fileKey;

    public FileAttributes(Path filePath) throws IOException {
        this.filePath = filePath;
        BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
        this.creationTime = attr.creationTime();
        this.lastAccessTime = attr.lastAccessTime();
        this.lastModifiedTime = attr.lastModifiedTime();
        this.fileKey = attr.fileKey();
        this.isDirectory = attr.isDirectory();
        this.isOther = attr.isOther();
        this.isRegularFile = attr.isRegularFile();
        this.isSymbolicLink = attr.isSymbolicLink();
    }
}
