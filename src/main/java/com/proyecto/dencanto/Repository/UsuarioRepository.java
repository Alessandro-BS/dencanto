package com.proyecto.dencanto.Repository;

import com.proyecto.dencanto.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Métodos personalizados si es necesario
}
