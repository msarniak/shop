package shop.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import shop.api.model.ErrorVO;
import shop.service.OrderValidationException;
import shop.service.ProductNotFoundException;
import shop.service.ProductValidationException;

/**
 * Global exception handler
 */
@ControllerAdvice
public class ShopExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Return 404 NOT_FOUND for ProductNotFoundException
   */
  @ExceptionHandler({ProductNotFoundException.class})
  protected ResponseEntity<ErrorVO> handleProductNotFoundException(Exception exception) {
    return new ResponseEntity<>(ErrorVO.builder()
      .message(exception.getMessage())
      .build(), HttpStatus.NOT_FOUND);
  }

  /**
   * Return 400 BAD_REQUEST for any validation exception
   */
  @ExceptionHandler({OrderValidationException.class, ProductValidationException.class})
  protected ResponseEntity<ErrorVO> handleOrderValidationException(Exception exception) {
    return new ResponseEntity<>(ErrorVO.builder()
      .message(exception.getMessage())
      .build(), HttpStatus.BAD_REQUEST);
  }
}
