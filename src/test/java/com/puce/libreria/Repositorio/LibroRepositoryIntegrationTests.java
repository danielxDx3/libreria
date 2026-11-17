package com.puce.libreria.Repositorio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.puce.libreria.Entity.Libro;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/** Comprueba que el repositorio guarda y consulta libros en H2. */
@SpringBootTest
@Transactional
class LibroRepositoryIntegrationTests {

    @Autowired
    private LibroRepository repositorio;

    @Test
    void guardaYBuscaLibroEnH2() {
        Libro nuevo = new Libro(null, "Programación Java", "Editorial PUCE", "Historia", 340);
        Libro guardado = repositorio.saveAndFlush(nuevo);

        assertTrue(guardado.getId() != null);
        List<Libro> encontrados = repositorio
                .findByTituloContainingIgnoreCaseOrEditorialContainingIgnoreCaseOrderByTituloAsc(
                        "programación", "programación");
        assertEquals(1, encontrados.stream()
                .filter(libro -> libro.getId().equals(guardado.getId())).count());
    }
}
