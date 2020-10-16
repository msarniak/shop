package shop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import shop.repository.ProductRepository;
import shop.repository.model.Product;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

  public static final long ID = 11L;
  @InjectMocks
  private ProductService productService;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductValidator productValidator;

  @Test
  public void shouldCallSaveOnCreate() {
    // given
    final Product product = Product.builder().build();

    // when
    productService.createProduct(product);

    // then
    verify(productRepository).save(product);
  }

  @Test
  public void shouldCallSaveOnUpdate() {
    // given
    final Product product = Product.builder().build();

    // when
    productService.updateProduct(product);

    // then
    verify(productRepository).save(product);
  }

  @Test(expected = ProductValidationException.class)
  public void shouldPropagateValdiationExceptionOnProductCreation() {
    // given
    final Product product = Product.builder().build();

    doThrow(new ProductValidationException("...")).when(productValidator).validateProduct(product);

    // when
    productService.createProduct(product);
  }

  @Test(expected = ProductValidationException.class)
  public void shouldPropagateValdiationExceptionOnProductUpdate() {
    // given
    final Product product = Product.builder().build();

    doThrow(new ProductValidationException("...")).when(productValidator).validateProduct(product);

    // when
    productService.updateProduct(product);
  }

  @Test
  public void shouldFindProductById() {
    // given
    final Product product = Product.builder().build();

    when(productRepository.findById(ID)).thenReturn(Optional.of(product));

    // when
    final Product result = productService.getProduct(ID);

    // then
    assertThat(result).isSameAs(product);
  }

  @Test(expected = ProductNotFoundException.class)
  public void shouldThrowProductNotFoundException() {
    // given
    when(productRepository.findById(ID)).thenReturn(Optional.empty());

    // when
    productService.getProduct(ID);
  }

}