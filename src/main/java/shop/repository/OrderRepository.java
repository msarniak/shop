package shop.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import shop.repository.model.Order;

/**
 * Repository for orders
 */
public interface OrderRepository extends CrudRepository<Order, Long> {

  List<Order> findOrdersByDateBetween(LocalDateTime from, LocalDateTime to);
}
