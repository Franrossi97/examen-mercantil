package examen.mercantil.examenmercantil.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ActiveProfiles("testCreationProductRequest")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductRequestServiceTests {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private static ProductRequestHeaderDao productRequestDao;

    @InjectMocks
    private ProductRequestServiceImpl productRequestService;

    @Autowired
    private ProductRequestCreationServiceImpl productRequestCreationService;

    private static List<ProductRequestHeader> getProductRequestForFind() {
        List<ProductRequestHeader> res = new ArrayList<>();
        ProductRequestHeader productRequestHeader = new ProductRequestHeader();

        productRequestHeader.setProductRequestDetails(new ArrayList<>());
        productRequestHeader.setCreationDate("17-06-97");
        productRequestHeader.setDiscountApplied(false);
        productRequestHeader.setStatus("PENDING");
        productRequestHeader.setAddress("Almafuerte 1600");
        productRequestHeader.setEmail("franrossi97@gmail.com");
        productRequestHeader.setPhone("2235017491");
        productRequestHeader.setTime("21:10");
        productRequestHeader.setTotalAmount(3500L);

        res.add(productRequestHeader);

        return res;
    }

    @Test
    public void whenGetProductRequestByDateShouldReturnAListOfThem() {

        when(productRequestDao.findByCreationDate("17-06-97")).thenReturn(getProductRequestForFind());

        List<ProductRequestHeader> productRequests = productRequestService.findProductRequestByDate("17-06-97");

        assert productRequests.size() == 1;
        assert productRequests.get(0).getProductRequestDetails().size() == 0;
        assert productRequests.get(0).getCreationDate().equals("17-06-97");
        assert !productRequests.get(0).isDiscountApplied();
        assert productRequests.get(0).getStatus().equals("PENDING");
        assert productRequests.get(0).getAddress().equals("Almafuerte 1600");
        assert productRequests.get(0).getEmail().equals("franrossi97@gmail.com");
        assert productRequests.get(0).getPhone().equals("2235017491");
        assert productRequests.get(0).getTime().equals("21:10");
        assert productRequests.get(0).getTotalAmount() == 3500L;
    }

    @Test
    public void whenGetProductRequestByWrongDateShouldReturnAnEmptyList() {

        when(productRequestDao.findByCreationDate("17-06-97")).thenReturn(new ArrayList<>());

        List<ProductRequestHeader> productRequests = productRequestService.findProductRequestByDate("17-06-97");

        assert productRequests.size() == 0;
    }

    @Test
    public void whenCreateProductRequestWithTwoProductsShouldReturnTheSavedResult() {
        ModelMapper modelMapper = new ModelMapper();

        List<Product> products = this.getListOfProducts();

        List<String> productsId = this.setIdOfProducts(products);

        ProductRequestHeaderDto productRequest = this.getProductRequestHeader(productsId.get(0), productsId.get(1));
        ProductRequestHeaderDto productRequestResponse = this.getProductRequestDtoResponse(productRequest);

        when(productRequestCreationService.createProductRequest(productRequest))
                .thenReturn(modelMapper.map(productRequestResponse, ProductRequestHeader.class));

        ProductRequestHeader productRequestHeaderEnt = this.productRequestService.createProductRequest(productRequest);

        assert productRequestHeaderEnt != null;
        assert productRequestHeaderEnt.getStatus().equals("PENDING");
        assert productRequestHeaderEnt.getTotalAmount() == 5000;
        assert !productRequestHeaderEnt.isDiscountApplied();
    }

    @Test
    public void whenCreateProductRequestWithThreeProductsShouldReturnTheSavedResult() {
        ModelMapper modelMapper = new ModelMapper();

        List<Product> products = this.getListOfProducts();

        List<String> productsId = this.setIdOfProducts(products);

        ProductRequestHeaderDto productRequest = this.getProductRequestHeader(productsId.get(0), productsId.get(1));
        ProductRequestHeaderDto productRequestResponse = this.getProductRequestDtoResponse(productRequest);

        when(productRequestCreationService.createProductRequest(productRequest))
                .thenReturn(modelMapper.map(productRequestResponse, ProductRequestHeader.class));

        ProductRequestHeader productRequestHeaderEnt = this.productRequestService.createProductRequest(productRequest);

        assert productRequestHeaderEnt != null;
        assert productRequestHeaderEnt.getStatus().equals("PENDING");
        assert productRequestHeaderEnt.getTotalAmount() == 5000;
        assert !productRequestHeaderEnt.isDiscountApplied();
    }

    @Test
    public void createAProductRequestWithInvalidValues() {
        ProductRequestHeaderDto productRequestHeaderDto = new ProductRequestHeaderDto();
        productRequestHeaderDto.setProductRequestDetails(new ArrayList<>());

        try {
            this.productRequestCreationService.createProductRequest(productRequestHeaderDto);

            assert false;
        } catch (ResponseStatusException e) {
            assert true;
        }
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

        response.setStatus("PENDING");
        response.setTotalAmount(5000);
        response.setDiscountApplied(false);

        return response;
    }
}
