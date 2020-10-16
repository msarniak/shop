package shop.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorVO {

  private final String message;

}
