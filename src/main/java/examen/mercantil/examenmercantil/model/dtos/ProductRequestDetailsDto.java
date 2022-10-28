package examen.mercantil.examenmercantil.model.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDetailsDto {

    private String id;
    private String productId;
    private int quantity;
    private float priceUnit;
}
