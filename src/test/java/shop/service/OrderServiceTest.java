package shop.service;


import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import shop.repository.OrderRepository;
import shop.repository.model.Order;
import shop.repository.model.OrderItem;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

  @InjectMocks
  private OrderService orderService;

  @Mock
  private OrderRepository orderRepository;
  @Mock
  private OrderValidator orderValidator;

  @Test
  public void shouldCallOrderValidator() {
    // given
    final Order order = Order.builder()
      .items(emptyList())
      .build();

    // when
    orderService.placeOrder(order);

    // then
    verify(orderValidator).validateOrder(order);
  }

  @Test
  public void shouldCallSaveOrder() {
    // given
    final Order order = Order.builder()
      .items(emptyList())
      .build();

    // when
    orderService.placeOrder(order);

    // then
    verify(orderRepository).save(order);
  }

  @Test(expected = OrderValidationException.class)
  public void shouldPropagateExceptionOnFailedValidation() {
    // given
    final Order order = Order.builder()
      .items(emptyList())
      .build();

    doThrow(new OrderValidationException("...")).when(orderValidator).validateOrder(order);

    // when
    orderService.placeOrder(order);
  }

  @Test
  public void shouldSetCurrentOrderDate() {
    // given
    final Instant date = Instant.now();
    Universe.useClock(Clock.fixed(date, ZoneId.of("GMT")));

    final Order order = Order.builder()
      .items(emptyList())
      .build();

    // when
    orderService.placeOrder(order);

    // then
    assertThat(order.getDate().toInstant(ZoneOffset.UTC))
      .isEqualTo(date);
  }

  @Test
  public void shouldLinkItemsToOrder() {
    // given
    final Order order = Order.builder()
      .items(asList(
        OrderItem.builder().build(),
        OrderItem.builder().build()
      ))
      .build();

    // when
    orderService.placeOrder(order);

    // then
    assertThat(order.getItems().get(0).getOrder()).isSameAs(order);
    assertThat(order.getItems().get(1).getOrder()).isSameAs(order);
  }

}