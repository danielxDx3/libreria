package com.puce.libreria.Exception;

public class LibroDuplicadoException extends RuntimeException {

    public LibroDuplicadoException(String titulo) {
        super("Ya existe un libro registrado con el título: " + titulo);
    }
}
