package com.backend.ecommerce.service;

import com.backend.ecommerce.entity.Size;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SizeService {
    Size createSize(Size size);
    List<Size> getAllSizes();
}