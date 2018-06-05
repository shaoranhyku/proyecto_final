package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AsignacionAlumno {

    private String nombreAsignatura;
    private ArrayList<String> temas;
    private String nombreAsignacion;
    private String descripcion;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaCreacion;
    private String nombreGit;
    private String enlaceGit;
    private String comentario;
    private double notaAuto;
    private double notaEvaluacion;
    private ArrayList<CriterioEvaluacionAlumno> criterios;

    public AsignacionAlumno(String nombreAsignatura, ArrayList<String> temas, String nombreAsignacion, String descripcion, LocalDateTime fechaEntrega, LocalDateTime fechaCreacion, String nombreGit, String enlaceGit, String comentario, double notaAuto, double notaEvaluacion, ArrayList<CriterioEvaluacionAlumno> criterios) {
        this.nombreAsignatura = nombreAsignatura;
        this.temas = temas;
        this.nombreAsignacion = nombreAsignacion;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.fechaCreacion = fechaCreacion;
        this.nombreGit = nombreGit;
        this.enlaceGit = enlaceGit;
        this.comentario = comentario;
        this.notaAuto = notaAuto;
        this.notaEvaluacion = notaEvaluacion;
        this.criterios = criterios;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public ArrayList<String> getTemas() {
        return temas;
    }

    public void setTemas(ArrayList<String> temas) {
        this.temas = temas;
    }

    public String getNombreAsignacion() {
        return nombreAsignacion;
    }

    public void setNombreAsignacion(String nombreAsignacion) {
        this.nombreAsignacion = nombreAsignacion;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreGit() {
        return nombreGit;
    }

    public void setNombreGit(String nombreGit) {
        this.nombreGit = nombreGit;
    }

    public String getEnlaceGit() {
        return enlaceGit;
    }

    public void setEnlaceGit(String enlaceGit) {
        this.enlaceGit = enlaceGit;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getNotaAuto() {
        return notaAuto;
    }

    public void setNotaAuto(double notaAuto) {
        this.notaAuto = notaAuto;
    }

    public double getNotaEvaluacion() {
        return notaEvaluacion;
    }

    public void setNotaEvaluacion(double notaEvaluacion) {
        this.notaEvaluacion = notaEvaluacion;
    }

    public ArrayList<CriterioEvaluacionAlumno> getCriterios() {
        return criterios;
    }

    public void setCriterios(ArrayList<CriterioEvaluacionAlumno> criterios) {
        this.criterios = criterios;
    }
}
