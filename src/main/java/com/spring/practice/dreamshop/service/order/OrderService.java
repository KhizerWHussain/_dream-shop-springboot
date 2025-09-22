package com.spring.practice.dreamshop.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.spring.practice.dreamshop.dto.OrderDTO;
import com.spring.practice.dreamshop.enums.OrderStatus;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.Cart;
import com.spring.practice.dreamshop.model.Order;
import com.spring.practice.dreamshop.model.OrderItem;
import com.spring.practice.dreamshop.model.Product;
import com.spring.practice.dreamshop.repository.OrderRepository;
import com.spring.practice.dreamshop.repository.ProductRepository;
import com.spring.practice.dreamshop.service.carts.ICartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {
    private final OrderRepository _order;
    private final ProductRepository _product;
    private final ICartService cartInterface;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public OrderDTO create(Long user_id) {
        Cart cart = cartInterface.findByUserId(user_id);
        Order order = init(cart);

        System.out.println("order ==? " + order);

        List<OrderItem> orderItems = create_order_items(order, cart);
        order.setItems(new HashSet<>(orderItems));
        order.setAmount(calculate_amount(orderItems));

        System.out.println("order ==? now =+> " + order);

        Order savedOrder = _order.save(order);
        cartInterface.clear(cart.getId());

        System.out.println("savedOrder ==> " + savedOrder);

        OrderDTO savedOrderDto = convert_to_dto(savedOrder);

        System.out.println("savedOrderDto ==> " + savedOrderDto);

        return savedOrderDto;
    }

    @Override
    public OrderDTO read(Long id) {
        return _order
                .findById(id)
                .map(this::convert_to_dto)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public List<OrderDTO> getUserOrders(Long user_id) {
        List<Order> orders = _order.findByUserId(user_id);
        return orders.stream().map(this::convert_to_dto).toList();

    }

    private Order init(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.Pending);
        order.setDatetime(LocalDate.now());
        return order;
    }

    private List<OrderItem> create_order_items(Order order, Cart cart) {
        return cart
                .getItems()
                .stream()
                .map(item -> {
                    Product product = item.getProduct();
                    product.setInventory(product.getInventory() - item.getQuantity());
                    _product.save(product);
                    return new OrderItem(item.getQuantity(), item.getUnit_price(), order, product);
                }).toList();
    }

    private BigDecimal calculate_amount(List<OrderItem> items) {
        return items
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderDTO convert_to_dto(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}
