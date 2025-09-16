package com.spring.practice.dreamshop.service.carts;

import java.math.BigDecimal;
import com.spring.practice.dreamshop.model.Cart;

public interface ICartService {
    Cart read(Long id);

    void clear(Long id);

    BigDecimal getTotal_Price(Long id);

    Long init();
}
