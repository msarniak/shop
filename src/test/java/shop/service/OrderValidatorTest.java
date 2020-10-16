package shop.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import java.math.BigDecimal;
import org.junit.Test;
import shop.repository.model.Order;
import shop.repository.model.OrderItem;
import shop.repository.model.Product;

public class OrderValidatorTest {

  private static final Long PRODUCT_1_ID = 1L;
  private static final BigDecimal PRODUCT_1_PRICE = new BigDecimal("12.00");
  private static final Long PRODUCT_2_ID = 2L;
  private static final BigDecimal PRODUCT_2_PRICE = new BigDecimal("24.00");
  private static final int QUANTITY = 10;

  private final OrderValidator validator = new OrderValidator();

  @Test
  public void shouldPassValidation() {
    // given
    final Order order = validOrder();

    // when
    validator.validateOrder(order);

    // then
    // ... no exception has been thrown
  }

  @Test(expected = OrderValidationException.class)
  public void shouldFailOnNullEmailAddress() {
    // given
    final Order order = validOrder();
    order.setBuyerEmail(null);

    // when
    validator.validateOrder(order);
  }

  @Test(expected = OrderValidationException.class)
  public void shouldFailOnEmptyEmailAddress() {
    // given
    final Order order = validOrder();
    order.setBuyerEmail("");

    // when
    validator.validateOrder(order);
  }

  @Test(expected = OrderValidationException.class)
  public void shouldFailOnNullItems() {
    // given
    final Order order = validOrder();
    order.setItems(null);

    // when
    validator.validateOrder(order);
  }

  @Test(expected = OrderValidationException.class)
  public void shouldFailOnEmptyItems() {
    // given
    final Order order = validOrder();
    order.setItems(emptyList());

    // when
    validator.validateOrder(order);
  }

  @Test(expected = OrderValidationException.class)
  public void shouldFailOnMissingProduct() {
    // given
    final Order order = validOrder();
    order.getItems().get(1).setProduct(null);

    // when
    validator.validateOrder(order);
  }

  @Test(expected = OrderValidationException.class)
  public void shouldFailOnMissingPrice() {
    // given
    final Order order = validOrder();
    order.getItems().get(0).setPrice(null);

    // when
    validator.validateOrder(order);
  }

  @Test(expected = OrderValidationException.class)
  public void shouldFailOnMismatchedPrice() {
    // given
    final Order order = validOrder();
    order.getItems().get(0).setPrice(PRODUCT_2_PRICE);

    // when
    validator.validateOrder(order);
  }

  @Test(expected = OrderValidationException.class)
  public void shouldFailOnZeroQuantity() {
    // given
    final Order order = validOrder();
    order.getItems().get(0).setQuantity(0);

    // when
    validator.validateOrder(order);
  }

  @Test(expected = OrderValidationException.class)
  public void shouldFailOnNegativeQuantity() {
    // given
    final Order order = validOrder();
    order.getItems().get(0).setQuantity(-1);

    // when
    validator.validateOrder(order);
  }

  private Order validOrder() {
    return Order.builder()
      .buyerEmail("email@address.somewhere")
      .items(asList(
        OrderItem.builder()
          .product(Product.builder().id(PRODUCT_1_ID).price(PRODUCT_1_PRICE).build())
          .price(PRODUCT_1_PRICE)
          .quantity(QUANTITY)
          .build(),
        OrderItem.builder()
          .product(Product.builder().id(PRODUCT_2_ID).price(PRODUCT_2_PRICE).build())
          .price(PRODUCT_2_PRICE)
          .quantity(QUANTITY)
          .build()
      )).build();
  }
}