package examen.mercantil.examenmercantil.controllers;

import examen.mercantil.examenmercantil.model.dtos.ProductDto;
import examen.mercantil.examenmercantil.model.entities.Product;
import examen.mercantil.examenmercantil.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity createProduct(@RequestBody ProductDto product) {

        return new ResponseEntity<Product>(this.productService.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{product_id}")
    public ResponseEntity getProduct(@PathVariable("product_id") String productId) {

        try {
            return new ResponseEntity<Product>(this.productService.findProduct(productId), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{product_id}")
    public ResponseEntity editProduct(@PathVariable("product_id") String productId,
                                               @RequestBody ProductDto product) {
        return new ResponseEntity<>(this.productService.editProduct(productId, product), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{product_id}")
    public ResponseEntity deleteProduct(@PathVariable("product_id") String productId) {
        this.productService.deleteProduct(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
