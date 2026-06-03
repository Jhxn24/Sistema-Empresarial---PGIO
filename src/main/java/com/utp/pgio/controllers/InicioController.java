package com.utp.pgio.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.utp.pgio.services.SolicitudService;

@Controller
public class InicioController {

    private final SolicitudService solicitudService;

    public InicioController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping("/inicio")
    public String inicio(Model model, Authentication auth) {
        model.addAttribute("titulo", "Inicio");
        model.addAttribute("solicitudes", solicitudService.listarTodas());
        if (auth != null) {
            model.addAttribute("username", auth.getName());
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isSupervisor = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_SUPERVISOR"));
            boolean isResolutor = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_RESOLUTOR"));
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isSupervisor", isSupervisor);
            model.addAttribute("isResolutor", isResolutor);
        }
        return "inicio";
    }
}
