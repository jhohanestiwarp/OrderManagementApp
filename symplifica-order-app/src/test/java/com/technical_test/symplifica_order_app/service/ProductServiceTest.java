package com.technical_test.symplifica_order_app.service;

import com.technical_test.symplifica_order_app.model.Product;
import com.technical_test.symplifica_order_app.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private static final String TEST = "";
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product(2, "Product 1", TEST, TEST, 20);
        product2 = new Product(3, "Product 2", TEST, TEST, 35);
    }

    @Test
    void testFindAll() {
        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        when(productRepository.save(product1)).thenReturn(product1);

        Product result = productService.save(product1);

        assertNotNull(result);
        assertEquals("Product 1", result.getName());
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void testFindAllById() {
        List<Integer> ids = Arrays.asList(1, 2);

        when(productRepository.findAllById(ids)).thenReturn(Arrays.asList(product1, product2));

        List<Product> result = productService.findAllById(ids);

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getId());
        assertEquals(3, result.get(1).getId());
        verify(productRepository, times(1)).findAllById(ids);
    }
}