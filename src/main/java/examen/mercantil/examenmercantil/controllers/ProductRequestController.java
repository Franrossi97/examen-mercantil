package examen.mercantil.examenmercantil.controllers;

import examen.mercantil.examenmercantil.model.dtos.ProductRequestHeaderDto;
import examen.mercantil.examenmercantil.services.ProductRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requestProducts")
public class ProductRequestController {

    @Autowired
    ProductRequestService productRequestService;

    @PostMapping(value = "/create")
    public ResponseEntity createProductRequest(@RequestBody ProductRequestHeaderDto productRequestDto) {
        return new ResponseEntity(this.productRequestService.createProductRequest(productRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/find/{date}")
    public ResponseEntity getRequestProductByDate(@PathVariable("date") String date) {
        System.out.println(date);
        return new ResponseEntity(this.productRequestService.findProductRequestByDate(date), HttpStatus.OK);
    }
}
