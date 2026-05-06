package com.utp.pgio.models;

public class Solicitud {
    private Long id;
    private String codigo; // Generado automáticamente (RF01)
    private String titulo;
    private String descripcion; // Detalle del problema
    private String categoria; // Sugerida por IA (RF09)
    private String prioridad; // Calculada por RF02
    private String fechaRegistro;

    public Solicitud() {

    }

    public Solicitud(Long id, String codigo, String titulo, String descripcion, String categoria, String prioridad,
            String fechaRegistro) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.prioridad = prioridad;
        this.fechaRegistro = fechaRegistro;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
