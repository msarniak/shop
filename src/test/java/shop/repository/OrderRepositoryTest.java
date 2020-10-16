package shop.repository;

import static java.time.LocalDateTime.parse;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static shop.repository.model.Product.builder;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.tomcat.jni.Local;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import shop.repository.model.Order;
import shop.repository.model.OrderItem;
import shop.repository.model.Product;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = {
  "spring.jpa.hibernate.ddl-auto=validate"
})
@AutoConfigureEmbeddedDatabase
public class OrderRepositoryTest {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void shouldFindByDateBetween() {
    // given
    final Order order1 = createOrder(parse("2020-10-15T14:08:15"));
    final Order order2 = createOrder(parse("2020-10-15T15:08:15"));
    final Order order3 = createOrder(parse("2020-10-15T16:08:15"));

    // when
    final List<Order> result = orderRepository.findOrdersByDateBetween(
      parse("2020-10-15T15:00:00"),
      parse("2020-10-15T16:00:00"));

    // then
    assertThat(result)
      .hasSize(1)
      .containsOnly(order2);
  }

  @Test
  public void shouldFindNoResults() {
    // given
    final Order order1 = createOrder(parse("2020-10-15T14:08:15"));

    // when
    final List<Order> result = orderRepository.findOrdersByDateBetween(
      parse("2020-10-10T15:00:00"),
      parse("2020-10-10T16:00:00"));

    // then
    assertThat(result).hasSize(0);
  }


  @Test
  public void shouldStoreOrderWithProducts() {
    // given
    final Product product1 = createProduct("name1");
    final Product product2 = createProduct("name2");

    final Order saved = createOrder(
      parse("2020-10-15T14:08:15"),
          item(product1),
          item(product2));

    // when
    final Order found = orderRepository.findById(saved.getId())
      .orElseGet(() -> fail("Order not found by id"));

    // then
    assertThat(found).isNotNull();
    assertThat(found.getItems())
      .hasSize(2)
      .containsOnly(saved.getItems().get(0), saved.getItems().get(1));
  }

  private OrderItem item(Product product) {
    return OrderItem.builder()
      .product(product)
      .price(product.getPrice())
      .quantity(1)
      .build();
  }

  private Product createProduct(String name) {
    return productRepository.save(
      Product.builder()
      .name(name)
      .price(new BigDecimal("12.1"))
      .build());
  }


  private Order createOrder(LocalDateTime orderDate, OrderItem... items) {
    final Order order =
      Order.builder()
        .buyerEmail("some@mail.com")
        .date(orderDate)
        .build();
    stream(items).forEach(item -> item.setOrder(order));
    order.setItems(asList(items));
    return orderRepository.save(order);
  }

}
