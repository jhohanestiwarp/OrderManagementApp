package com.technical_test.symplifica_order_app.controller;

import com.technical_test.symplifica_order_app.model.Order;
import com.technical_test.symplifica_order_app.model.Product;
import com.technical_test.symplifica_order_app.service.OrderService;
import com.technical_test.symplifica_order_app.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private ProductService productService;

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new BindingAwareModelMap();
    }

    @Test
    public void testViewOrder() {
        Order order = new Order();
        Product product = new Product();
        List<Order> orders = Arrays.asList(order);
        List<Product> products = Arrays.asList(product);

        when(orderService.findAll()).thenReturn(orders);
        when(productService.findAll()).thenReturn(products);

        String result = orderController.viewOrder(model);

        assertEquals("redirect:/orders/create", result);

        verify(orderService, times(1)).findAll();
        verify(productService, times(1)).findAll();

        assertEquals(orders, model.getAttribute("orders"));
        assertEquals(products, model.getAttribute("leftProducts"));
    }

    @Test
    public void testSaveOrder_NoProducts() {
        orderController.addedProducts = new ArrayList<>();

        String result = orderController.saveOrder(model);

        assertEquals("orders", result);
        assertEquals("There are no products selected.", model.asMap().get("messageWarning"));
    }

    @Test
    public void testSaveOrder_WithProducts() {
        orderController.addedProducts = new ArrayList<>(List.of(1, 2, 3));
        when(orderService.save(anyList())).thenReturn(true);

        String result = orderController.saveOrder(model);

        assertEquals("orders", result);
        assertEquals("Order saved successfully.", model.asMap().get("messageSuccess"));
        verify(orderService, times(1)).save(anyList());
    }

    @Test
    public void testToggleCheckProducts() {
        orderController.addedProducts = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> productIds = List.of(1, 2);
        Map<String, List<Product>> productsMap = mock(Map.class);
        when(orderService.getProducts(anyString(), anyList(), anyList())).thenReturn(productsMap);
        when(productsMap.get("rightProducts")).thenReturn(new ArrayList<>());
        when(productsMap.get("leftProducts")).thenReturn(new ArrayList<>());

        String result = orderController.toggleCheckProducts(productIds, "actionType", model);

        assertEquals("orders", result);
        verify(orderService, times(1)).getProducts(anyString(), anyList(), anyList());
    }
}
