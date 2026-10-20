package com.ozagrimate.util;

import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;

/**
 * Inspiration taken from <a href="https://stackoverflow.com/questions/46402714/not-able-to-get-json-file-from-resource-folder-using-relative-path">Stack Overflow</a>
 * And <a href="https://examples.javacodegeeks.com/jsonnode-to-map-conversion/">here</a>
 * And finally <a href="https://www.baeldung.com/jackson-object-mapper-tutorial#5-creating-java-map-from-json-string">here</a>
 */
public class LoadServiceConfiguration {

    private static final String configPath = "serviceConfig.json";

    public static ServiceConfig[] loadServiceConfig() {
        InputStream stream = LoadServiceConfiguration.class.getClassLoader().getResourceAsStream(configPath);
        return new ObjectMapper().readValue(stream, ServiceConfig[].class);
    }
}
