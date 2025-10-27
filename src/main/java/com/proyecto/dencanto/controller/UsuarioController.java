package com.proyecto.dencanto.controller;

import com.proyecto.dencanto.Modelo.Usuario;
import com.proyecto.dencanto.Modelo.Rol;
import com.proyecto.dencanto.Service.UsuarioService; // Con S mayúscula
import com.proyecto.dencanto.Service.RolService; // Con S mayúscula
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/intranet/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    // Mostrar la lista de usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        List<Rol> roles = rolService.obtenerTodos();

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", roles);
        model.addAttribute("usuario", new Usuario()); // Para el formulario de agregar
        return "intranet/usuarios";
    }

    // Agregar un usuario al sistema
    @PostMapping("/agregar")
    public String agregarUsuario(@Valid @ModelAttribute Usuario usuario,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<Usuario> usuarios = usuarioService.obtenerTodos();
            List<Rol> roles = rolService.obtenerTodos();
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("roles", roles);
            return "intranet/usuarios";
        }
        usuarioService.guardar(usuario);
        redirectAttributes.addFlashAttribute("successMessage", "Usuario agregado exitosamente");
        return "redirect:/intranet/usuarios";
    }

    // Mostrar el formulario para editar un usuario
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        List<Rol> roles = rolService.obtenerTodos();

        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", roles);
        model.addAttribute("editing", true);

        return "intranet/usuarios";
    }

    // Editar un usuario
    @PostMapping("/editar")
    public String editarUsuario(@Valid @ModelAttribute Usuario usuario,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<Usuario> usuarios = usuarioService.obtenerTodos();
            List<Rol> roles = rolService.obtenerTodos();
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("roles", roles);
            return "intranet/usuarios";
        }
        usuarioService.guardar(usuario);
        redirectAttributes.addFlashAttribute("successMessage", "Usuario actualizado exitosamente");
        return "redirect:/intranet/usuarios";
    }

    // Eliminar un usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        usuarioService.eliminar(id);
        redirectAttributes.addFlashAttribute("successMessage", "Usuario eliminado exitosamente");
        return "redirect:/intranet/usuarios";
    }

    // Resetear contraseña
    @GetMapping("/reset-password/{id}")
    public String resetearPassword(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        // Aquí puedes implementar la lógica para resetear la contraseña
        redirectAttributes.addFlashAttribute("successMessage", "Contraseña reseteada exitosamente");
        return "redirect:/intranet/usuarios";
    }

    @ModelAttribute("usuario")
    public Usuario getUsuario(@RequestParam(value = "rolId", required = false) Integer rolId) {
        Usuario usuario = new Usuario();
        if (rolId != null) {
            Rol rol = rolService.obtenerPorId(rolId);
            usuario.setRol(rol);
        }
        return usuario;
    }
}