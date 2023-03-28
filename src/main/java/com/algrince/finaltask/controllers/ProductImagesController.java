package com.algrince.finaltask.controllers;


import com.algrince.finaltask.services.ProductImagesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class ProductImagesController {

    private final ProductImagesService productImagesService;

    @GetMapping
    public String getImage(@RequestParam(required = false) Long id) {
        try {
            return productImagesService.getImage(id);
        } catch (IOException e){
            log.warn(e.getLocalizedMessage());
        }
        return null;
    }
}
