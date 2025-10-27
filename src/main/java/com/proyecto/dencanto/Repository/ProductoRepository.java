package com.proyecto.dencanto.Repository;

import com.proyecto.dencanto.Modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Métodos personalizados si es necesario
}

