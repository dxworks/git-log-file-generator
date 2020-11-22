package org.dxworks.logGenerator.config.interpreter;

import org.dxworks.logGenerator.config.Config;
import org.dxworks.logGenerator.config.Threshold;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigInterpreter {

    public static Config getFullConfig() throws IOException {
        if (!System.getProperty("fileConfig", "").equals("")) {
            Path ymlConfigPath = Paths.get(System.getProperty("fileConfig"));
            Config config = Config.createConfig(ymlConfigPath);
            return config;
        } else {
            int hours = Integer.parseInt(System.getProperty("config.threshold.hours", "0"));
            int minutes = Integer.parseInt(System.getProperty("config.threshold.minutes", "0"));
            int seconds = Integer.parseInt(System.getProperty("config.threshold.seconds", "0"));
            String rootDir = System.getProperty("config.rootDir");
            Threshold threshold = new Threshold(hours, minutes, seconds);
            return new Config(threshold);
        }


//        return Config();
    }

}
