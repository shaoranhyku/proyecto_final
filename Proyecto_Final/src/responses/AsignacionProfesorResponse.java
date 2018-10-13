package responses;

import models.*;

import java.util.List;

public class AsignacionProfesorResponse {

    private AsignacionAlumnoLista asignacion;
    private List<ItemListTema> temas;
    private List<CriterioEvaluacionProfesor> criterios;

    public AsignacionProfesorResponse(AsignacionAlumnoLista asignacion, List<ItemListTema> temas, List<CriterioEvaluacionProfesor> criterios) {
        this.asignacion = asignacion;
        this.temas = temas;
        this.criterios = criterios;
    }

    public AsignacionAlumnoLista getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AsignacionAlumnoLista asignacion) {
        this.asignacion = asignacion;
    }

    public List<ItemListTema> getTemas() {
        return temas;
    }

    public void setTemas(List<ItemListTema> temas) {
        this.temas = temas;
    }

    public List<CriterioEvaluacionProfesor> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<CriterioEvaluacionProfesor> criterios) {
        this.criterios = criterios;
    }
}
