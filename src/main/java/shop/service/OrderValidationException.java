package shop.service;

public class OrderValidationException extends RuntimeException {

  public OrderValidationException(String message) {
    super(message);
  }
}
