package shop.api.controller.converter;


import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import shop.api.model.OrderItemRequest;
import shop.api.model.OrderRequest;
import shop.repository.model.Order;
import shop.repository.model.OrderItem;
import shop.repository.model.Product;
import shop.service.ProductNotFoundException;
import shop.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class OrderConverterTest {

  private static final String EMAIL_ADDRESS = "email@address";
  private static final long PRODUCT_ID = 1L;
  private static final BigDecimal PRICE = new BigDecimal("12.00");
  private static final int QUANTITY = 22;
  private static final Product PRODUCT = Product.builder().build();

  @InjectMocks
  private OrderConverter converter;

  @Mock
  private ProductService productService;


  @Test
  public void shoulConvertToOrder() {
    // given
    final OrderRequest request =
      OrderRequest.builder()
        .email(EMAIL_ADDRESS)
        .items(
          singletonList(
            OrderItemRequest.builder()
              .productId(PRODUCT_ID)
              .price(PRICE)
              .quantity(QUANTITY)
              .build()))
      .build();

    when(productService.getProduct(PRODUCT_ID))
      .thenReturn(PRODUCT);

    // when
    final Order result = converter.convertToOrder(request);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getBuyerEmail()).isEqualTo(EMAIL_ADDRESS);
    assertThat(result.getItems()).hasSize(1);
    final OrderItem item = result.getItems().get(0);
    assertThat(item.getPrice()).isEqualByComparingTo(PRICE);
    assertThat(item.getProduct()).isSameAs(PRODUCT);
    assertThat(item.getQuantity()).isEqualTo(QUANTITY);
  }

  @Test
  public void shoulConvertToOrderWhenProductsAreNull() {
    // given
    final OrderRequest request =
      OrderRequest.builder()
        .email(EMAIL_ADDRESS)
        .items(null)
        .build();

    // when
    final Order result = converter.convertToOrder(request);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getBuyerEmail()).isEqualTo(EMAIL_ADDRESS);
    assertThat(result.getItems()).hasSize(0);
  }

  @Test(expected = ProductNotFoundException.class)
  public void shoulPropagateProductNotFoundException() {
    // given
    final OrderRequest request =
      OrderRequest.builder()
        .items(
          singletonList(
            OrderItemRequest.builder()
              .productId(PRODUCT_ID)
              .build()))
        .build();

    when(productService.getProduct(PRODUCT_ID))
      .thenThrow(new ProductNotFoundException("Not found...."));

    converter.convertToOrder(request);
  }

}