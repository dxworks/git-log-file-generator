package com.denisfeier.blacklist.entity;

public class BlackListEntityFile extends BlackListEntity {

    public BlackListEntityFile(String path) {
        super(path);
    }

    @Override
    public boolean match(String path) {
        return false;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BlackListEntityFile{");
        sb.append(this.getPath()).append('}');
        return sb.toString();
    }
}
