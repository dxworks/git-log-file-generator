package com.denisfeier.blacklist.entity;


public class BlackListEntityDirectory extends BlackListEntity {

    public BlackListEntityDirectory(String path) {
        super(path);
    }

    @Override
    public boolean match(String path) {
        return false;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BlackListEntityDirectory{");
        sb.append(this.getPath()).append('}');
        return sb.toString();
    }
}
