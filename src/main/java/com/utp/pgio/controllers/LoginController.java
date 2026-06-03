package com.utp.pgio.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.utp.pgio.models.Usuario;
import com.utp.pgio.repository.UsuarioRepository;
import com.utp.pgio.security.JwtUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping({ "/", "/login" })
    public String login(HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token != null && jwtUtil.validarToken(token)) {
            return "redirect:/inicio";
        }
        return "login";
    }

    @PostMapping("/login")
    public String acceder(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (usuario == null || !passwordEncoder.matches(password, usuario.getPassword())) {
            model.addAttribute("error", "Credenciales incorrectas o usuario no registrado");
            return "login";
        }
        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            model.addAttribute("error", "Usuario sin roles asignados");
            return "login";
        }

        List<String> roles = usuario.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        String token = jwtUtil.generarToken(usuario.getUsername(), roles);
        session.setAttribute("jwt", token);
        session.setAttribute("username", usuario.getUsername());
        session.setAttribute("roles", roles);
        session.setAttribute("empleado_id", usuario.getEmpleado() != null ? usuario.getEmpleado().getId() : null);

        System.out.println("[LOGIN] Usuario: " + usuario.getUsername() + " | Roles: " + roles);

        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (roles.contains("ROLE_SUPERVISOR")) {
            return "redirect:/supervisor/dashboard";
        } else if (roles.contains("ROLE_RESOLUTOR")) {
            return "redirect:/resolutor/dashboard";
        } else if (roles.contains("ROLE_SOLICITANTE")) {
            return "redirect:/solicitudes";
        } else {
            return "redirect:/inicio";
        }
    }
}
