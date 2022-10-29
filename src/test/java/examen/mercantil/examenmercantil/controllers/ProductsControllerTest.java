package examen.mercantil.examenmercantil.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import examen.mercantil.examenmercantil.model.dtos.ProductDto;
import examen.mercantil.examenmercantil.model.entities.Product;
import examen.mercantil.examenmercantil.services.ProductService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductsControllerTest {

    ModelMapper modelMapper = new ModelMapper();
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Test
    public void createProductTest() throws Exception {

        ProductDto productDto = this.getProductDto();

        when(productService.createProduct(any(ProductDto.class)))
                .thenReturn(modelMapper.map(productDto, Product.class));

        mockMvc.perform(post("/api/products")
                        .content(mapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    private ProductDto getProductDto() {
        ProductDto productDto = new ProductDto();

        productDto.setUnitPrice(1000);
        productDto.setShortDescription("Pizza");
        productDto.setLongDescription("Pizza grande");
        productDto.setName("Muzzarella");

        return productDto;
    }

    @Test
    public void getProductTest() throws Exception {

        ProductDto productDto = this.getProductDto();

        when(productService.findProduct(anyString()))
                .thenReturn(modelMapper.map(productDto, Product.class));

        mockMvc.perform(get("/api/products/B56"))
                .andExpect(status().isOk());
    }

    @Test
    public void editProductTest() throws Exception {
        ProductDto productDto = this.getProductDto();

        when(productService.editProduct(anyString(), any(ProductDto.class)))
                .thenReturn(modelMapper.map(productDto, Product.class));

        mockMvc.perform(put("/api/products/B56")
                .content(mapper.writeValueAsString(productDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteProductTest() throws Exception {

        mockMvc.perform(delete("/api/products/B56"))
                .andExpect(status().isNoContent());
    }
}
