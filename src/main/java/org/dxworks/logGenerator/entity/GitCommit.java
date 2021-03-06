package org.dxworks.logGenerator.entity;


import org.dxworks.logGenerator.exception.EmptyAttributesListException;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import  org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@ToString
final public class GitCommit {

    private String commitID;
    private String author;
    private String email;
    private LocalDateTime date;
    private String message;
    private List<FileAttribute> fileAttributes;
    private boolean isAdded;

    public GitCommit(List<FileAttribute> fileAttributes) throws EmptyAttributesListException {

        if (fileAttributes.size() == 0) {
            throw new EmptyAttributesListException("The list of attributes is empty");
        }

        FileAttribute fileAttribute = fileAttributes.get(0);

        this.commitID = RandomStringUtils.randomAlphanumeric(42);

        this.author = fileAttribute.getOwner().getName();

        this.email = this.author + "@generated.com";

        this.date = fileAttribute.getLastModifiedTime();

        this.message = "Generated";

        this.fileAttributes = fileAttributes;

        this.isAdded = false;

    }

    public String stringCommit() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("commit:").append(this.commitID).append("\n");

        stringBuilder.append("author:").append(this.author).append("\n");

        stringBuilder.append("email:").append(this.email).append("\n");

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss +0000");
        String formattedDate = date.format(myFormatObj);
        stringBuilder.append("date:").append(formattedDate).append("\n");

        stringBuilder.append("message:").append("\n").append(this.message).append("\n");

        stringBuilder.append("numstat:").append("\n\n");

        stringBuilder.append(this.numstatBuilder());

        return stringBuilder.toString();
    }

    private StringBuilder numstatBuilder() {
        StringBuilder builder = new StringBuilder();

        for (FileAttribute fa: this.fileAttributes) {
            String id = RandomStringUtils.randomAlphanumeric(7);
            Path pathNumstat = fa.getRelativePath().orElse(fa.getBasePath());

            String stringPath = FilenameUtils.separatorsToUnix(pathNumstat.toString());

            builder.append(":000000\t000000\t0000000\t")
                    .append(id)
                    .append("\t")
                    .append(this.isAdded ? "A" : "M")
                    .append("\t")
                    .append(stringPath)
                    .append("\n");
        }

        for (FileAttribute fa: this.fileAttributes) {

            Path pathNumstat = fa.getRelativePath().orElse(fa.getBasePath());

            String stringPath = FilenameUtils.separatorsToUnix(pathNumstat.toString());

            long lines = fa.getLines();
            builder.append(lines)
                    .append("\t")
                    .append(this.isAdded ? "0" : lines)
                    .append("\t")
                    .append(stringPath)
                    .append("\n");
        }

        return builder;
    }
}
