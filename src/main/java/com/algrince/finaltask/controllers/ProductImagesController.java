package com.algrince.finaltask.controllers;


import com.algrince.finaltask.services.ProductImagesService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("images")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductImagesController {

    private final ProductImagesService productImagesService;

    @GetMapping
    public String getImage(@RequestParam(required = false) Long id) {
        try {
            return productImagesService.getImage(id);
        } catch (IOException e){
            System.err.println(e);
        }
        return null;
    }
}
