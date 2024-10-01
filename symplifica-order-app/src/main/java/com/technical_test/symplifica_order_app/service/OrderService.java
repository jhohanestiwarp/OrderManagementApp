package com.technical_test.symplifica_order_app.service;

import com.technical_test.symplifica_order_app.model.Order;
import com.technical_test.symplifica_order_app.model.Product;
import com.technical_test.symplifica_order_app.model.constants.Constants;
import com.technical_test.symplifica_order_app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public boolean save(List<Integer> addedProducts) {
        try {
            Integer lastOrderId = orderRepository.findMaxOrderId();
            Integer newOrderId = (lastOrderId != null) ? lastOrderId + 1 : 1;

            List<Product> products = productService.findAllById(addedProducts);

            List<Order> orders = new ArrayList<>();

            products.forEach(product -> {
                Order order = new Order();
                order.setId(newOrderId);
                order.setProduct(product);
                orders.add(order);
            });

            orderRepository.saveAll(orders);
        } catch (Exception e) {
            log.error("Error saving order");
            return false;
        }
        return true;
    }

    public Map<String, List<Product>> getProducts(String action, List<Integer> addedProducts, List<Integer> productIds) {
        List<Product> allProducts = productService.findAll();

        List<Product> leftList = new ArrayList<>();
        List<Product> rightList = new ArrayList<>();

        Map<String, List<Product>> resultMap = new HashMap<>();

        allProducts.forEach(product -> {
            if (Constants.actionAdd.equals(action)) {
                if (productIds.contains(product.getId()) || addedProducts.contains(product.getId())) {
                    rightList.add(product);
                } else {
                    leftList.add(product);
                }
            }
            else if (Constants.actionRemove.equals(action)) {
                if (!productIds.contains(product.getId()) && addedProducts.contains(product.getId())) {
                    rightList.add(product);
                } else {
                    leftList.add(product);
                }
            }
        });

        resultMap.put("leftProducts", leftList);
        resultMap.put("rightProducts", rightList);

        return resultMap;
    }
}
