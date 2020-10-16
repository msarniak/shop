package shop.service;

public class ProductValidationException extends RuntimeException {

  public ProductValidationException(String message) {
    super(message);
  }
}
