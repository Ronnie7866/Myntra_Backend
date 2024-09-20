package com.backend.ecommerce.implementation;

import com.backend.ecommerce.entity.Size;
import com.backend.ecommerce.repository.SizeRepository;
import com.backend.ecommerce.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImplementation implements SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    public Size createSize(Size size) {
        return sizeRepository.save(size);
    }

    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }
}