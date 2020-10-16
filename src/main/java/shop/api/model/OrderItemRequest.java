package shop.api.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class OrderItemRequest {

  private final Long productId;
  private final BigDecimal price;
  private final int quantity;
}
