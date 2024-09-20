package com.backend.ecommerce.service;

import com.backend.ecommerce.entity.Color;

import java.util.List;

public interface ColorService {
    Color createColor(Color color);
    List<Color> getAllColors();
}
