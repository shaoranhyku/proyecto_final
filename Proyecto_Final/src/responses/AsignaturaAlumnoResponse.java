package responses;

import models.AsignacionAlumnoLista;
import models.ItemListTema;

import java.util.List;

public class AsignaturaAlumnoResponse {
    private String codigoAsignatura;
    private String nombreAsignatura;
    private List<ItemListTema> temas;
    private List<AsignacionAlumnoLista> asignaciones;

    public AsignaturaAlumnoResponse(String codigoAsignatura, String nombreAsignatura, List<ItemListTema> temas, List<AsignacionAlumnoLista> asignaciones) {
        this.codigoAsignatura = codigoAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.temas = temas;
        this.asignaciones = asignaciones;
    }

    public String getCodigoAsignatura() {
        return codigoAsignatura;
    }

    public void setCodigoAsignatura(String codigoAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public List<ItemListTema> getTemas() {
        return temas;
    }

    public void setTemas(List<ItemListTema> temas) {
        this.temas = temas;
    }

    public List<AsignacionAlumnoLista> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AsignacionAlumnoLista> asignaciones) {
        this.asignaciones = asignaciones;
    }
}
