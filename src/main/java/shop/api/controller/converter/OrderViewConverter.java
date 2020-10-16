package shop.api.controller.converter;

import static java.util.stream.Collectors.toList;
import org.springframework.stereotype.Component;
import shop.api.model.OrderItemView;
import shop.api.model.OrderView;
import shop.repository.model.Order;
import shop.repository.model.OrderItem;

/**
 * Convert internal order representation to REST API OrderView
 */
@Component
public class OrderViewConverter {

  public OrderView convertToOrderView(Order order) {
    return OrderView.builder()
      .id(order.getId())
      .email(order.getBuyerEmail())
      .orderDate(order.getDate())
      .items(order.getItems().stream().map(this::convertToOrderItemView).collect(toList()))
      .amount(order.getAmount())
      .build();
  }

  private OrderItemView convertToOrderItemView(OrderItem orderItem) {
    return OrderItemView.builder()
      .price(orderItem.getPrice())
      .quantity(orderItem.getQuantity())
      .productId(orderItem.getProduct().getId())
      .productName(orderItem.getProduct().getName())
      .build();
  }
}
