package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ItemListTema {

    private String codigoAsignatura;
    private String codigoTema;
    private String nombreTema;
    private String descripcion;
    private LocalDate fechaComienzo;
    private List<ItemListEnlace> enlaces;

    public ItemListTema(String codigoAsignatura, String codigoTema, String nombreTema, String descripcion, LocalDate fechaComienzo, List<ItemListEnlace> enlaces) {
        this.codigoAsignatura = codigoAsignatura;
        this.codigoTema = codigoTema;
        this.nombreTema = nombreTema;
        this.descripcion = descripcion;
        this.fechaComienzo = fechaComienzo;
        this.enlaces = enlaces;
    }

    public String getCodigoTema() {
        return codigoTema;
    }

    public void setCodigoTema(String codigoTema) {
        this.codigoTema = codigoTema;
    }

    public String getNombreTema() {
        return nombreTema;
    }

    public void setNombreTema(String nombreTema) {
        this.nombreTema = nombreTema;
    }

    public String getCodigoAsignatura() {
        return codigoAsignatura;
    }

    public void setCodigoAsignatura(String codigoAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
    }

    @Override
    public String toString() {
        return String.format("%s %s", codigoTema, nombreTema);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaComienzo() {
        return fechaComienzo;
    }

    public void setFechaComienzo(LocalDate fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
    }

    public List<ItemListEnlace> getEnlaces() {
        return enlaces;
    }

    public void setEnlaces(List<ItemListEnlace> enlaces) {
        this.enlaces = enlaces;
    }
}
