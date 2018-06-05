package models;

import java.time.LocalDate;

public class AsignacionUltimaLista {
    private int codAsignacion;
    private String nombreAsignacion;
    private String nombreAsignatura;
    private LocalDate fechaEntrega;

    public AsignacionUltimaLista(int codAsignacion, String nombreAsignacion, String nombreAsignatura, LocalDate fechaEntrega) {
        this.codAsignacion = codAsignacion;
        this.nombreAsignacion = nombreAsignacion;
        this.nombreAsignatura = nombreAsignatura;
        this.fechaEntrega = fechaEntrega;
    }

    public int getCodAsignacion() {
        return codAsignacion;
    }

    public void setCodAsignacion(int codAsignacion) {
        this.codAsignacion = codAsignacion;
    }

    public String getNombreAsignacion() {
        return nombreAsignacion;
    }

    public void setNombreAsignacion(String nombreAsignacion) {
        this.nombreAsignacion = nombreAsignacion;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
