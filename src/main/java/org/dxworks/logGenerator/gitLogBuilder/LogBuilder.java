package org.dxworks.logGenerator.gitLogBuilder;

import org.dxworks.logGenerator.config.Config;
import org.dxworks.logGenerator.entity.FileAttribute;
import org.dxworks.logGenerator.entity.GitCommit;
import org.dxworks.logGenerator.exception.EmptyAttributesListException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

public class LogBuilder {

    private final static String pattern = "yyyy-MM-dd HH:mm:ss";
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public static Map<Long, List<FileAttribute>> sortedByLastModifiedTime(List<FileAttribute> fileAttributes, int hours, int minutes, int seconds) {

        int calculatedHours = hours * 3600;
        int calculatedMinutes = minutes * 60;

        long step = (calculatedHours <= 0
                ? 1 : calculatedHours) * (calculatedMinutes <= 0
                ? 1 : calculatedMinutes) * (seconds <= 0
                ? 1 : seconds);

        System.out.println("Step:" + step);

        List<FileAttribute> sortedList = fileAttributes.stream().sorted(
                Comparator.comparing(FileAttribute::getLastModifiedTime)
        ).collect(Collectors.toList());

        Map<Long, List<FileAttribute>> sortedMap = new TreeMap<>();

        for (FileAttribute f : sortedList) {
            long key = (f.getLastModifiedTime().toInstant(ZoneOffset.UTC).toEpochMilli() / 1000) / step;

            if (sortedMap.containsKey(key)) {
                sortedMap.get(key).add(f);
            } else {
                List<FileAttribute> newList = new LinkedList<>();
                newList.add(f);
                sortedMap.put(key, newList);
            }
        }

        return sortedMap;
    }

    public static List<FileAttribute> getModifiedFilesOnly(List<FileAttribute> fileAttributeList) {
        return fileAttributeList
                .stream()
                .filter(fileAttribute -> {
                    String formatted1 = simpleDateFormat.format(new Date(fileAttribute.getLastModifiedTime().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli()));
                    String formatted2 = simpleDateFormat.format(new Date(fileAttribute.getCreationTime().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli()));
                    return !formatted1.equals(formatted2);
                }
                ).collect(Collectors.toList());
    }

    private static Map<Long, List<FileAttribute>> configuredSortedListOfFileAttribute(Config config, List<FileAttribute> fileAttributes) {

        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        if (config.threshold != null) {
            hours = config.threshold.getHours();
            minutes = config.threshold.getMinutes();
            seconds = config.threshold.getSeconds();
        }

        return LogBuilder.sortedByLastModifiedTime(
                fileAttributes,
                hours,
                minutes,
                seconds);
    }

    public static List<GitCommit> createGitCommitLogs(List<FileAttribute> listOfFiles, Config config, boolean added) throws EmptyAttributesListException {
        Map<Long, List<FileAttribute>> sortedByLastModifiedTime =
                configuredSortedListOfFileAttribute(config, listOfFiles);

        List<GitCommit> gitCommits = new LinkedList<>();

        for (Map.Entry<Long, List<FileAttribute>> entry : sortedByLastModifiedTime.entrySet()) {
            GitCommit gitCommit = new GitCommit(entry.getValue());
            gitCommit.setAdded(added);
            gitCommits.add(gitCommit);
        }

        return gitCommits;
    }

    public static void showCommits(List<FileAttribute> attributeList, Config config, boolean added) throws EmptyAttributesListException {
        if (attributeList.size() != 0) {
            List<GitCommit> gitCommitsForJustAdded = LogBuilder.createGitCommitLogs(attributeList, config, added);

            gitCommitsForJustAdded.forEach(gitCommit -> System.out.println(gitCommit.stringCommit()));
        }
    }

    public static void outputInFile(Path outputPath, List<GitCommit> gitCommitsForJustAdded) throws EmptyAttributesListException, IOException {
        if (gitCommitsForJustAdded.size() != 0) {

            FileWriter myWriter = new FileWriter(outputPath.toFile());
            gitCommitsForJustAdded.forEach(gitCommit -> {
                try {
                    myWriter.write(gitCommit.stringCommit());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            myWriter.close();
        }
    }
}
