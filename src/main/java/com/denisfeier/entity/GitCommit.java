package com.denisfeier.entity;


import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

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

    public GitCommit(List<FileAttribute> fileAttributes) {

        FileAttribute fileAttribute = fileAttributes.get(0);

        this.commitID = RandomStringUtils.randomAlphanumeric(42);

        this.author = fileAttribute.getOwner().getName();

        this.email = this.author + "@generated.com";

        this.date = LocalDateTime.ofInstant(
                fileAttribute.getCreationTime().toInstant(),
                ZoneId.systemDefault());

        this.message = "Generated";

        this.fileAttributes = fileAttributes;

    }

    public String stringCommit() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("commit:").append(this.commitID).append("\n");

        stringBuilder.append("author:").append(this.email).append("\n");

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
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
            builder.append(":000000\t000000\t0000000\t")
                    .append(id)
                    .append("\t")
                    .append("A")
                    .append("\t")
                    .append(pathNumstat.toString())
                    .append("\n");
        }

        for (FileAttribute fa: this.fileAttributes) {

            Path pathNumstat = fa.getRelativePath().orElse(fa.getBasePath());

            builder.append(fa.getLines())
                    .append("\t")
                    .append("0")
                    .append("\t")
                    .append(pathNumstat.toString())
                    .append("\n");
        }

        return builder;
    }
}
