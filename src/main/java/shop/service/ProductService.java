package shop.service;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.repository.ProductRepository;
import shop.repository.model.Product;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  private final ProductValidator productValidator;
  private final ProductRepository repository;

  public Product createProduct(Product product) {
    productValidator.validateProduct(product);
    return repository.save(product);
  }

  public Product updateProduct(Product product) {
    productValidator.validateProduct(product);
    return repository.save(product);
  }

  public Product getProduct(Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new ProductNotFoundException(format("Product by id %s couldn't be found.", id)));
  }

  public List<Product> listProducts() {
    return stream(repository.findAll().spliterator(), false).collect(toList());
  }

}
