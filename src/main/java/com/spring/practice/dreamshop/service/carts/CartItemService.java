package com.spring.practice.dreamshop.service.carts;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.Cart;
import com.spring.practice.dreamshop.model.CartItem;
import com.spring.practice.dreamshop.model.Product;
import com.spring.practice.dreamshop.repository.CartItemRepository;
import com.spring.practice.dreamshop.repository.CartRepository;
import com.spring.practice.dreamshop.service.product.IProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartRepository _cart;
    private final CartItemRepository _cartItem;
    private final IProductService productInterface;
    private final ICartService cartInterface;

    @Override
    public void add(Long cart_id, Long product_id, int quantity) {
        Cart cart = cartInterface.read(cart_id);
        Product product = productInterface.read(product_id);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product_id))
                .findFirst()
                .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnit_price(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotal_Price();
        cart.add(cartItem);

        _cartItem.save(cartItem);
        _cart.save(cart);
    }

    @Override
    public void remove(Long cart_id, Long product_id) {
        Cart cart = cartInterface.read(cart_id);
        CartItem itemToRemove = getItem(cart_id, product_id);

        cart.remove(itemToRemove);
        _cart.save(cart);
    }

    @Override
    public void updateQuantity(Long cart_id, Long product_id, int quantity) {
        Cart cart = cartInterface.read(cart_id);

        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product_id))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnit_price(item.getProduct().getPrice());
                    item.setTotal_Price();
                });

        // BigDecimal amount = cart.getAmount();
        BigDecimal amount = cart.getItems().stream()
                .map(CartItem::getTotal_price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setAmount(amount);
        _cart.save(cart);
    }

    @Override
    public CartItem getItem(Long cart_id, Long product_id) {
        Cart cart = cartInterface.read(cart_id);

        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product_id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }
}
