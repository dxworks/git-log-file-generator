package org.dxworks.ignorer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class IgnorerBuilder {

    private final List<String> globs;

    private final FileSystem fileSystem = FileSystems.getDefault();

    private final Logger logger = LoggerFactory.getLogger(IgnorerBuilder.class);

    public IgnorerBuilder(List<String> globs) {
        this.globs = globs;
    }

    public IgnorerBuilder(Path path) {
        this.globs = this.getStrings(path);
    }

    public Ignorer compile() {
        this.logger.info("Compile globs");
        List<PathMatcher> blackMatchersGlobs = this.preCompile(true);
        List<PathMatcher> whiteMatchersGlobs = this.preCompile(false);
        this.logger.info("Globs compilation finished successfully");
        return new Ignorer(blackMatchersGlobs, whiteMatchersGlobs);
    }

    private List<PathMatcher> preCompile(boolean isNegative) {
        this.logger.info("Precompile globs");
        return this.globs
                .stream()
                .filter(glob -> {
                    if (isNegative)
                        return !glob.startsWith("!");
                    else
                        return glob.startsWith("!");
                })
                .map(glob -> {
                    if (!isNegative)
                        return glob.substring(1);
                    else return glob;
                })
                .map(glob -> "glob:" + glob)
                .map(fileSystem::getPathMatcher)
                .collect(Collectors.toList());
    }

    private List<String> getStrings(Path path) {
        this.logger.info("Fetch globs from user path: {}", path.toAbsolutePath());
        List<String> fileItems = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(path.toString()))) {
            String globItem;
            while ((globItem = br.readLine()) != null) {
                fileItems.add(globItem);
            }
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }

        List<String> globs = fileItems
                .stream()
                .map(String::trim)
                .map(glob -> glob.replaceAll(" ", ""))
                .filter(line -> !line.isEmpty())
                .filter(line -> !line.startsWith("#"))
                .collect(Collectors.toList());

        this.logger.info("Globs list: {}", globs);

        return globs;
    }

    public List<String> getGlobs() {
        return globs;
    }
}
