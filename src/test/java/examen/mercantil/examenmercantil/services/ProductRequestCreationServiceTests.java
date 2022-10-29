package examen.mercantil.examenmercantil.services;

import examen.mercantil.examenmercantil.daos.ProductDao;
import examen.mercantil.examenmercantil.daos.ProductRequestHeaderDao;
import examen.mercantil.examenmercantil.model.dtos.ProductRequestDetailsDto;
import examen.mercantil.examenmercantil.model.dtos.ProductRequestHeaderDto;
import examen.mercantil.examenmercantil.model.entities.Product;
import examen.mercantil.examenmercantil.model.entities.ProductRequestHeader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductRequestCreationServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    ProductRequestHeaderDao productRequestDao;

    ProductDao productDao = mock(ProductDao.class);

    @InjectMocks
    ProductRequestCreationServiceImpl productRequestCreationService;

    @Test
    public void whenCreateProductRequestWithTwoProductsShouldReturnTheSavedResult() {
        ModelMapper modelMapper = new ModelMapper();

        List<String> productsId = this.setIdOfProducts(this.getListOfProducts());

        ProductRequestHeaderDto productRequest = this.getProductRequestHeader(productsId.get(0), productsId.get(1));
        ProductRequestHeaderDto productRequestResponse = this.getProductRequestDtoResponse(productRequest);

        when(productRequestDao.save(modelMapper.map(productRequest, ProductRequestHeader.class)))
                .thenReturn(modelMapper.map(productRequestResponse, ProductRequestHeader.class));

        when(productDao.findAllById(anyIterable())).thenReturn(this.getListOfProducts());

        ProductRequestHeader productRequestHeaderEnt = this.productRequestCreationService.createProductRequest(productRequest);

        assert productRequestHeaderEnt != null;
    }

    private ProductRequestHeaderDto getProductRequestHeader(String productId1, String productId2) {

        ProductRequestHeaderDto productRequest = new ProductRequestHeaderDto();

        ProductRequestDetailsDto productRequestDetailsDto1 = new ProductRequestDetailsDto();

        productRequestDetailsDto1.setProductId(productId1);
        productRequestDetailsDto1.setQuantity(1);

        ProductRequestDetailsDto productRequestDetailsDto2 = new ProductRequestDetailsDto();

        productRequestDetailsDto2.setProductId(productId2);
        productRequestDetailsDto2.setQuantity(1);

        List<ProductRequestDetailsDto> productRequestDetailsDtos = new ArrayList<>();

        productRequestDetailsDtos.add(productRequestDetailsDto1);
        productRequestDetailsDtos.add(productRequestDetailsDto2);

        productRequest.setAddress("Almafuerte 1600");
        productRequest.setEmail("franrossi97@gmail.com");
        productRequest.setPhone("2235017491");
        productRequest.setTime("20:08");
        productRequest.setProductRequestDetails(productRequestDetailsDtos);

        return productRequest;
    }

    private List<Product> getListOfProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(UUID.randomUUID().toString(), "Muzzarella", "", "", 2000));
        products.add(new Product(UUID.randomUUID().toString(), "Americana", "", "", 3000));

        return products;
    }

    private List<String> setIdOfProducts(List<Product> products) {
        List<String> idOfProducts = new ArrayList<>();

        products.forEach((product -> {
            idOfProducts.add(product.getId());
        }));

        return idOfProducts;
    }

    private ProductRequestHeaderDto getProductRequestDtoResponse(ProductRequestHeaderDto productRequest) {
        ProductRequestHeaderDto response = productRequest;

        return response;
    }

}
