package com.spring.practice.dreamshop.service.carts;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.Cart;
import com.spring.practice.dreamshop.repository.CartItemRepository;
import com.spring.practice.dreamshop.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository _cart;
    private final CartItemRepository _cartItem;

    @Override
    public Cart read(Long id) {
        Cart cart = _cart.findById(id).orElseThrow(() -> new NotFoundException("Cart not found"));

        BigDecimal amount = cart.getAmount();
        cart.setAmount(amount);
        return _cart.save(cart);
    }

    @Override
    public void clear(Long id) {
        Cart cart = read(id);

        _cartItem.deleteAllByCartId(id);
        cart.getItems().clear();
        _cart.deleteById(id);
    }

    @Override
    public BigDecimal getTotal_Price(Long id) {
        Cart cart = read(id);
        return cart.getAmount();
    }

}
