package com.proyecto.dencanto.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.proyecto.dencanto.Modelo.Carrito;
import com.proyecto.dencanto.Modelo.DetalleCarrito;
import com.proyecto.dencanto.Modelo.DetalleVenta;
import com.proyecto.dencanto.Modelo.Producto;
import com.proyecto.dencanto.Modelo.Usuario;
import com.proyecto.dencanto.Modelo.Venta;
import com.proyecto.dencanto.Repository.CarritoRepository;
import com.proyecto.dencanto.Repository.DetalleCarritoRepository;
import com.proyecto.dencanto.Repository.DetalleVentaRepository;
import com.proyecto.dencanto.Repository.ProductoRepository;
import com.proyecto.dencanto.Repository.UsuarioRepository;
import com.proyecto.dencanto.Repository.VentaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarritoService {
    private final CarritoRepository carritoRepo;
    private final DetalleCarritoRepository detalleRepo;
    private final ProductoRepository productoRepo;
    private final VentaRepository ventaRepo;
    private final DetalleVentaRepository detalleVentaRepo;
    // private final UsuarioRepository usuarioRepo;

    public CarritoService(CarritoRepository carritoRepo,
                          DetalleCarritoRepository detalleRepo,
                          ProductoRepository productoRepo,
                          VentaRepository ventaRepo,
                          DetalleVentaRepository detalleVentaRepo,
                          UsuarioRepository usuarioRepo) {
        this.carritoRepo = carritoRepo;
        this.detalleRepo = detalleRepo;
        this.productoRepo = productoRepo;
        this.ventaRepo = ventaRepo;
        this.detalleVentaRepo = detalleVentaRepo;
        // this.usuarioRepo = usuarioRepo;
    }

    public Carrito obtenerCarritoActivoParaUsuario(Usuario usuario) {
        Carrito carrito = carritoRepo.findByUsuarioAndEstado(usuario, "ACTIVO")
                .orElseGet(() -> {
                    Carrito c = Carrito.builder()
                            .usuario(usuario)
                            .estado("ACTIVO")
                            .total(BigDecimal.ZERO)
                            .fechaCreacion(LocalDateTime.now())
                            .fechaActualizacion(LocalDateTime.now())
                            .build();
                    return carritoRepo.save(c);
                });
        asegurarListaItems(carrito);
        return carrito;
    }

    public Carrito agregarProducto(Usuario usuario, Integer productoId, int cantidad) {
    Producto producto = productoRepo.findById(productoId)
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

    Carrito carrito = obtenerCarritoActivoParaUsuario(usuario);
    asegurarListaItems(carrito);

    // Buscar si ya existe item del mismo producto
    DetalleCarrito existente = carrito.getItems() == null ? null :
            carrito.getItems().stream()
            .filter(it -> it.getProducto().getId().equals(productoId))
            .findFirst().orElse(null);

    if (existente != null) {
        existente.setCantidad(existente.getCantidad() + cantidad);
        detalleRepo.save(existente);
    } else {
        DetalleCarrito nuevo = DetalleCarrito.builder()
                .carrito(carrito)
                .producto(producto)
                .cantidad(cantidad)
                .precioUnitario(BigDecimal.valueOf(producto.getPrecio())) // 👈 AQUÍ ESTÁ LA LÍNEA CLAVE
                .build();

        detalleRepo.save(nuevo);
        carrito.getItems().add(nuevo);
    }

    recalcularTotal(carrito);
    return carritoRepo.save(carrito);
}

    public Carrito actualizarCantidad(Usuario usuario, Integer detalleId, int cantidad) {
        DetalleCarrito detalle = detalleRepo.findById(detalleId)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado"));
        validarPropietario(detalle.getCarrito(), usuario);
        if (cantidad <= 0) {
            detalle.getCarrito().getItems().remove(detalle);
            detalleRepo.delete(detalle);
        } else {
            detalle.setCantidad(cantidad);
            detalleRepo.save(detalle);
        }
        Carrito c = carritoRepo.findById(detalle.getCarrito().getId()).get();
        recalcularTotal(c);
        return carritoRepo.save(c);
    }

    public Carrito eliminarDetalle(Usuario usuario, Integer detalleId) {
        DetalleCarrito detalle = detalleRepo.findById(detalleId)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado"));
        Carrito carrito = detalle.getCarrito();
        validarPropietario(carrito, usuario);
        carrito.getItems().remove(detalle);
        detalleRepo.delete(detalle);
        recalcularTotal(carrito);
        return carritoRepo.save(carrito);
    }

    public void cancelarCarrito(Usuario usuario) {
        Carrito carrito = obtenerCarritoActivoParaUsuario(usuario);
        if (!carrito.getItems().isEmpty()) {
            detalleRepo.deleteAll(carrito.getItems());
            carrito.getItems().clear();
        }
        carrito.setEstado("CANCELADO");
        carrito.setTotal(BigDecimal.ZERO);
        carrito.setFechaActualizacion(LocalDateTime.now());
        carritoRepo.save(carrito);
    }

    private void asegurarListaItems(Carrito carrito) {
        if (carrito.getItems() == null) {
            carrito.setItems(new ArrayList<>());
        }
    }

    private void validarPropietario(Carrito carrito, Usuario usuario) {
        if (carrito == null || carrito.getUsuario() == null || usuario == null
                || !carrito.getUsuario().getId().equals(usuario.getId())
                || !"ACTIVO".equalsIgnoreCase(carrito.getEstado())) {
            throw new IllegalStateException("No tiene acceso a este carrito");
        }
    }

    private void recalcularTotal(Carrito carrito) {
        BigDecimal total = carrito.getItems() == null ? BigDecimal.ZERO :
                carrito.getItems().stream()
                    .map(it -> it.getPrecioUnitario().multiply(BigDecimal.valueOf(it.getCantidad())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        carrito.setTotal(total);
        carrito.setFechaActualizacion(LocalDateTime.now());
    }

    public void confirmarCompra(Usuario usuario) {
        Carrito carrito = obtenerCarritoActivoParaUsuario(usuario);
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new IllegalStateException("Carrito vacío");
        }

        // crear venta
        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setTotal(carrito.getTotal());
        venta.setEstado("COMPLETADO");
        venta.setFechaCreacion(LocalDateTime.now());
        venta.setFechaActualizacion(LocalDateTime.now());
        venta = ventaRepo.save(venta);

        // crear detalle_venta por cada detalle_carrito
        for (DetalleCarrito it : carrito.getItems()) {
            DetalleVenta dv = DetalleVenta.builder()
                    .venta(venta)
                    .producto(it.getProducto())
                    .cantidad(it.getCantidad())
                    .precioUnitario(it.getPrecioUnitario())
                    .build();
            detalleVentaRepo.save(dv);

            // actualizar stock (simple)
            Producto p = it.getProducto();
            p.setStock(p.getStock() - it.getCantidad());
            if (p.getStock() <= 0) p.setEstado("Agotado");
            productoRepo.save(p);
        }

        // archivar carrito
        carrito.setEstado("COMPRADO");
        carrito.setFechaActualizacion(LocalDateTime.now());
        carritoRepo.save(carrito);
    }

    public BigDecimal calcularSubtotal(Carrito carrito) {
        return carrito.getTotal() == null ? BigDecimal.ZERO : carrito.getTotal();
    }

    public BigDecimal calcularImpuesto(Carrito carrito) {
        return calcularSubtotal(carrito).multiply(BigDecimal.valueOf(0.18)).setScale(2, RoundingMode.HALF_UP);
    }
}
