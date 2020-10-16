package shop.api.controller.converter;

import org.springframework.stereotype.Component;
import shop.api.model.ProductView;
import shop.repository.model.Product;

/**
 * Convert internal product representation to REST API ProductView
 */
@Component
public class ProductViewConverter {

  public ProductView convertToProductView(Product product) {
    return ProductView.builder()
      .id(product.getId())
      .name(product.getName())
      .price(product.getPrice())
      .build();
  }
}
