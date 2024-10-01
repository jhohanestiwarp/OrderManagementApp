package com.technical_test.symplifica_order_app.repository;

import com.technical_test.symplifica_order_app.model.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Integer> {
}