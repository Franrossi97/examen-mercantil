package examen.mercantil.examenmercantil.services;

import examen.mercantil.examenmercantil.daos.ProductDao;
import examen.mercantil.examenmercantil.model.dtos.ProductDto;
import examen.mercantil.examenmercantil.model.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductDao productDao;

    @Override
    public Product createProduct(ProductDto product) {
        ModelMapper modelMapper = new ModelMapper();
        /*Product productEntity = Product.builder()
                .longDescription(product.getLongDescription())
                .name(product.getName())
                .shortDescription(product.getShortDescription())
                .unitPrice(product.getUnitPrice()).build();*/

        return this.productDao.save(modelMapper.map(product, Product.class));
    }

    @Override
    public Product findProduct(String productId) throws ResponseStatusException {

        Product productOpt = this.productDao.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "El producto con id=" + productId + " no existe."
                ));

        return productOpt;
    }

    @Override
    public Product editProduct(String productId, ProductDto product) {
        ModelMapper modelMapper = new ModelMapper();
        product.setId(productId);

        return this.productDao.save(modelMapper.map(product, Product.class));
    }

    @Override
    public void deleteProduct(String productId) {
        this.productDao.deleteById(productId);
    }
}