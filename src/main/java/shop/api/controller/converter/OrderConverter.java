package shop.api.controller.converter;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.api.model.OrderItemRequest;
import shop.api.model.OrderRequest;
import shop.repository.model.Order;
import shop.repository.model.OrderItem;
import shop.service.ProductService;

/**
 * Convert OrderCreation request to Order representation.
 *
 */
@Component
@RequiredArgsConstructor
public class OrderConverter {

  private final ProductService productService;

  public Order convertToOrder(OrderRequest orderData) {
    return Order.builder()
      .buyerEmail(orderData.getEmail())
      .items(convertToItems(orderData.getItems()))
      .build();
  }

  private List<OrderItem> convertToItems(List<OrderItemRequest> items) {
    if (nonNull(items)) {
      return items.stream()
        .map(this::convertToItem)
        .collect(toList());
    } else {
      return emptyList();
    }
  }

  private OrderItem convertToItem(OrderItemRequest vo) {
    return OrderItem.builder()
      .price(vo.getPrice())
      .product(ofNullable(vo.getProductId()).map(productService::getProduct).orElse(null))
      .quantity(vo.getQuantity())
      .build();
  }
}
