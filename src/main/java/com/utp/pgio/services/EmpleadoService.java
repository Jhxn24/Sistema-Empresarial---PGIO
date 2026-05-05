package com.utp.pgio.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.utp.pgio.models.Empleado;

@Service
public class EmpleadoService {
    private List<Empleado> listaEmpleados = new ArrayList<>();
    private Long nextId = 1L;
    private int nextCod = 1;

    public EmpleadoService() {
        cargarEjemplos();
    }

    private String generarCodigo() {
        return String.format("EMP-%04d", nextCod++);
    }

    private void cargarEjemplos() {
        guardar(new Empleado(null, null, "Ana Torres", "Analista de Sistemas",
                "TI", "ana.torres@empresa.com", "987654321", "ACTIVO", "15/01/2024"));

        guardar(new Empleado(null, null, "Luis Mendez", "Desarrollador Senior",
                "TI", "luis.mendez@empresa.com", "987654322", "ACTIVO", "20/03/2024"));

        guardar(new Empleado(null, null, "Carla Ruiz", "Coordinadora de RRHH",
                "RRHH", "carla.ruiz@empresa.com", "987654323", "ACTIVO", "10/06/2023"));

        guardar(new Empleado(null, null, "Pedro Vega", "Contador General",
                "FINANZAS", "pedro.vega@empresa.com", "987654324", "ACTIVO", "05/09/2023"));

        guardar(new Empleado(null, null, "María López", "Asistente Legal",
                "LEGAL", "maria.lopez@empresa.com", "911223344", "INACTIVO", "12/02/2024"));
    }

    public List<Empleado> listarTodos() {
        return listaEmpleados;
    }

    public void guardar(Empleado empleado) {
        empleado.setId(nextId++);
        empleado.setCodigo(generarCodigo());
        listaEmpleados.add(empleado);
    }
}
