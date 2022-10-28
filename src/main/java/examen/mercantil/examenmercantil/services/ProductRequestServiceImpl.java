package examen.mercantil.examenmercantil.services;

import examen.mercantil.examenmercantil.daos.ProductRequestHeaderDao;
import examen.mercantil.examenmercantil.model.dtos.ProductRequestHeaderDto;
import examen.mercantil.examenmercantil.model.entities.ProductRequestHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRequestServiceImpl implements ProductRequestService {

    @Autowired
    ProductRequestCreationServiceImpl productRequestCreationService;

    @Autowired
    ProductRequestHeaderDao productRequestDao;

    @Override
    public ProductRequestHeader createProductRequest(ProductRequestHeaderDto productRequest) {
        return this.productRequestCreationService.createProductRequest(productRequest);
    }

    @Override
    public List<ProductRequestHeader> findProductRequestByDate(String date) {
        return this.productRequestDao.findByCreationDate(date);
    }
}
