package com.denisfeier.blacklist;

import com.denisfeier.blacklist.entity.BlackListEntity;

import java.util.List;

public interface BlackListReader {

    List<BlackListEntity> createBlackList(String from);

}
