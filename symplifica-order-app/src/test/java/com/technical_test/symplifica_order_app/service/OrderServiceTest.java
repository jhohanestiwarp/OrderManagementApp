package com.technical_test.symplifica_order_app.service;

import com.technical_test.symplifica_order_app.model.Product;
import com.technical_test.symplifica_order_app.model.constants.Constants;
import com.technical_test.symplifica_order_app.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    private static final String TEST = "";
    private Product product1;
    private Product product2;
    private Product product3;
    List<Integer> addedProducts;

    @BeforeEach
    void setUp() {
        addedProducts = Arrays.asList(2, 3);
        product1 = new Product(2, "Product 1", TEST, TEST, 20);
        product2 = new Product(3, "Product 2", TEST, TEST, 35);
        product3 = new Product(4, "Product 3", TEST, TEST, 56);
    }

    @Test
    void testSave() {
        List<Product> products = Arrays.asList(product1, product2, product3);

        when(orderRepository.findMaxOrderId()).thenReturn(10);
        when(productService.findAllById(addedProducts)).thenReturn(products);

        boolean result = orderService.save(addedProducts);

        assertTrue(result);
        verify(orderRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testGetProducts_ActionAdd() {
        List<Integer> productIds = Arrays.asList(2, 3);
        List<Product> allProducts = Arrays.asList(product1, product2, product3);

        when(productService.findAll()).thenReturn(allProducts);

        Map<String, List<Product>> result = orderService.getProducts(Constants.actionAdd, addedProducts, productIds);

        assertEquals(2, result.get("rightProducts").size());
        assertEquals(1, result.get("leftProducts").size());

        verify(productService, times(1)).findAll();
    }

    @Test
    void testGetProducts_ActionRemove() {
        List<Integer> productIds = Arrays.asList(2, 3);
        List<Product> allProducts = Arrays.asList(product1, product2, product3);

        when(productService.findAll()).thenReturn(allProducts);

        Map<String, List<Product>> result = orderService.getProducts(Constants.actionRemove, addedProducts, productIds);

        assertEquals(0, result.get("rightProducts").size());
        assertEquals(3, result.get("leftProducts").size());

        verify(productService, times(1)).findAll();
    }
}