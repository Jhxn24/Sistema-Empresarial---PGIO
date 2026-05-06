package com.utp.pgio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utp.pgio.models.Solicitud;
import com.utp.pgio.services.SolicitudService;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping("/nueva")
    public String formularioSolicitud(Model model) {
        model.addAttribute("titulo", "Nueva Solicitud");
        model.addAttribute("solicitud", new Solicitud());
        return "formulario";
    }

    @PostMapping("/guardar")
    public String guardarSolicitud(@ModelAttribute("solicitud") Solicitud solicitud) {
        solicitudService.guardarSolicitud(solicitud);
        return "redirect:/inicio";
    }

}
