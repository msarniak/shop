package shop.api.controller.converter;

import org.springframework.stereotype.Component;
import shop.api.model.ProductRequest;
import shop.repository.model.Product;

/**
 * Convert ProductRequest to internal Product representation
 */
@Component
public class ProductConverter {

  public Product convertToProduct(ProductRequest request) {
    return Product.builder()
      .name(request.getName())
      .price(request.getPrice())
      .build();
  }

  public void merge(Product product, ProductRequest productRequest) {
    product.setName(productRequest.getName());
    product.setPrice(productRequest.getPrice());
  }
}
