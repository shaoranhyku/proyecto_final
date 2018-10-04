package responses;

import models.AsignacionAlumnoLista;
import models.CriterioEvaluacionAlumno;
import models.EntregaAsignacion;

import java.util.List;

public class AsignacionAlumnoResponse {

    private AsignacionAlumnoLista asignacion;
    private EntregaAsignacion entrega;
    private List<CriterioEvaluacionAlumno> criteriosNota;

    public AsignacionAlumnoResponse(AsignacionAlumnoLista asignacion, EntregaAsignacion entrega, List<CriterioEvaluacionAlumno> criteriosNota) {
        this.asignacion = asignacion;
        this.entrega = entrega;
        this.criteriosNota = criteriosNota;
    }

    public AsignacionAlumnoLista getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AsignacionAlumnoLista asignacion) {
        this.asignacion = asignacion;
    }

    public EntregaAsignacion getEntrega() {
        return entrega;
    }

    public void setEntrega(EntregaAsignacion entrega) {
        this.entrega = entrega;
    }

    public List<CriterioEvaluacionAlumno> getCriteriosNota() {
        return criteriosNota;
    }

    public void setCriteriosNota(List<CriterioEvaluacionAlumno> criteriosNota) {
        this.criteriosNota = criteriosNota;
    }
}
