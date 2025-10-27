package com.proyecto.dencanto.Repository;

import com.proyecto.dencanto.Modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Métodos personalizados si es necesario
}
