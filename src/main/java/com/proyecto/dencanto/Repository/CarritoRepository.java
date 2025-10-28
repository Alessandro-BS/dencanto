package com.proyecto.dencanto.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.dencanto.Modelo.Carrito;
import com.proyecto.dencanto.Modelo.Usuario;

public interface CarritoRepository extends JpaRepository<Carrito,Integer> {
    Optional<Carrito> findByUsuarioAndEstado(Usuario usuario, String estado);
}
