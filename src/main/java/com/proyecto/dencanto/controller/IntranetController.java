package com.proyecto.dencanto.controller;


import jakarta.servlet.http.HttpSession;
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
        return "intranet/historialVentas";
    }

     @GetMapping("/ventas")
    public String gestionVentas() {
        return "intranet/ventas";
    }
    
}
