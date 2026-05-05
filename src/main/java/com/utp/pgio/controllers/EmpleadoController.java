package com.utp.pgio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.utp.pgio.models.Empleado;
import com.utp.pgio.services.EmpleadoService;

@Controller
public class EmpleadoController {

    private EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/empleados")
    public String empleados(Model model) {
        model.addAttribute("titulo", "Empleados");
        model.addAttribute("empleados", empleadoService.listarTodos());
        model.addAttribute("empleado", new Empleado());
        return "empleados";
    }

    @PostMapping("/empleados/guardar")
    public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/empleados";
    }

}
