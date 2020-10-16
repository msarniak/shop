package shop.api.controller.converter;


import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import org.junit.Test;
import shop.api.model.ProductRequest;
import shop.repository.model.Product;

public class ProductConverterTest {

  private static final String NAME = "Name of requested product";
  private static final BigDecimal PRICE = new BigDecimal("33.10");
  private static final long ID = 10L;

  private ProductConverter converter = new ProductConverter();

  @Test
  public void shouldConvertProductRequest() {
    // given
    final ProductRequest request = ProductRequest.builder()
      .name(NAME)
      .price(PRICE)
      .build();

    // when
    final Product result = converter.convertToProduct(request);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo(NAME);
    assertThat(result.getPrice()).isEqualByComparingTo(PRICE);
    assertThat(result.getId()).isNull();
  }

  @Test
  public void shouldMergeProductRequest() {
    // given
    final ProductRequest request = ProductRequest.builder()
      .name(NAME)
      .price(PRICE)
      .build();

    final Product product = Product.builder()
      .id(ID)
      .build();

    // when
    converter.merge(product, request);

    // then
    assertThat(product).isNotNull();
    assertThat(product.getName()).isEqualTo(NAME);
    assertThat(product.getPrice()).isEqualByComparingTo(PRICE);
    assertThat(product.getId()).isEqualTo(ID);
  }

}