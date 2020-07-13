package com.denisfeier.entity;

import java.io.IOException;
import java.nio.file.Path;

public class File extends BaseFile{

    public File(Path basePath) throws IOException {
        super(basePath);
    }
}
