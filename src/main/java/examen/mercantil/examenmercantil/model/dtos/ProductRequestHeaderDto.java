package examen.mercantil.examenmercantil.model.dtos;

import examen.mercantil.examenmercantil.model.entities.ProductRequestDetails;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestHeaderDto {

    private String id;
    private String address;
    private String email;
    private String phone;
    private String time;
    private String creationDate;
    private float totalAmount;
    private boolean discountApplied;
    private String status;
    private List<ProductRequestDetails> productRequestDetails;
}
