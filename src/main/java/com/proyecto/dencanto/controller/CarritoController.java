package com.proyecto.dencanto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.dencanto.Modelo.Carrito;
import com.proyecto.dencanto.Modelo.DetalleCarrito;
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
                          @RequestParam(value = "cantidad", defaultValue = "1") int cantidad,
                          RedirectAttributes redirectAttributes) {
        Usuario u = obtenerUsuarioAutenticado();
        if (u == null) {
            throw new IllegalStateException("No se encontró el usuario de prueba (id=1)");
        }
        carritoService.agregarProducto(u, productoId, cantidad);
        redirectAttributes.addFlashAttribute("mensajeCarrito", "Producto agregado al carrito");
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
        BigDecimal subtotal = carritoService.calcularSubtotal(carrito).setScale(2, RoundingMode.HALF_UP);
        BigDecimal impuesto = carritoService.calcularImpuesto(carrito);
        BigDecimal totalPagar = subtotal.add(impuesto).setScale(2, RoundingMode.HALF_UP);
        int totalArticulos = carrito.getItems() == null ? 0 : carrito.getItems().stream()
                .mapToInt(DetalleCarrito::getCantidad)
                .sum();

        model.addAttribute("carrito", carrito);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("impuesto", impuesto);
        model.addAttribute("totalPagar", totalPagar);
        model.addAttribute("totalArticulos", totalArticulos);
        return "carrito/ver"; // plantilla thymeleaf
    }

    @PostMapping("/actualizar")
    public String actualizarCantidad(@RequestParam("detalleId") Integer detalleId,
                                     @RequestParam("cantidad") int cantidad,
                                     RedirectAttributes redirectAttributes) {
        Usuario u = obtenerUsuarioAutenticado();
        try {
            carritoService.actualizarCantidad(u, detalleId, cantidad);
            redirectAttributes.addFlashAttribute("mensajeCarrito", "Cantidad actualizada correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorCarrito", e.getMessage());
        }
        return "redirect:/carrito/ver";
    }

    @PostMapping("/eliminar")
    public String eliminarDetalle(@RequestParam("detalleId") Integer detalleId,
                                  RedirectAttributes redirectAttributes) {
        Usuario u = obtenerUsuarioAutenticado();
        try {
            carritoService.eliminarDetalle(u, detalleId);
            redirectAttributes.addFlashAttribute("mensajeCarrito", "Producto eliminado del carrito");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorCarrito", e.getMessage());
        }
        return "redirect:/carrito/ver";
    }

    @PostMapping("/cancelar")
    public String cancelarCarrito(RedirectAttributes redirectAttributes) {
        Usuario u = obtenerUsuarioAutenticado();
        carritoService.cancelarCarrito(u);
        redirectAttributes.addFlashAttribute("compraCancelada", true);
        return "redirect:/";
    }

    // 🔹 Confirmar compra
    @PostMapping("/confirmar")
    public String confirmarCompra(RedirectAttributes redirectAttributes) {
        Usuario u = obtenerUsuarioAutenticado();
        if (u == null) {
            throw new IllegalStateException("No se encontró el usuario de prueba (id=1)");
        }
        carritoService.confirmarCompra(u);
        redirectAttributes.addFlashAttribute("compraExitosa", true);
        return "redirect:/"; // después de confirmar, redirige al inicio
    }
}
