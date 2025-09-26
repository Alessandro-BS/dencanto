package com.proyecto.dencanto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/intranet")
public class IntranetController {
    // Página del login
    @GetMapping("/login")
    public String login() {
        return "intranet/login"; // templates/intranetlogin.html
    }

    // Procesar login
    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        // Conexion luego a BD
        if ("admin".equals(username) && "1234".equals(password)) {
            model.addAttribute("usuario", "Administrador");
            model.addAttribute("rol", "ADMIN");
            model.addAttribute("success", true); // señal para modal
            return "intranet/login";
        } else if ("vendedor".equals(username) && "1234".equals(password)) {
            model.addAttribute("usuario", "Vendedor");
            model.addAttribute("rol", "VENDEDOR");
            model.addAttribute("success", true); // señal para modal
            return "intranet/login";
        } else {
            model.addAttribute("error", "Usuario y/o contraseña incorrectos");
            return "intranet/login"; // vuelve al login con error
        }
    }

    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(required = false) String usuario,
            @RequestParam(required = false) String rol,
            Model model) {
        if (usuario == null)
            usuario = "Invitado";
        if (rol == null)
            rol = "INVITADO";

        model.addAttribute("usuario", usuario);
        model.addAttribute("rol", rol);
        return "intranet/dashboard"; // templates/intranet/dashboard.html
    }

    // Modulos de la intranet
    @GetMapping("/productos")
    public String gestionProductos() {
        return "intranet/productos"; // templates/intranet/usuarios.html
    }

    @GetMapping("/usuarios")
    public String gestionUsuarios() {
        return "intranet/usuarios"; // templates/intranet/usuarios.html
    }

    @GetMapping("/cotizaciones")
    public String gestionCotizaciones() {
        return "intranet/cotizaciones"; // templates/intranet/cotizaciones.html
    }

    @GetMapping("/ventas")
    public String gestionVentas() {
        return "intranet/ventas"; // templates/intranet/ventas.html
    }

    @GetMapping("/reportes")
    public String gestionReportes() {
        return "intranet/reportes"; // templates/intranet/reportes.html
    }
}
