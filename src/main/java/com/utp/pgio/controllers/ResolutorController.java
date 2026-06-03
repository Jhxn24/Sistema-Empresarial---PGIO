package com.utp.pgio.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.utp.pgio.models.Solicitud;
import com.utp.pgio.models.Usuario;
import com.utp.pgio.repository.UsuarioRepository;
import com.utp.pgio.services.SolicitudService;

@Controller
@RequestMapping("/resolutor")
public class ResolutorController {

    private final SolicitudService solicitudService;
    private final UsuarioRepository usuarioRepository;

    public ResolutorController(SolicitudService solicitudService, UsuarioRepository usuarioRepository) {
        this.solicitudService = solicitudService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("titulo", "Panel Resolutor");
        model.addAttribute("username", auth.getName());
        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
        if (usuario != null && usuario.getEmpleado() != null) {
            model.addAttribute("misAsignaciones",
                    solicitudService.listarPorResolutor(usuario.getEmpleado()));
        } else {
            model.addAttribute("misAsignaciones", java.util.Collections.emptyList());
        }
        model.addAttribute("solicitudesAsignadas", solicitudService.listarPorEstado("ASIGNADA"));
        return "resolutor/dashboard";
    }

    @PostMapping("/solicitudes/{id}/resolver")
    public String resolverSolicitud(@PathVariable Long id) {
        solicitudService.cambiarEstado(id, Solicitud.EstadoSolicitud.RESUELTA);
        return "redirect:/resolutor/dashboard";
    }
}
