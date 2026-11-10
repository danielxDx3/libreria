package com.puce.libreria.Repositorio;


import com.puce.libreria.Entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository <Libro, Long> {
}
