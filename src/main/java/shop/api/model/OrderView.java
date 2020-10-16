package shop.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderView {

  private final Long id;
  private final String email;
  private final LocalDateTime orderDate;
  private final List<OrderItemView> items;
  private final BigDecimal amount;

}
