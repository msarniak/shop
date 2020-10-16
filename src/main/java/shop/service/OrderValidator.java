package shop.service;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import shop.repository.model.Order;
import shop.repository.model.OrderItem;

@Service
public class OrderValidator {

  public void validateOrder(Order order) {
    if (isEmpty(order.getBuyerEmail())) {
      throw new OrderValidationException("Invalid order: buyer email address is mandatory.");
    }

    if (isNull(order.getItems()) || order.getItems().isEmpty()) {
      throw new OrderValidationException("Invalid order: no items.");
    }

    order.getItems().forEach(this::validateItem);
  }

  private void validateItem(OrderItem orderItem) {
    if (isNull(orderItem.getProduct())) {
      throw new OrderValidationException("Invalid orderItem - missing product.");
    }
    final Long productId = orderItem.getProduct().getId();
    if (isNull(orderItem.getPrice())) {
      throw new OrderValidationException(format("Invalid orderItem for product '%s': no price.", productId));
    }
    final BigDecimal value1 = orderItem.getProduct().getPrice();
    final BigDecimal value2 = orderItem.getPrice();
    if (!areEqual(value1, value2)) {
      throw new OrderValidationException(format("Invalid orderItem for product '%s': price '%s' does not match the actual product price '%s'.",
        productId, orderItem.getPrice(), orderItem.getProduct().getPrice()));
    }
    if (orderItem.getQuantity() <= 0) {
      throw new OrderValidationException(format("Invalid orderItem for product '%s': quantity should be > 0, found %s.",
        productId, orderItem.getQuantity()));
    }
  }

  private boolean areEqual(BigDecimal value1, BigDecimal value2) {
    return value1.compareTo(value2) == 0;
  }

}
