package shop.api.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class OrderItemView {

  private final Long productId;
  private final String productName;
  private final BigDecimal price;
  private final int quantity;
}
