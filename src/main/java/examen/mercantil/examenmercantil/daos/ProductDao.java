package examen.mercantil.examenmercantil.daos;

import examen.mercantil.examenmercantil.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, String> {
}
