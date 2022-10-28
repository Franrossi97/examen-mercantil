package examen.mercantil.examenmercantil.model.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pedidos_cabecera")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestHeader {

    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, name = "id")
    private String id;

    @Column(name = "direccion", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefono", nullable = false)
    private String phone;

    @Column(name = "horario", nullable = false)
    private String time;

    @Column(name = "fecha_alta", nullable = false)
    private String creationDate;

    @Column(name = "monto_total", nullable = false)
    private float totalAmount;

    @Column(name = "aplico_descuento", nullable = false)
    private boolean discountApplied;

    @Column(name = "estado", nullable = false)
    private String status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_cabecera")
    private List<ProductRequestDetails> productRequestDetails;
}
