package com.proyecto.dencanto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.dencanto.Modelo.Carrito;
import com.proyecto.dencanto.Modelo.Usuario;
import com.proyecto.dencanto.Repository.UsuarioRepository;
import com.proyecto.dencanto.Service.CarritoService;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    private final CarritoService carritoService;

    @Autowired
    private final UsuarioRepository usuarioRepo;

    public CarritoController(CarritoService carritoService, UsuarioRepository usuarioRepo) {
        this.carritoService = carritoService;
        this.usuarioRepo = usuarioRepo;
    }

    // ✅ Por ahora usamos un usuario fijo (id = 1) para pruebas
    private Usuario obtenerUsuarioAutenticado() {
        return usuarioRepo.findById(1).orElse(null);
    }

    // 🔹 Agregar producto al carrito
    @PostMapping("/agregar")
    public String agregar(@RequestParam("productoId") Integer productoId,
                          @RequestParam(value = "cantidad", defaultValue = "1") int cantidad) {
        Usuario u = obtenerUsuarioAutenticado();
        if (u == null) {
            throw new IllegalStateException("No se encontró el usuario de prueba (id=1)");
        }
        carritoService.agregarProducto(u, productoId, cantidad);
        return "redirect:/carrito/ver";
    }

    // 🔹 Ver carrito actual
    @GetMapping("/ver")
    public String verCarrito(Model model) {
        Usuario u = obtenerUsuarioAutenticado();
        if (u == null) {
            throw new IllegalStateException("No se encontró el usuario de prueba (id=1)");
        }
        Carrito carrito = carritoService.obtenerCarritoActivoParaUsuario(u);
        model.addAttribute("carrito", carrito);
        return "carrito/ver"; // plantilla thymeleaf
    }

    // 🔹 Confirmar compra
    @PostMapping("/confirmar")
    public String confirmarCompra() {
        Usuario u = obtenerUsuarioAutenticado();
        if (u == null) {
            throw new IllegalStateException("No se encontró el usuario de prueba (id=1)");
        }
        carritoService.confirmarCompra(u);
        return "redirect:/intranet/ventas"; // después de confirmar, redirige a las ventas
    }
}
