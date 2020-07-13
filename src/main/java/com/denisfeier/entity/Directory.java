package com.denisfeier.entity;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Directory extends BaseFile {

    private List<BaseFile> subFiles;

    public Directory(Path basePath) throws IOException {
        super(basePath);
        this.subFiles = new ArrayList<>();
    }

    public List<BaseFile> getSubFiles() {
        return subFiles;
    }

    public void setSubFiles(List<BaseFile> subFiles) {
        this.subFiles = subFiles;
    }

    public void addSubFile(BaseFile baseFile) {
        subFiles.add(baseFile);
    }
}
