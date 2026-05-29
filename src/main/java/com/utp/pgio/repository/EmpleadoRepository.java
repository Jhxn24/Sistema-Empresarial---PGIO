package com.utp.pgio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.utp.pgio.models.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByEmail(String email);

    @Query("SELECT e FROM Empleado e WHERE e.estado = 'ACTIVO' AND NOT EXISTS (SELECT u FROM Usuario u WHERE u.empleado = e)")
    List<Empleado> findActivosSinUsuario();
}
