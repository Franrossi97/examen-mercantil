package examen.mercantil.examenmercantil.model.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private float unitPrice;
}
