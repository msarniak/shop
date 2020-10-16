package shop.api.controller;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import shop.api.controller.converter.ProductConverter;
import shop.api.controller.converter.ProductViewConverter;
import shop.api.model.ProductRequest;
import shop.api.model.ProductView;
import shop.repository.model.Product;
import shop.service.ProductService;

/**
 * Rest ProductAPI
 */
@RestController
@RequiredArgsConstructor
public class ProductApiController implements ProductApi {

  private final ProductService productService;
  private final ProductViewConverter productViewConverter;
  private final ProductConverter productConverter;

  /**
   * List all products defined in the system
   * @return list of productS
   */
  @Override
  public ResponseEntity<List<ProductView>> listProducts() {
    final List<ProductView> result = productService.listProducts().stream()
      .map(productViewConverter::convertToProductView)
      .collect(toList());
    return ok(result);
  }

  /**
   * Get the specific product by it's id
   */
  @Override
  public ResponseEntity<ProductView> getProduct(Long id) {
    final Product product = productService.getProduct(id);
    return ok(productViewConverter.convertToProductView(product));
  }

  /**
   * Create new product by name
   */
  @Override
  public ResponseEntity<ProductView> create(ProductRequest productRequest) {
    final Product product = productConverter.convertToProduct(productRequest);

    final Product createdProduct = productService.createProduct(product);

    return created(URI.create(format("/products/%s", createdProduct.getId())))
      .body(productViewConverter.convertToProductView(createdProduct));
  }

  /**
   * Update existing product
   */
  @Override
  public ResponseEntity<ProductView> update(Long id, ProductRequest productRequest) {
    final Product product = productService.getProduct(id);

    productConverter.merge(product, productRequest);

    final Product updated = productService.updateProduct(product);
    return ok(productViewConverter.convertToProductView(updated));
  }

}
