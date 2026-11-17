package com.puce.libreria.Controller;

import com.puce.libreria.Entity.Libro;
import com.puce.libreria.Exception.LibroDuplicadoException;
import com.puce.libreria.Service.LibroService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
public class LibroController {

    private static final Logger log = LoggerFactory.getLogger(LibroController.class);

    private final LibroService libroService;

    private static final List<String> GENEROS = List.of(
            "Novela", "Romance", "Terror", "Fantasía", "Historia", "Ciencia ficción", "Otro");

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // GET /libros y GET /libros?busqueda=texto
    @GetMapping
    public String listar(@RequestParam(name = "busqueda", required = false) String busqueda,
                         Model model) {
        model.addAttribute("libros", libroService.listarLibros(busqueda));
        model.addAttribute("busqueda", busqueda == null ? "" : busqueda);
        return "pages/libros";
    }

    // GET /libros/formulario
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("libro", new Libro());
        prepararFormulario(model, false);
        return "pages/libroform";
    }

    // GET /libros/12/editar
    @GetMapping("/{id}/editar")
    public String mostrarEdicion(@PathVariable Long id, Model model) {
        model.addAttribute("libro", libroService.buscarPorId(id));
        prepararFormulario(model, true);
        return "pages/libroform";
    }

    // POST /libros/guardar
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("libro") Libro libro,
                          BindingResult errores,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        boolean editando = libro.getId() != null;
        if (errores.hasErrors()) {
            prepararFormulario(model, editando);
            return "pages/libroform";
        }

        try {
            libroService.guardarLibro(libro);
            redirectAttributes.addFlashAttribute("mensajeExito", editando
                    ? "Libro actualizado correctamente."
                    : "Libro registrado correctamente.");
            return "redirect:/libros";
        } catch (LibroDuplicadoException ex) {
            errores.rejectValue("titulo", "libro.duplicado", ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            log.warn("No se pudo guardar el libro por una restricción de base de datos", ex);
            model.addAttribute("mensajeError",
                    "No se pudo guardar. Revise que el título no esté duplicado.");
        }

        prepararFormulario(model, editando);
        return "pages/libroform";
    }

    // POST /libros/12/eliminar (no se borra desde un enlace GET)
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        libroService.eliminarLibro(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Libro eliminado correctamente.");
        return "redirect:/libros";
    }

    private void prepararFormulario(Model model, boolean editando) {
        model.addAttribute("generos", GENEROS);
        model.addAttribute("editando", editando);

    }
}
