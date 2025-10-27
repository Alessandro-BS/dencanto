package com.proyecto.dencanto.Service;

import com.proyecto.dencanto.Modelo.Rol;
import com.proyecto.dencanto.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

    public Rol obtenerPorId(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }
    
    public Rol obtenerPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }
}