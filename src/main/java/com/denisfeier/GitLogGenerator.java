package com.denisfeier;

import com.denisfeier.blacklist.BlackListReader;
import com.denisfeier.blacklist.BlackListReaderFile;
import com.denisfeier.blacklist.entity.BlackListEntity;
import com.denisfeier.entity.FileAttributes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GitLogGenerator {
    public static void main(String[] args) {
        String stringPath = args[0];
        String blackListPath = args[1];
        System.out.println(stringPath);
        System.out.println(blackListPath);
        BlackListReader reader = new BlackListReaderFile();
        for (BlackListEntity str: reader.createBlackList(blackListPath)) {
            System.out.println(str);
        }

//        Path path = Paths.get(stringPath);
//        try {
//            List<FileAttributes> fileAttributes = RecursiveScanner.dirScanning(path);
//            for (FileAttributes entity: fileAttributes) {
//                System.out.println(entity);
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}
