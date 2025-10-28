package com.proyecto.dencanto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.dencanto.Modelo.DetalleVenta;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta,Integer> {
    
}
