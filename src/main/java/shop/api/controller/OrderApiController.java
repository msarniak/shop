package shop.api.controller;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import shop.api.controller.converter.OrderConverter;
import shop.api.controller.converter.OrderViewConverter;
import shop.api.model.OrderRequest;
import shop.api.model.OrderView;
import shop.repository.model.Order;
import shop.service.OrderService;

/**
 * Rest OrderAPI
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderApiController implements OrderApi {

  private static final ZoneId GMT = ZoneId.of("GMT");
  private final OrderService orderService;

  private final OrderViewConverter orderViewConverter;
  private final OrderConverter orderConverter;

  /**
   * Place new order in the system.
   * Converts the OrderRequest to internal Order representation and passes the order to service
   * for validation and creation.
   */
  @Override
  public ResponseEntity<OrderView> placeOrder(OrderRequest orderData) {
    final Order order = orderConverter.convertToOrder(orderData);

    final Order savedOrder = orderService.placeOrder(order);

    return created(URI.create(format("/orders/%s", savedOrder.getId())))
      .body(orderViewConverter.convertToOrderView(savedOrder));
  }

  /**
   * List order stored in the system, filtered by date range
   */
  @Override
  public ResponseEntity<List<OrderView>> listOrders(ZonedDateTime timeFrom, ZonedDateTime timeTo) {
    final List<OrderView> result = orderService.listOrders(
      timeFrom.withZoneSameInstant(GMT).toLocalDateTime(),
      timeTo.withZoneSameInstant(GMT).toLocalDateTime())
      .stream()
      .map(orderViewConverter::convertToOrderView)
      .collect(toList());
    return ok(result);
  }

}
