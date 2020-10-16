package shop.api.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.api.model.ErrorVO;
import shop.api.model.ProductRequest;
import shop.api.model.ProductView;

/**
 * The product api.
 */
@RequestMapping("products")
public interface ProductApi {

  String APPLICATION_JSON = "application/json";

  @Operation(
    summary = "List existing product",
    description = "List all products defined in the shop.",
    operationId = "listProducts"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "OK"),
    @ApiResponse(responseCode = "500", description = "Internal server error",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class)))
  })
  @RequestMapping(value = "", method = GET, produces = APPLICATION_JSON)
  ResponseEntity<List<ProductView>> listProducts();

 @Operation(
    summary = "Get existing product by id",
    description = "Get the details of existing product by product id.",
    operationId = "getProduct",
   responses =  {
    @ApiResponse(responseCode = "200", description = "OK"),
    @ApiResponse(responseCode = "404", description = "Product with this id does not exist",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class)))
  })
  @RequestMapping(value = "{id}", method = GET, produces = APPLICATION_JSON)
  ResponseEntity<ProductView> getProduct(
    @Parameter(name = "id", required = true, description = "Id of the product") @PathVariable("id") Long id);

  @Operation(
    summary = "Add new product",
    description = "Define new product in the shop.",
    operationId = "createProduct"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Product has been created"),
    @ApiResponse(responseCode = "400", description = "Product cannot be created due to isues with the request",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class)))
  })
  @RequestMapping(value = "", method = POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
  ResponseEntity<ProductView> create(
    @Parameter(name = "product", description = "Created product data") @RequestBody ProductRequest product);


  @Operation(
    summary = "Update existing product",
    description = "Update definition of the product in the shop; product price and name may be updated.",
    operationId = "updateProduct"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Product has been updated"),
    @ApiResponse(responseCode = "400", description = "Product cannot be updated due to isues with the request",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class))),
    @ApiResponse(responseCode = "404", description = "Product with this id does not exist",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error",
      content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorVO.class)))
  })
  @RequestMapping(value = "{id}", method = PUT, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
  ResponseEntity<ProductView> update(
    @Parameter(name = "id", description = "Id of the product thats should updated", required = true) @PathVariable("id") Long id,
    @Parameter(name = "product", description = "Updated product data", required = true) @RequestBody ProductRequest product);
}
