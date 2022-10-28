package examen.mercantil.examenmercantil.daos;

import examen.mercantil.examenmercantil.model.entities.ProductRequestHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRequestHeaderDao extends JpaRepository<ProductRequestHeader, String> {

    List<ProductRequestHeader> findByCreationDate(String creationDate);
}
