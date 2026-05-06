package com.utp.pgio.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.pgio.models.Solicitud;

@Service
public class SolicitudService {
    private List<Solicitud> listaSolicitudes = new ArrayList<>();
    private Long nextId = 1L;
    private int nextCorrelativo = 1;

    public void guardarSolicitud(Solicitud solicitud) {
        solicitud.setId(nextId++);
        solicitud.setCodigo(String.format("SOL-2026-%04d", nextCorrelativo++));

        // 2. Simulación de IA para Categoría (RF09)
        String desc = solicitud.getDescripcion().toLowerCase();
        if (desc.contains("error") || desc.contains("servidor") || desc.contains("password")) {
            solicitud.setCategoria("TI");
        } else {
            solicitud.setCategoria("GENERAL");
        }
        // 3. Motor de Priorización Simple (RF02)
        // Si es de TI y tiene palabras urgentes, prioridad ALTA
        if (solicitud.getCategoria().equals("TI") || desc.contains("urgente") || desc.contains("ahora")) {
            solicitud.setPrioridad("ALTA");
        } else {
            solicitud.setPrioridad("MEDIA");
        }

        listaSolicitudes.add(solicitud);
        System.out
                .println("Solicitud procesada: " + solicitud.getCodigo() + " | Prioridad: " + solicitud.getPrioridad());
    }

    public List<Solicitud> listarTodas() {
        return listaSolicitudes;
    }

}
