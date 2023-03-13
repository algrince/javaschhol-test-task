package com.algrince.finaltask.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ProductImagesService {

    public String getImage() throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(
                new File("src/main/resources/images/default.png"));
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
