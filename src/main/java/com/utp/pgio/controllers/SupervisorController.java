package com.utp.pgio.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.utp.pgio.models.Solicitud;
import com.utp.pgio.services.SolicitudService;

@Controller
@RequestMapping("/supervisor")
public class SupervisorController {

    private final SolicitudService solicitudService;

    public SupervisorController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("titulo", "Panel Supervisor");
        model.addAttribute("username", auth.getName());
        model.addAttribute("solicitudesPendientes", solicitudService.listarPorEstado("CREADA"));
        model.addAttribute("solicitudesEnRevision", solicitudService.listarPorEstado("EN_REVISION"));
        return "supervisor/dashboard";
    }

    @GetMapping("/solicitudes")
    public String solicitudes(Model model) {
        model.addAttribute("titulo", "Gestión de Solicitudes");
        model.addAttribute("solicitudes", solicitudService.listarTodas());
        return "supervisor/solicitudes";
    }

    @PostMapping("/solicitudes/{id}/aprobar")
    public String aprobarSolicitud(@PathVariable Long id) {
        solicitudService.cambiarEstado(id, Solicitud.EstadoSolicitud.ASIGNADA);
        return "redirect:/supervisor/solicitudes";
    }

    @PostMapping("/solicitudes/{id}/rechazar")
    public String rechazarSolicitud(@PathVariable Long id) {
        solicitudService.cambiarEstado(id, Solicitud.EstadoSolicitud.RECHAZADA);
        return "redirect:/supervisor/solicitudes";
    }
}
