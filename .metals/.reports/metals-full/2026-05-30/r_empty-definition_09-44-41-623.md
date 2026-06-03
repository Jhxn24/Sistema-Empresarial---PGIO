error id: file:///C:/Users/JEYSSON/Documents/Ciclo%20VII/PGIO/Sistema-Empresarial---PGIO/src/main/java/com/utp/pgio/services/SolicitudService.java:_empty_/SolicitudRepository#
file:///C:/Users/JEYSSON/Documents/Ciclo%20VII/PGIO/Sistema-Empresarial---PGIO/src/main/java/com/utp/pgio/services/SolicitudService.java
empty definition using pc, found symbol in pc: _empty_/SolicitudRepository#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 548
uri: file:///C:/Users/JEYSSON/Documents/Ciclo%20VII/PGIO/Sistema-Empresarial---PGIO/src/main/java/com/utp/pgio/services/SolicitudService.java
text:
```scala
package com.utp.pgio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utp.pgio.models.Auditoria;
import com.utp.pgio.models.Empleado;
import com.utp.pgio.models.Solicitud;
import com.utp.pgio.repository.AuditoriaRepository;
import com.utp.pgio.repository.EmpleadoRepository;
import com.utp.pgio.repository.SolicitudRepository;

@Service
public class SolicitudService {

    private final Solic@@itudRepository solicitudRepository;
    private final EmpleadoRepository empleadoRepository;
    private final AuditoriaRepository auditoriaRepository;

    public SolicitudService(SolicitudRepository solicitudRepository,
            EmpleadoRepository empleadoRepository,
            AuditoriaRepository auditoriaRepository) {
        this.solicitudRepository = solicitudRepository;
        this.empleadoRepository = empleadoRepository;
        this.auditoriaRepository = auditoriaRepository;
    }

    public List<Solicitud> listarTodas() {
        return solicitudRepository.findAll();
    }

    public long contarTodas() {
        return solicitudRepository.count();
    }

    public List<Solicitud> listarRecientes(int cantidad) {
        return solicitudRepository.findAll().stream()
                .sorted((a, b) -> b.getFechaRegistro().compareTo(a.getFechaRegistro()))
                .limit(cantidad)
                .toList();
    }

    public List<Solicitud> listarPorEstado(String estado) {
        try {
            Solicitud.EstadoSolicitud estadoEnum = Solicitud.EstadoSolicitud.valueOf(estado);
            return solicitudRepository.findAll().stream()
                    .filter(s -> s.getEstado() == estadoEnum)
                    .toList();
        } catch (IllegalArgumentException e) {
            return java.util.Collections.emptyList();
        }
    }

    public List<Solicitud> listarPorSolicitante(Empleado solicitante) {
        return solicitudRepository.findAll().stream()
                .filter(s -> s.getSolicitante() != null && s.getSolicitante().getId().equals(solicitante.getId()))
                .toList();
    }

    public List<Solicitud> listarPorResolutor(Empleado resolutor) {
        return solicitudRepository.findAll().stream()
                .filter(s -> s.getResolutor() != null && s.getResolutor().getId().equals(resolutor.getId()))
                .toList();
    }

    public Empleado obtenerEmpleadoPorEmail(String email) {
        return empleadoRepository.findAll().stream()
                .filter(e -> email.equals(e.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void cambiarEstado(Long id, Solicitud.EstadoSolicitud nuevoEstado) {
        Optional<Solicitud> opt = solicitudRepository.findById(id);
        if (opt.isPresent()) {
            Solicitud s = opt.get();
            s.setEstado(nuevoEstado);
            solicitudRepository.save(s);

            Auditoria log = new Auditoria(
                    "CAMBIO_ESTADO",
                    s.getCodigo(),
                    "sistema@pgio.com",
                    "Estado cambiado a: " + nuevoEstado.name());
            auditoriaRepository.save(log);
        }
    }

    @Transactional
    public Solicitud guardar(Solicitud solicitud) {
        long correlativo = solicitudRepository.count() + 1;
        solicitud.setCodigo(String.format("SOL-2026-%04d", correlativo));

        String desc = solicitud.getDescripcion() != null ? solicitud.getDescripcion().toLowerCase() : "";
        if (desc.contains("error") || desc.contains("servidor") || desc.contains("password")
 desc.contains("sistema") || desc.contains("red") || desc.contains("correo")) {
            solicitud.setCategoria("TI");
        } else if (desc.contains("pago") || desc.contains("factura") || desc.contains("contrato")) {
            solicitud.setCategoria("FINANZAS");
        } else {
            solicitud.setCategoria("GENERAL");
        }

        if (solicitud.getCategoria().equals("TI") || desc.contains("urgente") || desc.contains("critico")) {
            solicitud.setPrioridad("ALTA");
        } else if (desc.contains("importante") || solicitud.getCategoria().equals("FINANZAS")) {
            solicitud.setPrioridad("MEDIA");
        } else {
            solicitud.setPrioridad("BAJA");
        }

        Empleado especialistaAsignado = empleadoRepository.findAll().stream()
                .filter(e -> "ACTIVO".equalsIgnoreCase(e.getEstado()))
                .filter(e -> "TI".equals(solicitud.getCategoria()) ? "TI".equals(e.getDepartamento()) : true)
                .findFirst()
                .orElse(null);

        solicitud.setResolutor(especialistaAsignado);

        Solicitud solicitudProcesada = solicitudRepository.save(solicitud);

        String operador = (solicitud.getSolicitante() != null) ? solicitud.getSolicitante().getEmail()
                : "sistema@pgio.com";
        String detalle = "Solicitud clasificada como [" + solicitudProcesada.getCategoria() +
                "] con prioridad [" + solicitudProcesada.getPrioridad() +
                "]. Resolutor asignado: " +
                (especialistaAsignado != null ? especialistaAsignado.getNombre() : "Mesa de Control");

        auditoriaRepository.save(new Auditoria(
                "REGISTRO_SOLICITUD", solicitudProcesada.getCodigo(), operador, detalle));

        return solicitudProcesada;
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/SolicitudRepository#