package models;

import java.time.LocalDateTime;

public class Asignacion {

    private String nombre;
    private String asignatura;
    private String descripcion;
    private LocalDateTime fechaEntrega;

    public Asignacion(String nombre, String asignatura, String descripcion, LocalDateTime fechaEntrega) {
        this.nombre = nombre;
        this.asignatura = asignatura;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
