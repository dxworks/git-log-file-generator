package org.dxworks.gitLogBuilder;

import org.dxworks.entity.FileAttribute;

import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

public class LogBuilder {

    public static Map<Long, List<FileAttribute>> sortedByLastModifiedTime(List<FileAttribute> fileAttributes, int hours, int minutes, int seconds) {

        int calculatedHours = hours * 3600;
        int calculatedMinutes = minutes * 60;

//        System.out.println(hours);
//        System.out.println(minutes);
//        System.out.println(seconds);

        long step = (calculatedHours <= 0
                ? 1 : calculatedHours) * (calculatedMinutes <= 0
                ? 1 : calculatedMinutes) * (seconds <= 0
                ? 1 : seconds) * 1000;

        System.out.println("Step:" + step);

        List<FileAttribute> sortedList = fileAttributes.stream().sorted(
                Comparator.comparing(FileAttribute::getLastModifiedTime)
        ).collect(Collectors.toList());

        Map<Long, List<FileAttribute>> sortedMap = new HashMap<>();

        for (FileAttribute f : sortedList) {
            long key = f.getLastModifiedTime().toInstant(ZoneOffset.UTC).toEpochMilli() / step;

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



}
