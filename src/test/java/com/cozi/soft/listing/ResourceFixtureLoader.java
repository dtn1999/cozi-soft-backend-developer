package com.cozi.soft.listing;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@UtilityClass
public class ResourceFixtureLoader {
    private final ObjectMapper mapper = ObjectMapperUtils.createObjectMapper();

    public <T> T loadResourceAsString(String resourcePath, Class<T> clazz) {
        ResourceLoader resourceLoader = new FileSystemResourceLoader();
        Resource resource = resourceLoader.getResource(resourcePath);
        try (var inputStream = resource.getInputStream()) {
            return mapper.readValue(inputStream, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load resource: " + resourcePath, e);
        }
    }

}
