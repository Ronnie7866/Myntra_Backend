package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entity.Size;
import com.backend.ecommerce.implementation.SizeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {
    @Autowired
    private SizeServiceImplementation sizeService;

    @PostMapping
    public Size createSize(@RequestBody Size size) {
        return sizeService.createSize(size);
    }

    @GetMapping
    public List<Size> getSizes() {
        return sizeService.getAllSizes();
    }
}