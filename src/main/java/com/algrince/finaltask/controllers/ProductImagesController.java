package com.algrince.finaltask.controllers;


import com.algrince.finaltask.services.ProductImagesService;

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
            return productImagesService.getImage();
        } catch (IOException e){
            System.err.println(e);
        }
        return null;
    }
}
