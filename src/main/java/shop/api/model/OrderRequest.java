package shop.api.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class OrderRequest {

  private final String email;
  private final List<OrderItemRequest> items;

}
