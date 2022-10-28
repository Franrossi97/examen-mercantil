package examen.mercantil.examenmercantil.services;

import examen.mercantil.examenmercantil.model.dtos.ProductRequestHeaderDto;
import examen.mercantil.examenmercantil.model.entities.ProductRequestHeader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductRequestService {

    ProductRequestHeader createProductRequest(ProductRequestHeaderDto productRequest);

    List<ProductRequestHeader> findProductRequestByDate(String date);
}
