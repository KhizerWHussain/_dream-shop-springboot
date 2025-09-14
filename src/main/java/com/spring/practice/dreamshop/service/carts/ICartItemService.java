package com.spring.practice.dreamshop.service.carts;

import com.spring.practice.dreamshop.model.CartItem;

public interface ICartItemService {
    void add(Long cart_id, Long product_id, int quantity);

    void remove(Long cart_id, Long product_id);

    void updateQuantity(Long cart_id, Long product_id, int quantity);

    CartItem getItem(Long cart_id, Long product_id);
}
