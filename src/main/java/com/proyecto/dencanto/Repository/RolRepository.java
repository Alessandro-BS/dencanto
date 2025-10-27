package com.proyecto.dencanto.Repository;

import com.proyecto.dencanto.Modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String nombre);
}