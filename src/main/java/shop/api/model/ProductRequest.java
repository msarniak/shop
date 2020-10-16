package shop.api.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class ProductRequest {

  private final String name;
  private final BigDecimal price;
}
