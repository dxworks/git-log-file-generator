package com.denisfeier.blacklist;

import com.denisfeier.blacklist.entity.BlackListEntity;
import com.denisfeier.blacklist.entity.BlackListEntityDirectory;
import com.denisfeier.blacklist.entity.BlackListEntityFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class BlackListReaderFile implements BlackListReader {

    @Override
    public List<BlackListEntity> createBlackList(String from) {

        List<BlackListEntity> blackList = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(from))) {
            String blackListLine;
            String separators = "[/\\\\]";
            String filePath = "^([a-zA-Z][a-zA-Z0-9]+[/\\\\])*[a-z0-9A-Z*]*\\.[a-zA-Z0-9*]+$";
            String dirPath = "([a-zA-Z.][a-zA-Z0-9]+[/\\\\])+$";
            while ((blackListLine = br.readLine()) != null) {

                if (blackListLine.startsWith("#") || blackListLine.isBlank())
                    continue;

                String withOsSeparator = blackListLine
                        .replaceAll(separators, File.separator);

                BlackListEntity entity;
                if (Pattern.matches(dirPath, withOsSeparator)) {
                    entity = new BlackListEntityDirectory(withOsSeparator);
                    blackList.add(entity);
                } else {
                    if (Pattern.matches(filePath, withOsSeparator)) {
                        entity = new BlackListEntityFile(withOsSeparator);
                        blackList.add(entity);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blackList;
    }
}
