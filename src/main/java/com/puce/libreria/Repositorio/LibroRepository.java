package com.puce.libreria.Repositorio;

import com.puce.libreria.Entity.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Consultas a la tabla libros; Spring Data JPA genera la implementación. */
public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTituloIgnoreCase(String titulo);

    boolean existsByTituloIgnoreCaseAndIdNot(String titulo, Long id);

    List<Libro> findByTituloContainingIgnoreCaseOrEditorialContainingIgnoreCaseOrderByTituloAsc(
            String titulo, String editorial);
}
