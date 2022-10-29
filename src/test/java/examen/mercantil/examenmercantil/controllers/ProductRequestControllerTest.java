package examen.mercantil.examenmercantil.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import examen.mercantil.examenmercantil.model.dtos.ProductRequestDetailsDto;
import examen.mercantil.examenmercantil.model.dtos.ProductRequestHeaderDto;
import examen.mercantil.examenmercantil.model.entities.ProductRequestHeader;
import examen.mercantil.examenmercantil.services.ProductRequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductRequestControllerTest {

    ModelMapper modelMapper = new ModelMapper();
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductRequestService productRequestService;

    @Test
    public void createProductTest() throws Exception {

        ProductRequestHeaderDto productRequestHeaderDto = this.getProductRequestHeader("B1", "B2");

        when(productRequestService.createProductRequest(any(ProductRequestHeaderDto.class)))
                .thenReturn(modelMapper.map(productRequestHeaderDto, ProductRequestHeader.class));

        mockMvc.perform(post("/api/requestProducts")
                .content(mapper.writeValueAsString(productRequestHeaderDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void createProductTestWithInvalidValues() throws Exception {

        ProductRequestHeaderDto productRequestHeaderDto = new ProductRequestHeaderDto();

        /*when(productRequestService.createProductRequest(any(ProductRequestHeaderDto.class)))
                .thenReturn(modelMapper.map(productRequestHeaderDto, ProductRequestHeader.class));*/

        mockMvc.perform(post("/api/requestProducts")
                        .content(mapper.writeValueAsString(productRequestHeaderDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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

    @Test
    public void getProductByDateTest() throws Exception {
        ProductRequestHeaderDto productRequestHeaderDto = this.getProductRequestHeader("B1", "B2");

        when(productRequestService.findProductRequestByDate(anyString()))
                .thenReturn(List.of(modelMapper.map(productRequestHeaderDto, ProductRequestHeader.class)));

        mockMvc.perform(get("/api/requestProducts/17-06-97"))
                .andExpect(status().isOk());
    }
}
