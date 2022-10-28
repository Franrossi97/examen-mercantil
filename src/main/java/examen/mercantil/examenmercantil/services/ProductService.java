package examen.mercantil.examenmercantil.services;

import examen.mercantil.examenmercantil.model.dtos.ProductDto;
import examen.mercantil.examenmercantil.model.entities.Product;
import examen.mercantil.examenmercantil.model.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Product createProduct(ProductDto product);

    Product findProduct(String productId) throws ProductNotFoundException;

    Product editProduct(String productId, ProductDto product);

    void deleteProduct(String productId);
}
