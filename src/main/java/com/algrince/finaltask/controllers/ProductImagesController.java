package com.algrince.finaltask.controllers;


import com.algrince.finaltask.services.ProductImagesService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("images")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductImagesController {

    private final ProductImagesService productImagesService;

    @GetMapping
    public String getImage() {
        try {
            String image = productImagesService.getImage();
            return image;
        } catch (IOException e){
            System.out.println(e);
        }
        return null;
    }
}
