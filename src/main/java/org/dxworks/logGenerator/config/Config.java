package org.dxworks.logGenerator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.IOException;
import java.nio.file.Path;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Config {
    public Threshold threshold;
//    public String rootDir;

    public static Config createConfig(Path path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(path.toFile(), Config.class);
    }
}
