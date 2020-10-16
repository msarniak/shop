package shop.api.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.api.model.ErrorVO;
import shop.api.model.OrderRequest;
import shop.api.model.OrderView;

/**
 * Rest API to maintain orders
 */
@RequestMapping("orders")
public interface OrderApi {

  String APPLICATION_JSON = "application/json";

  @Operation(
    summary = "Create new order",
    description = "Place new order from list of products. Order is user email address and a list of order items, where "
      + "each order item is a reference to a product, quantity of items and price. Price may look redundant, but "
      + "this way we guard that user knows actual product prices when placing the order. If user puts wrong price (by mistake or "
      + " product price in the system has just changed), validation will fail and order will not be placed.",
    operationId = "placeOrder"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Order has been created"),
    @ApiResponse(responseCode = "400", description = "Order cannot be created due to isues with the request",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class))),
    @ApiResponse(responseCode = "404", description = "Referenced resource (product) could not be found",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class)))
  })
  @RequestMapping(value = "", method = POST, consumes = "application/json", produces = APPLICATION_JSON)
  ResponseEntity<OrderView> placeOrder(
    @Parameter(required = true, description = "Order details.") @RequestBody OrderRequest order);

  @Operation(
    summary = "List orders placed in the system.",
    description = "Returns list of all orders placed in the system, filtered by date range.",
    operationId = "listOrders"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Return the list of orders"),
    @ApiResponse(responseCode = "400", description = "There are issues in the request (ex. filter dates are not valid)",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class)))
  })
  @RequestMapping(value = "", method = GET, produces = APPLICATION_JSON)
  ResponseEntity<List<OrderView>> listOrders(
    @Parameter(name = "from", example = "2020-10-15T14:30Z", required = true)
    @DateTimeFormat(iso = ISO.DATE_TIME) @RequestParam(value = "from", required = true) ZonedDateTime timeFrom,
    @Parameter(name = "to", example = "2020-10-20T14:30Z", required = true)
    @DateTimeFormat(iso = ISO.DATE_TIME) @RequestParam(value = "to", required = true) ZonedDateTime timeTo);

}
