package com.algrince.finaltask.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImagesService {

    public String getImage(Long id) throws IOException {

        String defaultPath = "src/main/resources/images";
        String imagePath = "/default.png";

        if (id != null) {
            imagePath = "/" + id + ".png";
            Path path = Paths.get(defaultPath + imagePath);
            if (Files.notExists(path)) {
                log.warn("Image does not exist with path: " + defaultPath + imagePath);
                imagePath = "/default.png";
            }
        }

        byte[] fileContent = FileUtils.readFileToByteArray(
                new File(defaultPath + imagePath));

        return Base64.getEncoder().encodeToString(fileContent);
    }

}
