package shop.api.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductView {

  private final Long id;
  private final String name;
  private final BigDecimal price;
}
