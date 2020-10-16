package shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The Shop application
 *   - exposes ProductAPI and OrderAPI rest endpoints.
 *   - use Swagger for endpoint documentation
 */
@SpringBootApplication
public class ShopApp {

  /**
   * Main method to run by spring boot
   */
  public static void main(String[] args) {
    SpringApplication.run(ShopApp.class, args);
  }
}
