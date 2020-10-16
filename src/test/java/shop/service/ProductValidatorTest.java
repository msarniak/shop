package shop.service;

import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import shop.repository.ProductRepository;
import shop.repository.model.Product;

@RunWith(MockitoJUnitRunner.class)
public class ProductValidatorTest {

  private static final String NAME = "the name";
  private static final long ID = 10L;
  private static final long OTHER_ID = 11L;
  private static final BigDecimal PRICE = new BigDecimal("9.99");

  @InjectMocks
  private ProductValidator productValidator;

  @Mock
  private ProductRepository productRepository;

  @Test
  public void shouldPassOnValidProduct() {
    // given
    final Product product = validProduct();

    when(productRepository.findByName(NAME)).thenReturn(Optional.empty());

    // when
    productValidator.validateProduct(product);
  }

  @Test
  public void shouldPassOnExistingProductWithUnchangedName() {
    // given
    final Product product = validProduct();
    product.setId(ID);

    when(productRepository.findByName(NAME))
      .thenReturn(Optional.of(Product.builder().id(ID).build()));

    // when
    productValidator.validateProduct(product);
  }

  @Test(expected = ProductValidationException.class)
  public void shouldFailOnDuplicateNameForNewProduct() {
    // given
    final Product product = validProduct();

    when(productRepository.findByName(NAME))
      .thenReturn(Optional.of(Product.builder().id(ID).build()));

    // when
    productValidator.validateProduct(product);
  }

  @Test(expected = ProductValidationException.class)
  public void shouldFailOnDuplicateNameForUpdatedProduct() {
    // given
    final Product product = validProduct();
    product.setId(ID);

    // there is a product in repository, with same name, but different id:
    // that means user tried to update this product name with name used by other product
    when(productRepository.findByName(NAME))
      .thenReturn(Optional.of(Product.builder().id(OTHER_ID).build()));

    // when
    productValidator.validateProduct(product);
  }

  @Test(expected = ProductValidationException.class)
  public void shouldFailMissingName() {
    // given
    final Product product = validProduct();
    product.setName(null);

    // when
    productValidator.validateProduct(product);
  }

  @Test(expected = ProductValidationException.class)
  public void shouldFailEmptyName() {
    // given
    final Product product = validProduct();
    product.setName("   ");

    // when
    productValidator.validateProduct(product);
  }

  @Test(expected = ProductValidationException.class)
  public void shouldFailMissingPrice() {
    // given
    final Product product = validProduct();
    product.setPrice(null);

    // when
    productValidator.validateProduct(product);
  }

  @Test(expected = ProductValidationException.class)
  public void shouldFailOnZeroPrice() {
    // given
    final Product product = validProduct();
    product.setPrice(BigDecimal.ZERO);

    // when
    productValidator.validateProduct(product);
  }

  @Test(expected = ProductValidationException.class)
  public void shouldFailOnNegativeZeroPrice() {
    // given
    final Product product = validProduct();
    product.setPrice(new BigDecimal("-0.01"));

    // when
    productValidator.validateProduct(product);
  }

  private Product validProduct() {
    return Product.builder()
      .name(NAME)
      .price(PRICE)
      .build();
  }

}