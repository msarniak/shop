package shop.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import shop.repository.model.Product;

/**
 * Repository for products
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

  Optional<Product> findByName(String name);
}
