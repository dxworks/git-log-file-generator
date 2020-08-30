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
import java.nio.file.attribute.UserPrincipal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public final class FileAttribute {

    private Path basePath;
    private FileTime lastModifiedTime;
    private FileTime lastAccessTime;
    private FileTime creationTime;
    private Long size;
    private Boolean isRegularFile;
    private Boolean isDirectory;
    private Boolean isSymbolicLink;
    private Boolean isOther;
    private UserPrincipal owner;

    public FileAttribute(Path filePath) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
        this.basePath = filePath;
        this.creationTime = attr.creationTime();
        this.lastAccessTime = attr.lastAccessTime();
        this.lastModifiedTime = attr.lastModifiedTime();
        this.isDirectory = attr.isDirectory();
        this.isOther = attr.isOther();
        this.isRegularFile = attr.isRegularFile();
        this.isSymbolicLink = attr.isSymbolicLink();
        this.size = attr.size();
        this.owner = Files.getOwner(filePath);
    }
}
