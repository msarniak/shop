package shop.service;

import static io.micrometer.core.instrument.util.StringUtils.isBlank;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.repository.ProductRepository;
import shop.repository.model.Product;

/**
 * Validates product (both new and updated)
 */
@Service
@RequiredArgsConstructor
public class ProductValidator {

  private final ProductRepository productRepository;

  public void validateProduct(Product product) {
    if (isBlank(product.getName())) {
      throw new ProductValidationException("Product name can't be empty");
    }
    if (isNull(product.getPrice())) {
      throw new ProductValidationException("Product price can't be null");
    }
    if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
      throw new ProductValidationException("Product price has to be greater than zero.");
    }

    productRepository.findByName(product.getName().trim())
      .ifPresent(found -> {
        if (isNull(product.getId()) || !product.getId().equals(found.getId())) {
          throw new ProductValidationException(
            format("Product with name '%s' already exists ('%s').", product.getName(), found.getId()));
        }
      });
  }
}
