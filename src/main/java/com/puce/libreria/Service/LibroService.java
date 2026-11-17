package com.puce.libreria.Service;

import com.puce.libreria.Entity.Libro;
import com.puce.libreria.Exception.LibroDuplicadoException;
import com.puce.libreria.Exception.LibroNoEncontradoException;
import com.puce.libreria.Repositorio.LibroRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Lógica de negocio: consultar, registrar, actualizar y eliminar libros. */
@Service
@Transactional(readOnly = true)
public class LibroService {

    private final LibroRepository libroRepository;

    // Inyección por constructor: facilita las pruebas y evita dependencias nulas.
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> listarLibros(String busqueda) {
        if (busqueda == null || busqueda.isBlank()) {
            return libroRepository.findAll(Sort.by(Sort.Direction.ASC, "titulo"));
        }

        String texto = busqueda.strip();
        return libroRepository
                .findByTituloContainingIgnoreCaseOrEditorialContainingIgnoreCaseOrderByTituloAsc(
                        texto, texto);
    }

    public Libro buscarPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new LibroNoEncontradoException(id));
    }

    @Transactional
    public Libro guardarLibro(Libro datos) {
        Objects.requireNonNull(datos, "Los datos del libro son obligatorios");

        String titulo = normalizar(datos.getTitulo());
        String editorial = normalizar(datos.getEditorial());
        String genero = normalizar(datos.getGenero());

        // Comprueba duplicados también al editar: el propio ID no cuenta.
        boolean tituloDuplicado = datos.getId() == null
                ? libroRepository.existsByTituloIgnoreCase(titulo)
                : libroRepository.existsByTituloIgnoreCaseAndIdNot(titulo, datos.getId());

        if (tituloDuplicado) {
            throw new LibroDuplicadoException(titulo);
        }

        // Si se edita, se recupera la entidad actual para evitar insertar otra fila.
        Libro libro = datos.getId() == null ? new Libro() : buscarPorId(datos.getId());
        libro.setTitulo(titulo);
        libro.setEditorial(editorial);
        libro.setGenero(genero);
        libro.setNumeroPagina(datos.getNumeroPagina());

        return libroRepository.save(libro);
    }

    @Transactional
    public void eliminarLibro(Long id) {
        Libro libro = buscarPorId(id);
        libroRepository.delete(libro);
    }

    private String normalizar(String valor) {
        return valor == null ? "" : valor.strip();
    }
}
