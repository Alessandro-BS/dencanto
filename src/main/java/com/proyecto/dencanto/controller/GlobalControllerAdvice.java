package com.proyecto.dencanto.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserInfo(Model model, HttpSession session) {
        String usuario = (String) session.getAttribute("usuario");
        String rol = (String) session.getAttribute("rol");

        if (usuario == null) usuario = "Invitado";
        if (rol == null) rol = "INVITADO";

        model.addAttribute("usuario", usuario);
        model.addAttribute("rol", rol);
    }
}
