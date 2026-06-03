package com.utp.pgio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utp.pgio.models.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

}
