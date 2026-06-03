package com.utp.pgio.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utp.pgio.models.Empleado;
import com.utp.pgio.models.Usuario;
import com.utp.pgio.repository.EmpleadoRepository;
import com.utp.pgio.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
            EmpleadoRepository empleadoRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.empleadoRepository = empleadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public boolean existeUsername(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public Usuario crear(String username, String password, String rolNombre, Long empleadoId) {
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe: " + username);
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRoles(Set.of(Usuario.RolNombre.valueOf(rolNombre)));

        if (empleadoId != null) {
            Empleado emp = empleadoRepository.findById(empleadoId)
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + empleadoId));
            usuario.setEmpleado(emp);
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario editarRol(Long id, String nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        usuario.setRoles(Set.of(Usuario.RolNombre.valueOf(nuevoRol)));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void cambiarPassword(Long id, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public long contarTodos() {
        return usuarioRepository.count();
    }
}
