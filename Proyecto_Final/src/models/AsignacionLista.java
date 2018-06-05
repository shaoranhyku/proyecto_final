package models;

import java.time.LocalDateTime;

public class AsignacionLista {

    private int codigoAsignacion;
    private String nombreAsignacion;
    private String asignatura;
    private String descripcion;
    private LocalDateTime fechaEntrega;
    private boolean entregado;

    public AsignacionLista(int codigoAsignacion, String nombreAsignacion, String asignatura, String descripcion, LocalDateTime fechaEntrega, boolean entregado) {
        this.codigoAsignacion = codigoAsignacion;
        this.nombreAsignacion = nombreAsignacion;
        this.asignatura = asignatura;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.entregado = entregado;
    }

    public String getNombreAsignacion() {
        return nombreAsignacion;
    }

    public void setNombreAsignacion(String nombreAsignacion) {
        this.nombreAsignacion = nombreAsignacion;
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

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public int getCodigoAsignacion() {
        return codigoAsignacion;
    }

    public void setCodigoAsignacion(int codigoAsignacion) {
        this.codigoAsignacion = codigoAsignacion;
    }
}
