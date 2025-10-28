package com.proyecto.dencanto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.dencanto.Modelo.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Integer> {

    @EntityGraph(attributePaths = {"usuario", "detalles", "detalles.producto"})
    List<Venta> findAllByOrderByFechaCreacionDesc();
}
