package examen.mercantil.examenmercantil.services;

import examen.mercantil.examenmercantil.daos.ProductDao;
import examen.mercantil.examenmercantil.daos.ProductRequestHeaderDao;
import examen.mercantil.examenmercantil.model.dtos.ProductRequestDetailsDto;
import examen.mercantil.examenmercantil.model.dtos.ProductRequestHeaderDto;
import examen.mercantil.examenmercantil.model.entities.Product;
import examen.mercantil.examenmercantil.model.entities.ProductRequestHeader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductRequestCreationServiceImpl {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductRequestHeaderDao productRequestDao;

    public ProductRequestHeader createProductRequest(ProductRequestHeaderDto productRequest) throws ResponseStatusException {

        if(!this.verifyNewProductRequest(productRequest)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Los parámetros ingresados no son válidos.");
        }

        ModelMapper modelMapper = new ModelMapper();

        productRequest = this.setRequestDetails(productRequest);
        productRequest = this.setRequestTotalPrice(productRequest);
        productRequest = this.checkDiscount(productRequest);
        productRequest = this.setDateAndHour(productRequest);
        productRequest.setStatus("PENDING");

        return this.productRequestDao.save(modelMapper.map(productRequest, ProductRequestHeader.class));
    }

    private boolean verifyNewProductRequest(ProductRequestHeaderDto productRequest) {
        return productRequest.getAddress() != null &&
                productRequest.getEmail() != null &&
                productRequest.getPhone() != null &&
                productRequest.getTime() != null &&
                this.checkProductRequestDetails(productRequest.getProductRequestDetails());
    }

    private boolean checkProductRequestDetails(List<ProductRequestDetailsDto> productRequestDetailsDtos) {
        boolean res = true;

        for(int i = 0; res && i < productRequestDetailsDtos.size(); i++) {
            res = productRequestDetailsDtos.get(i).getQuantity() > 0;
        }

        return res;
    }

    private ProductRequestHeaderDto checkDiscount(ProductRequestHeaderDto productRequest) {
        AtomicInteger cont = new AtomicInteger();

        productRequest.getProductRequestDetails().forEach((productRequestDetails) -> {
            cont.addAndGet(productRequestDetails.getQuantity());
        });

        if(cont.get() >= 3) {
            productRequest.setDiscountApplied(true);
            productRequest.setTotalAmount((float) (productRequest.getTotalAmount() * 0.7));
        } else {
            productRequest.setDiscountApplied(false);
        }

        return productRequest;
    }

    private ProductRequestHeaderDto setDateAndHour(ProductRequestHeaderDto productRequest) {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        productRequest.setCreationDate(localDate.getDayOfMonth() + "-" + localDate.getMonthValue() + "-" + localDate.getYear());
        productRequest.setTime(localTime.getHour() + ":" + localTime.getMinute());

        return productRequest;
    }

    private ProductRequestHeaderDto setRequestDetails(ProductRequestHeaderDto productRequest) {
        List<Product> products = this.getProductsById(this.generateListOfProductId(productRequest.getProductRequestDetails()));
        Map<String, Product> productsById = this.generateMapOfProductsById(products);

        productRequest.getProductRequestDetails().forEach((request) -> {
            request.setPriceUnit(productsById.get(request.getProductId()).getUnitPrice());
        });

        return productRequest;
    }

    private ProductRequestHeaderDto setRequestTotalPrice(ProductRequestHeaderDto productRequest) {
        AtomicReference<Float> totalPrice = new AtomicReference<>((float) 0);

        productRequest.getProductRequestDetails().forEach((request) -> {
            totalPrice.updateAndGet(v -> (float) (v + request.getPriceUnit() * request.getQuantity()));
        });

        productRequest.setTotalAmount(totalPrice.get());

        return productRequest;
    }

    private Set<String> generateListOfProductId(List<ProductRequestDetailsDto> productRequestDetails) {
        Set<String> productsId = new HashSet<>();

        productRequestDetails.forEach((detail) -> {
            productsId.add(detail.getProductId());
        });

        return productsId;
    }

    private List<Product> getProductsById(Set<String> productsId) {
        List<Product> aux = this.productDao.findAllById(productsId);
        return aux;
    }

    private Map<String, Product> generateMapOfProductsById(List<Product> products) {
        Map<String, Product> productsById = new HashMap<>();

        products.forEach((product -> {
            productsById.put(product.getId(), product);
        }));

        return productsById;
    }
}
