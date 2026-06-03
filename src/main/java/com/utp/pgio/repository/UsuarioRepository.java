package com.utp.pgio.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.utp.pgio.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

}
