package com.puce.libreria.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Representa un libro guardado en la tabla libros de la base de datos H2. */
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ingrese el título del libro")
    @Size(max = 160, message = "El título no debe superar los 160 caracteres")
    @Column(name = "titulo", nullable = false, unique = true, length = 160)
    private String titulo;

    @NotBlank(message = "Ingrese la editorial")
    @Size(max = 120, message = "La editorial no debe superar los 120 caracteres")
    @Column(name = "editorial", nullable = false, length = 120)
    private String editorial;

    @NotBlank(message = "Seleccione un género")
    @Size(max = 60, message = "El género no debe superar los 60 caracteres")
    @Column(name = "genero", nullable = false, length = 60)
    private String genero;

    @NotNull(message = "Ingrese el número de páginas")
    @Min(value = 1, message = "El número de páginas debe ser mayor que cero")
    @Max(value = 20000, message = "El número de páginas no puede superar 20.000")
    @Column(name = "numero_pagina", nullable = false)
    private Integer numeroPagina;

    public Libro() {
        // JPA necesita un constructor sin argumentos.
    }

    public Libro(Long id, String titulo, String editorial, String genero, Integer numeroPagina) {
        this.id = id;
        this.titulo = titulo;
        this.editorial = editorial;
        this.genero = genero;
        this.numeroPagina = numeroPagina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(Integer numeroPagina) {
        this.numeroPagina = numeroPagina;
    }
}
