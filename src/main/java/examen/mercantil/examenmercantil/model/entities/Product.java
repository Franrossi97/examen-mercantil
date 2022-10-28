package examen.mercantil.examenmercantil.model.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "productos")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, name = "id")
    private String id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "descripcion_corta", nullable = false)
    private String shortDescription;

    @Column(name = "descripcion_larga", nullable = false)
    private String longDescription;

    @Column(name = "precio_unitario", nullable = false)
    private float unitPrice;
}
