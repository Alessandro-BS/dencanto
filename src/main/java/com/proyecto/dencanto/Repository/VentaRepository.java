package com.proyecto.dencanto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.dencanto.Modelo.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Integer> {

}
