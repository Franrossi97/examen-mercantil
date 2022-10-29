package examen.mercantil.examenmercantil.services;

import examen.mercantil.examenmercantil.daos.ProductDao;
import examen.mercantil.examenmercantil.model.dtos.ProductDto;
import examen.mercantil.examenmercantil.model.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Autowired
    private MockMvc mockMvc;

    ProductDao productDao = mock(ProductDao.class);

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    public void createProductTest() {
        ModelMapper modelMapper = new ModelMapper();

        ProductDto productDto = this.getProduct();
        ProductDto productResponseDto = this.getProduct();
        productResponseDto.setId("B657");

        when(productDao.save(any(Product.class)))
                .thenReturn(modelMapper.map(productResponseDto, Product.class));

        Product resProduct = productService.createProduct(productDto);

        assert resProduct != null;
        assert resProduct.getId().equals("B657");
    }

    @Test
    public void findProductTest() {
        ModelMapper modelMapper = new ModelMapper();

        ProductDto productResponseDto = this.getProduct();
        productResponseDto.setId("B657");

        when(productDao.findById(anyString()))
                .thenReturn(Optional.of(modelMapper.map(productResponseDto, Product.class)));

        Product resProduct = productService.findProduct("B657");

        assert resProduct != null;
    }

    /*@Test
    public void findProductTestThrowingError() {
        ModelMapper modelMapper = new ModelMapper();

        ProductDto productResponseDto = this.getProduct();
        productResponseDto.setId("B657");

        when(productDao.findById(anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, ""));

        System.out.println(productService.findProduct("B657"));

        Exception e = productService.findProduct("B657");
    }*/

    @Test
    public void editProductTest() {
        ModelMapper modelMapper = new ModelMapper();

        ProductDto productDto = this.getProduct();
        ProductDto productResponseDto = this.getProduct();
        productResponseDto.setId("B657");

        when(productDao.save(any(Product.class)))
                .thenReturn(modelMapper.map(productResponseDto, Product.class));

        Product resProduct = productService.editProduct("B657", productDto);

        assert resProduct != null;
    }

    @Test
    public void deleteProductTest() {
        ModelMapper modelMapper = new ModelMapper();

        ProductDto productDto = this.getProduct();
        ProductDto productResponseDto = this.getProduct();
        productResponseDto.setId("B657");

        productService.deleteProduct("B657");
    }

    private ProductDto getProduct() {
        ProductDto productDto = new ProductDto();

        productDto.setName("Muzzarella");
        productDto.setLongDescription("Pizza grande de queso muzzarella");
        productDto.setShortDescription("Pizza muzzarella");
        productDto.setUnitPrice(1000);

        return productDto;
    }
}
