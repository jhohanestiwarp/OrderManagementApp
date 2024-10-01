package com.technical_test.symplifica_order_app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderId implements Serializable {
    private Integer id;
    private Integer product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId = (OrderId) o;
        return Objects.equals(id, orderId.id) && Objects.equals(product, orderId.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product);
    }
}
