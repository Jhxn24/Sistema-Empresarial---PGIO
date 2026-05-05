package com.utp.pgio.models;

public class Empleado {
    private Long id;
    private String codigo;
    private String nombre;
    private String cargo;
    private String departamento;
    private String email;
    private String telefono;
    private String estado;
    private String fechaIngreso;

    public Empleado() {
    }

    public Empleado(Long id, String codigo, String nombre, String cargo, String departamento, String email,
            String telefono, String estado, String fechaIngreso) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cargo = cargo;
        this.departamento = departamento;
        this.email = email;
        this.telefono = telefono;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
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

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

}
