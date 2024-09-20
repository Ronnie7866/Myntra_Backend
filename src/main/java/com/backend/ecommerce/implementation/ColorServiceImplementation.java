package com.backend.ecommerce.implementation;

import com.backend.ecommerce.entity.Color;
import com.backend.ecommerce.repository.ColorRepository;
import com.backend.ecommerce.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImplementation implements ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public Color createColor(Color color) {
        return colorRepository.save(color);
    }

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }
}