package com.utp.pgio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utp.pgio.models.Empleado;
import com.utp.pgio.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    public List<Empleado> listarSinUsuario() {
        return empleadoRepository.findActivosSinUsuario();
    }

    public long contarTodos() {
        return empleadoRepository.count();
    }

    public Optional<Empleado> buscarPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    @Transactional
    public Empleado guardar(Empleado empleado) {
        if (empleado.getId() != null) {
            return empleadoRepository.findById(empleado.getId())
                    .map(existente -> {
                        existente.setNombre(empleado.getNombre());
                        existente.setCargo(empleado.getCargo());
                        existente.setDepartamento(empleado.getDepartamento());
                        existente.setEmail(empleado.getEmail());
                        existente.setTelefono(empleado.getTelefono());
                        existente.setEstado(empleado.getEstado());
                        return empleadoRepository.save(existente);
                    })
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + empleado.getId()));
        }

        if (empleado.getCodigo() == null || empleado.getCodigo().isBlank()) {
            long total = empleadoRepository.count() + 1;
            empleado.setCodigo(String.format("EMP-%04d", total));
        }
        if (empleado.getEstado() == null || empleado.getEstado().isBlank()) {
            empleado.setEstado("ACTIVO");
        }
        return empleadoRepository.save(empleado);
    }

    @Transactional
    public void desactivar(Long id) {
        empleadoRepository.findById(id).ifPresent(emp -> {
            emp.setEstado("INACTIVO");
            empleadoRepository.save(emp);
        });
    }
}
