package com.proyecto.dencanto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Homecontroller {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("titulo", "Inicio - Colchones D' Encanto");
        return "index";
    }

    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        model.addAttribute("titulo", "Productos - Fábrica de Colchones");
        return "productos";
    }

    @GetMapping("/ofertas")
    public String mostrarOfertas(Model model) {
        model.addAttribute("titulo", "Ofertas - Colchones D' Encanto");
        return "ofertas";
    }

    @GetMapping("/nosotros")
    public String mostrarNosotros(Model model){
        model.addAttribute("titulo", "Nosotros - Colchones D' Encanto");
        return "nosotros";
    }

    @GetMapping("/FAQ")
    public String mostrarFAQ(Model model){
        model.addAttribute("titulo", "FAQ - Colchones D' Encanto");
        return "FAQ";
    }

    @GetMapping("/ubicanos")
    public String mostrarUbicanos(Model model){
        model.addAttribute("titulo", "Ubicanos - Colchones D' Encanto");
        return "ubicanos";
    }
}

