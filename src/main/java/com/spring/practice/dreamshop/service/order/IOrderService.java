package com.spring.practice.dreamshop.service.order;

import java.util.List;
import com.spring.practice.dreamshop.dto.OrderDTO;

public interface IOrderService {
    OrderDTO create(Long user_id);

    OrderDTO read(Long id);

    List<OrderDTO> getUserOrders(Long user_id);

}
