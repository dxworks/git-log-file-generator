package com.denisfeier.entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;

public abstract class BaseFile {

    private Path basePath;
    private UserPrincipal owner;
    private FileAttributes fileAttributes;

    public BaseFile(Path basePath) throws IOException {
        this.basePath = basePath;
        this.owner = Files.getOwner(basePath);
        this.fileAttributes = new FileAttributes(basePath);
    }

    public Path getBasePath() {
        return basePath;
    }

    public void setBasePath(Path basePath) {
        this.basePath = basePath;
    }

    public UserPrincipal getOwner() {
        return owner;
    }

    public void setOwner(UserPrincipal owner) {
        this.owner = owner;
    }

    public FileAttributes getFileAttributes() {
        return fileAttributes;
    }

    public void setFileAttributes(FileAttributes fileAttributes) {
        this.fileAttributes = fileAttributes;
    }
}
