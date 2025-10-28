package com.proyecto.dencanto.controller;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.dencanto.Modelo.Venta;
import com.proyecto.dencanto.Repository.VentaRepository;



@Controller
@RequestMapping("/intranet")
public class IntranetController {

    // Modificaciones avance 3:
    // Muestre todas las ventas registradas en la tabla ventas.
    // Liste sus detalles (productos, cantidades, precios) desde detalle_venta.
    // Permita ver el detalle de cada venta en el modal.
    private final VentaRepository ventaRepository;
    public IntranetController(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }
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
    public String gestionHistorialVentas(Model model) {
        List<Venta> ventas = ventaRepository.findAllByOrderByFechaCreacionDesc();
        BigDecimal montoTotal = ventas.stream()
                .map(Venta::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        int cantidadVentas = ventas.size();
        BigDecimal promedio = cantidadVentas > 0
                ? montoTotal.divide(BigDecimal.valueOf(cantidadVentas), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal comision = montoTotal.multiply(BigDecimal.valueOf(0.10)).setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("ventas", ventas);
        model.addAttribute("montoTotalVentas", montoTotal);
        model.addAttribute("cantidadVentas", cantidadVentas);
        model.addAttribute("promedioVenta", promedio);
        model.addAttribute("comisionEstimada", comision);
        return "intranet/historialVentas";
    }

     @GetMapping("/ventas")
    public String gestionVentas() {
        return "intranet/ventas";
    }


    
}
