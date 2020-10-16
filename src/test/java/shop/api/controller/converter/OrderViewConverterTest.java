package shop.api.controller.converter;


import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Test;
import shop.api.model.OrderItemView;
import shop.api.model.OrderView;
import shop.repository.model.Order;
import shop.repository.model.OrderItem;
import shop.repository.model.Product;

public class OrderViewConverterTest {

  private static final String EMAIL_ADDRESS = "email@address";
  private static final LocalDateTime ORDER_DATE = LocalDateTime.now();
  private static final long ORDER_ID = 100L;
  private static final Long ITEM_ID = 222L;
  private static final int QUANTITY = 7;
  private static final BigDecimal PRICE = new BigDecimal("11.00");
  private static final String PRODUCT_NAME = "Product name";


  private OrderViewConverter converter = new OrderViewConverter();

  @Test
  public void shouldConvertOrderToView() {
    // given
    Order order = Order.builder()
      .buyerEmail(EMAIL_ADDRESS)
      .date(ORDER_DATE)
      .id(ORDER_ID)
      .items(
        singletonList(
          OrderItem.builder()
            .id(ITEM_ID)
            .quantity(QUANTITY)
            .price(PRICE)
            .product(
              Product.builder()
                .name(PRODUCT_NAME)
                .build()
            ).build()
        )
      ).build();

    // when
    final OrderView result = converter.convertToOrderView(order);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getEmail()).isEqualTo(EMAIL_ADDRESS);
    assertThat(result.getOrderDate()).isEqualTo(ORDER_DATE);
    assertThat(result.getId()).isEqualTo(order.getId());
    assertThat(result.getItems()).hasSize(1);
    final OrderItemView item = result.getItems().get(0);
    assertThat(item.getPrice()).isEqualByComparingTo(PRICE);
    assertThat(item.getProductName()).isEqualTo(PRODUCT_NAME);
    assertThat(item.getQuantity()).isEqualTo(QUANTITY);
    assertThat(result.getAmount()).isEqualByComparingTo(new BigDecimal("77.00"));
  }

}