package com.utp.pgio.controllers;

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
    public String inicio(Model model) {
        model.addAttribute("titulo", "Inicio");
        model.addAttribute("solicitudes", solicitudService.listarTodas());
        return "inicio";
    }

}
