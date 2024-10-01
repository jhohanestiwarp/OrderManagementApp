package com.technical_test.symplifica_order_app.service;

import com.technical_test.symplifica_order_app.model.Product;
import com.technical_test.symplifica_order_app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllById(List <Integer> ids) {
        return productRepository.findAllById(ids);
    }
}
