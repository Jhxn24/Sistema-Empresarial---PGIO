package com.utp.pgio.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String codigo;
    @Column(nullable = false, length = 40)
    private String nombre;
    @Column(nullable = false, length = 40)
    private String cargo;
    @Column(nullable = false, length = 40)
    private String departamento;
    @Column(unique = true, nullable = false, length = 50)
    private String email;
    @Column(length = 15)
    private String telefono;
    @Column(length = 20, nullable = false)
    private String estado;
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDateTime fechaIngreso;

    public Empleado() {
    }

    @PrePersist
    protected void guardarFechaIngreso() {
        this.fechaIngreso = LocalDateTime.now();
    }

    public Empleado(Long id, String codigo, String nombre, String cargo, String departamento, String email,
            String telefono, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cargo = cargo;
        this.departamento = departamento;
        this.email = email;
        this.telefono = telefono;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

}
