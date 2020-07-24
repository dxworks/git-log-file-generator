package com.denisfeier.blacklist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public abstract class BlackListEntity {

    private String path;

    public BlackListEntity(String path) {
        this.path = path;
    }

    public abstract boolean match(String path);
}
