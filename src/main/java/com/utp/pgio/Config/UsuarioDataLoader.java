package com.utp.pgio.Config;

import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.utp.pgio.models.Empleado;
import com.utp.pgio.models.Usuario;
import com.utp.pgio.repository.EmpleadoRepository;
import com.utp.pgio.repository.UsuarioRepository;

@Component
public class UsuarioDataLoader implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDataLoader(EmpleadoRepository empleadoRepository, UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        crearUsuario("admin@pgio.com", "admin123", "Admin Principal", "Administrador", "TI",
                Set.of(Usuario.RolNombre.ROLE_ADMIN));
        crearUsuario("supervisor@pgio.com", "utp2026", "Carlos Supervisor", "Coordinador", "TI",
                Set.of(Usuario.RolNombre.ROLE_SUPERVISOR));
        crearUsuario("resolutor@pgio.com", "utp2026", "Maria Resolutora", "Especialista TI", "TI",
                Set.of(Usuario.RolNombre.ROLE_RESOLUTOR));
        crearUsuario("solicitante@pgio.com", "utp2026", "Juan Solicitante", "Analista", "RRHH",
                Set.of(Usuario.RolNombre.ROLE_SOLICITANTE));
    }

    private void crearUsuario(String email, String password, String nombre, String cargo, String depto,
            Set<Usuario.RolNombre> roles) {
        if (usuarioRepository.findByUsername(email).isEmpty()) {

            Empleado emp = new Empleado();
            emp.setCodigo("EMP-" + email.substring(0, 3).toUpperCase() + System.currentTimeMillis() % 1000);
            emp.setNombre(nombre);
            emp.setCargo(cargo);
            emp.setDepartamento(depto);
            emp.setEmail(email);
            emp.setEstado("ACTIVO");
            Empleado empGuardado = empleadoRepository.save(emp);

            Usuario user = new Usuario();
            user.setUsername(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(roles);
            user.setEmpleado(empGuardado);

            usuarioRepository.save(user);
            System.out.println("Usuario de prueba creado: " + email);
        }
    }
}