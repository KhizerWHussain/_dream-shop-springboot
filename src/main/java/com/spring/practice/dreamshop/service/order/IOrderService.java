package com.spring.practice.dreamshop.service.order;

import java.util.List;
import com.spring.practice.dreamshop.model.Order;

public interface IOrderService {
    Order create(Long user_id);

    Order read(Long id);

    List<Order> getUserOrders(Long user_id);

}
