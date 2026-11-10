package com.puce.libreria.Service;

import com.puce.libreria.Entity.Libro;
import com.puce.libreria.Repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;


    public Libro guardarLibro (Libro libro){
        libroRepository.save(libro);
        
    }
}
