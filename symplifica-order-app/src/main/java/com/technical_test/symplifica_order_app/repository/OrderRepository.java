package com.technical_test.symplifica_order_app.repository;

import com.technical_test.symplifica_order_app.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ListCrudRepository<Order, Integer> {
    @Query("SELECT MAX(o.id) FROM Order o")
    Integer findMaxOrderId();
}
