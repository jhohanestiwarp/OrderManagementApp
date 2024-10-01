package com.technical_test.symplifica_order_app.controller;

import com.technical_test.symplifica_order_app.model.Product;
import com.technical_test.symplifica_order_app.service.OrderService;
import com.technical_test.symplifica_order_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
@SessionAttributes("addedProducts")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    public List<Integer> addedProducts;

    @ModelAttribute("addedProducts")
    public List<Product> initializeAddedProducts() {
        return new ArrayList<>();
    }

    @GetMapping("")
    public String viewOrder(Model model) {
        initModel(model);
        return "redirect:/orders/create";
    }

    @GetMapping("/create")
        public String listOrdes(Model model) {
        initModel(model);
        return "orders";
    }

    @PostMapping("/save")
    public String saveOrder(Model model) {
        if (addedProducts.isEmpty()) {
            model.addAttribute("messageWarning", "There are no products selected.");
        } else {
            boolean result = orderService.save(addedProducts);

            if(result){
                addedProducts.clear();
                model.addAttribute("messageSuccess", "Order saved successfully.");
            } else{
                model.addAttribute("messageError", "The order could not be saved.");
            }
        }

        initModel(model);

        return "orders";
    }

    @PostMapping("/toggleCheck")
    public String toggleCheckProducts(@RequestParam(value = "productIds", required = false) List<Integer> productIds,
                                      @RequestParam String actionType, Model model) {
        if (productIds == null) {
            productIds = new ArrayList<>();
        }

        Map<String, List<Product>> productsMap = orderService.getProducts(actionType, addedProducts, productIds);

        addedProducts.clear();
        addedProducts.addAll(productsMap.get("rightProducts").stream().map(Product::getId).toList());

        model.addAttribute("leftProducts", productsMap.get("leftProducts"));
        model.addAttribute("rightProducts", productsMap.get("rightProducts"));
        model.addAttribute("orders", orderService.findAll());

        return "orders";
    }

    private void initModel(Model model) {
        if (addedProducts == null || addedProducts.isEmpty()) {
            addedProducts = new ArrayList<>();
        }
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("leftProducts", productService.findAll());
    }
}
