package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entity.Color;
import com.backend.ecommerce.implementation.ColorServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
public class ColorController {
    @Autowired
    private ColorServiceImplementation colorService;

    @PostMapping
    public Color createColor(@RequestBody Color color) {
        return colorService.createColor(color);
    }

    @GetMapping
    public List<Color> getColors() {
        return colorService.getAllColors();
    }
}