package shop.repository;

import static org.assertj.core.api.Assertions.assertThat;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import shop.repository.model.Product;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = {
  "spring.jpa.hibernate.ddl-auto=validate"
})
@AutoConfigureEmbeddedDatabase
public class ProductRepositoryTest {

  private static final String NAME = "the name";
  private static final BigDecimal PRICE = new BigDecimal("0.99");

  @Autowired
  private ProductRepository repository;

  @Test
  public void shouldFindByName() {
    // given
    Product saved = repository.save(
      Product.builder()
        .name(NAME)
        .price(PRICE)
        .build());

    // when
    final Optional<Product> result = repository.findByName(NAME);

    // then
    assertThat(result)
      .isPresent()
      .hasValue(saved);
  }

  @Test
  public void shouldNotFindByName() {
    // given
    Product saved = repository.save(
      Product.builder()
        .name(NAME)
        .price(PRICE)
        .build());

    // when
    final Optional<Product> result = repository.findByName("some other name");

    // then
    assertThat(result).isEmpty();
  }


}