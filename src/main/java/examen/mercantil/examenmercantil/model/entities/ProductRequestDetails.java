package examen.mercantil.examenmercantil.model.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "pedidos_detalle")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDetails {

    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, name = "id")
    private String id;

    /*@ManyToOne
    @JoinColumn(name = "pedidos_cabecera")
    private String requestHeaderId;*/

    @Column(name = "producto_id", nullable = false)
    private String productId;

    @Column(name = "cantidad", nullable = false)
    private int quantity;

    @Column(name = "precio_unitario", nullable = false)
    private float priceUnit;
}
