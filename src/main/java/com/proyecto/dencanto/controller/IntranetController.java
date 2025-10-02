package com.proyecto.dencanto.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/intranet")
public class IntranetController {

    // Página del login
    @GetMapping("/login")
    public String login() {
        return "intranet/login"; // templates/intranet/login.html
    }

    // Procesar login
    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        if ("admin".equals(username) && "1234".equals(password)) {
            session.setAttribute("usuario", "Administrador");
            session.setAttribute("rol", "ADMIN");
            model.addAttribute("success", true);
            return "redirect:/intranet/dashboard";
        } else if ("vendedor".equals(username) && "1234".equals(password)) {
            session.setAttribute("usuario", "Vendedor");
            session.setAttribute("rol", "VENDEDOR");
            model.addAttribute("success", true);
            return "redirect:/intranet/dashboard";
        } else {
            model.addAttribute("error", "Usuario y/o contraseña incorrectos");
            return "intranet/login";
        }
    }

    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "intranet/dashboard"; // templates/intranet/dashboard.html
    }

    // Administrador
    @GetMapping("/productos")
    public String gestionProductos(Model model) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/static/data/productos.json");
            List<Map<String, Object>> productos = mapper.readValue(
                    inputStream, new TypeReference<List<Map<String, Object>>>() {});
            model.addAttribute("productos", productos);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("productos", List.of()); // lista vacía si falla
        }
        return "intranet/productos"; // templates/intranet/productos.html
    }

    @GetMapping("/usuarios")
    public String gestionUsuarios() {
        return "intranet/usuarios";
    }

    @GetMapping("/cotizaciones")
    public String gestionCotizaciones() {
        return "intranet/cotizaciones";
    }

    

    @GetMapping("/reportes")
    public String gestionReportes() {
        return "intranet/reportes";
    }

    // Vendedor
    @GetMapping("/revisarCotizaciones")
    public String revisarCotizaciones() {
        return "intranet/cotizaciones";
    }

   

    @GetMapping("/historialVentas")
    public String gestionHistorialVentas() {
        return "intranet/reportes";
    }

     @GetMapping("/ventas")
    public String gestionVentas() {
        return "intranet/ventas";
    }
    
}
