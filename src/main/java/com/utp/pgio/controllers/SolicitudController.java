package com.utp.pgio.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.utp.pgio.models.Empleado;
import com.utp.pgio.models.Solicitud;
import com.utp.pgio.models.Usuario;
import com.utp.pgio.repository.UsuarioRepository;
import com.utp.pgio.services.SolicitudService;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;
    private final UsuarioRepository usuarioRepository;

    public SolicitudController(SolicitudService solicitudService, UsuarioRepository usuarioRepository) {
        this.solicitudService = solicitudService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/nueva")
    public String formularioSolicitud(Model model) {
        model.addAttribute("titulo", "Nueva Solicitud");
        model.addAttribute("solicitud", new Solicitud());
        return "solicitante/formulario";
    }

    @PostMapping("/guardar")
    public String guardarSolicitud(@ModelAttribute("solicitud") Solicitud solicitud,
            Authentication auth) {

        // Asignar el solicitante desde el usuario autenticado
        if (auth != null) {
            Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
            if (usuario != null && usuario.getEmpleado() != null) {
                solicitud.setSolicitante(usuario.getEmpleado());
            } else {
                Empleado emp = solicitudService.obtenerEmpleadoPorEmail(auth.getName());
                if (emp != null) {
                    solicitud.setSolicitante(emp);
                }
            }
        }

        solicitudService.guardar(solicitud);
        return "redirect:/inicio";
    }

    @GetMapping
    public String listarSolicitudes(Model model, Authentication auth) {
        model.addAttribute("titulo", "Mis Solicitudes");
        if (auth != null) {
            Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
            if (usuario != null && usuario.getEmpleado() != null) {
                model.addAttribute("solicitudes",
                        solicitudService.listarPorSolicitante(usuario.getEmpleado()));
            } else {
                model.addAttribute("solicitudes", java.util.Collections.emptyList());
            }
        }
        return "solicitante/mis_solicitudes";
    }
}
