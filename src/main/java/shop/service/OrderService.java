package shop.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.repository.OrderRepository;
import shop.repository.model.Order;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderValidator orderValidator;

  public Order placeOrder(final Order order) {
    orderValidator.validateOrder(order);
    order.setDate(LocalDateTime.now(Universe.getClock()));
    order.getItems().forEach(orderItem -> orderItem.setOrder(order));

    return orderRepository.save(order);
  }

  public List<Order> listOrders(LocalDateTime from, LocalDateTime to) {
    return orderRepository.findOrdersByDateBetween(from, to);
  }

}
