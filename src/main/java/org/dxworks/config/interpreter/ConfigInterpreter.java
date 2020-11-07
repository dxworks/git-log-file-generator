package org.dxworks.config.interpreter;

import org.dxworks.config.Config;
import org.dxworks.config.Threshold;

import java.io.IOException;
import java.nio.file.Path;

public class ConfigInterpreter {

    public static Config getFullConfig() throws IOException {
        if (!System.getProperty("fileConfig", "").equals("")) {
            Path ymlConfigPath = Path.of(System.getProperty("fileConfig"));
            Config config = Config.createConfig(ymlConfigPath);
            return config;
        } else {
            int hours = Integer.parseInt(System.getProperty("config.threshold.hours", "0"));
            int minutes = Integer.parseInt(System.getProperty("config.threshold.minutes", "0"));
            int seconds = Integer.parseInt(System.getProperty("config.threshold.seconds", "0"));
            String rootDir = System.getProperty("config.rootDir");
            Threshold threshold = new Threshold(hours, minutes, seconds);
            return new Config(threshold, rootDir);
        }


//        return Config();
    }

}
