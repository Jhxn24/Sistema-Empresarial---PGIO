package com.utp.pgio.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.utp.pgio.models.Empleado;
import com.utp.pgio.models.Usuario;
import com.utp.pgio.services.EmpleadoService;
import com.utp.pgio.services.SolicitudService;
import com.utp.pgio.services.UsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EmpleadoService empleadoService;
    private final SolicitudService solicitudService;
    private final UsuarioService usuarioService;

    public AdminController(EmpleadoService empleadoService, SolicitudService solicitudService,
            UsuarioService usuarioService) {
        this.empleadoService = empleadoService;
        this.solicitudService = solicitudService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("titulo", "Panel Admin");
        model.addAttribute("username", auth.getName());
        model.addAttribute("totalEmpleados", empleadoService.contarTodos());
        model.addAttribute("totalSolicitudes", solicitudService.contarTodas());
        model.addAttribute("solicitudesRecientes", solicitudService.listarRecientes(5));
        return "admin/dashboard";
    }

    @GetMapping("/empleados")
    public String empleados(Model model) {
        model.addAttribute("titulo", "Gestión de Empleados");
        model.addAttribute("empleados", empleadoService.listarTodos());
        model.addAttribute("empleado", new Empleado());
        return "admin/empleados";
    }

    @PostMapping("/empleados/guardar")
    public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/admin/empleados";
    }

    // FIX 1: devuelve Map simple → sin riesgo de referencias circulares
    // JPA/Hibernate
    @GetMapping("/empleados/{id}/datos")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerEmpleado(@PathVariable Long id) {
        return empleadoService.buscarPorId(id)
                .map(emp -> {
                    Map<String, Object> datos = new LinkedHashMap<>();
                    datos.put("id", emp.getId());
                    datos.put("codigo", emp.getCodigo());
                    datos.put("nombre", emp.getNombre());
                    datos.put("cargo", emp.getCargo());
                    datos.put("departamento", emp.getDepartamento());
                    datos.put("email", emp.getEmail());
                    datos.put("telefono", emp.getTelefono() != null ? emp.getTelefono() : "");
                    datos.put("estado", emp.getEstado());
                    return ResponseEntity.ok(datos);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/empleados/{id}/desactivar")
    public String desactivarEmpleado(@PathVariable Long id) {
        empleadoService.desactivar(id);
        return "redirect:/admin/empleados";
    }

    @GetMapping("/solicitudes")
    public String solicitudes(Model model) {
        model.addAttribute("titulo", "Todas las Solicitudes");
        model.addAttribute("solicitudes", solicitudService.listarTodas());
        return "admin/solicitudes";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("titulo", "Gestión de Usuarios");
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("empleadosSinUsuario", empleadoService.listarSinUsuario());
        model.addAttribute("roles", Usuario.RolNombre.values());
        return "admin/usuarios";
    }

    @PostMapping("/usuarios/crear")
    public String crearUsuario(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String rol,
            @RequestParam(required = false) Long empleadoId,
            RedirectAttributes attrs) {
        try {
            usuarioService.crear(username, password, rol, empleadoId);
            attrs.addFlashAttribute("exito", "Usuario \"" + username + "\" creado correctamente.");
        } catch (IllegalArgumentException e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/{id}/editar-rol")
    public String editarRol(@PathVariable Long id,
            @RequestParam String rol,
            RedirectAttributes attrs) {
        try {
            usuarioService.editarRol(id, rol);
            attrs.addFlashAttribute("exito", "Rol actualizado correctamente.");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "Error al actualizar el rol.");
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/{id}/cambiar-password")
    public String cambiarPassword(@PathVariable Long id,
            @RequestParam String nuevaPassword,
            RedirectAttributes attrs) {
        try {
            usuarioService.cambiarPassword(id, nuevaPassword);
            attrs.addFlashAttribute("exito", "Contraseña actualizada correctamente.");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "Error al cambiar la contraseña.");
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/{id}/eliminar")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            usuarioService.eliminar(id);
            attrs.addFlashAttribute("exito", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "No se pudo eliminar el usuario.");
        }
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/{id}/datos")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> datosUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(u -> {
                    Map<String, Object> datos = new LinkedHashMap<>();
                    datos.put("id", u.getId());
                    datos.put("username", u.getUsername());
                    datos.put("rol", u.getRoles().isEmpty() ? "" : u.getRoles().iterator().next().name());
                    datos.put("empleadoId", u.getEmpleado() != null ? u.getEmpleado().getId() : null);
                    datos.put("empleadoNombre", u.getEmpleado() != null ? u.getEmpleado().getNombre() : "Sin empleado");
                    return ResponseEntity.ok(datos);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
