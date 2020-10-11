package com.denisfeier.ignorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.LinkedList;
import java.util.List;

public class Ignorer {

    private final List<PathMatcher> blackMatchersGlobs;
    private final List<PathMatcher> whiteMatchersGlobs;

    public Ignorer(List<PathMatcher> blackMatchersGlobs, List<PathMatcher> whiteMatchersGlobs) {
        this.blackMatchersGlobs = blackMatchersGlobs;
        this.whiteMatchersGlobs = whiteMatchersGlobs;
    }

    public Ignorer() {
        this.blackMatchersGlobs = new LinkedList<>();
        this.whiteMatchersGlobs = new LinkedList<>();
    }

    public boolean accept(String path) {
        boolean whiteGlobs = this.match(this.whiteMatchersGlobs, path);
        boolean blackGlobs = this.match(this.blackMatchersGlobs, path);
        return whiteGlobs || !blackGlobs;
    }

    private boolean match(List<PathMatcher> globsMatcher, String path) {
        return globsMatcher.stream()
                .map(glob -> glob.matches(Path.of(path)))
                .reduce(false, (aBoolean, aBoolean2) -> aBoolean || aBoolean2);
    }

    public List<PathMatcher> getBlackMatchersGlobs() {
        return blackMatchersGlobs;
    }

    public List<PathMatcher> getWhiteMatchersGlobs() {
        return whiteMatchersGlobs;
    }
}
