package shop.api.controller.converter;


import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import org.junit.Test;
import shop.api.model.ProductView;
import shop.repository.model.Product;

public class ProductViewConverterTest {

  private static final String NAME = "Name of requested product";
  private static final BigDecimal PRICE = new BigDecimal("33.10");
  private static final long ID = 10L;

  private final ProductViewConverter converter = new ProductViewConverter();

  @Test
  public void shouldConvertToProductView() {
    // given
    final Product product = Product.builder()
      .id(ID)
      .name(NAME)
      .price(PRICE)
      .build();

    // when
    final ProductView result = converter.convertToProductView(product);

    // then
    assertThat(result.getId()).isEqualTo(ID);
    assertThat(result.getName()).isEqualTo(NAME);
    assertThat(result.getPrice()).isEqualByComparingTo(PRICE);
  }

}