package com.puce.libreria.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.puce.libreria.Entity.Libro;
import com.puce.libreria.Exception.LibroDuplicadoException;
import com.puce.libreria.Exception.LibroNoEncontradoException;
import com.puce.libreria.Repositorio.LibroRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LibroServiceTests {

    @Mock
    private LibroRepository repositorio;

    private LibroService servicio;

    @BeforeEach
    void configurar() {
        servicio = new LibroService(repositorio);
    }

    @Test
    void registraLibroYDevuelveEntidadGuardada() {
        Libro nuevo = new Libro(null, "  El Principito  ", "  Salamandra ", "Novela", 96);
        when(repositorio.existsByTituloIgnoreCase("El Principito")).thenReturn(false);
        when(repositorio.save(any(Libro.class))).thenAnswer(invocacion -> {
            Libro entidad = invocacion.getArgument(0);
            entidad.setId(1L);
            return entidad;
        });

        Libro guardado = servicio.guardarLibro(nuevo);

        assertEquals(Long.valueOf(1L), guardado.getId());
        assertEquals("El Principito", guardado.getTitulo());
        assertEquals("Salamandra", guardado.getEditorial());
        verify(repositorio).save(any(Libro.class));
    }

    @Test
    void rechazaTituloDuplicadoSinDistinguirMayusculas() {
        Libro nuevo = new Libro(null, "El Principito", "Editorial", "Novela", 96);
        when(repositorio.existsByTituloIgnoreCase("El Principito")).thenReturn(true);

        assertThrows(LibroDuplicadoException.class, () -> servicio.guardarLibro(nuevo));
    }

    @Test
    void actualizaLibroExistenteSinCrearOtro() {
        Libro existente = new Libro(3L, "Anterior", "Editorial", "Novela", 100);
        Libro cambios = new Libro(3L, "Nuevo título", "Otra", "Historia", 120);
        when(repositorio.existsByTituloIgnoreCaseAndIdNot("Nuevo título", 3L)).thenReturn(false);
        when(repositorio.findById(3L)).thenReturn(Optional.of(existente));
        when(repositorio.save(existente)).thenReturn(existente);

        Libro guardado = servicio.guardarLibro(cambios);

        assertEquals(Long.valueOf(3L), guardado.getId());
        assertEquals("Nuevo título", guardado.getTitulo());
        assertEquals(Integer.valueOf(120), guardado.getNumeroPagina());
    }

    @Test
    void buscarLibroNoExistenteDevuelveError404DeNegocio() {
        when(repositorio.findById(404L)).thenReturn(Optional.empty());
        assertThrows(LibroNoEncontradoException.class, () -> servicio.buscarPorId(404L));
    }

    @Test
    void eliminaUnLibroExistente() {
        Libro libro = new Libro(4L, "Libro", "Editorial", "Novela", 90);
        when(repositorio.findById(4L)).thenReturn(Optional.of(libro));

        servicio.eliminarLibro(4L);

        verify(repositorio).delete(libro);
    }
}
