package examen.mercantil.examenmercantil.services;

import examen.mercantil.examenmercantil.model.dtos.ProductDto;
import examen.mercantil.examenmercantil.model.entities.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public interface ProductService {

    Product createProduct(ProductDto product);

    Product findProduct(String productId) throws ResponseStatusException;

    Product editProduct(String productId, ProductDto product);

    void deleteProduct(String productId);
}
