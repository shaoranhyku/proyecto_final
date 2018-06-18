package responses;

import models.AsignacionLista;
import models.CriterioEvaluacionAlumno;
import models.EntregaAsignacion;

import java.util.List;

public class AsignacionAlumnoResponse {

    private AsignacionLista asignacion;
    private EntregaAsignacion entrega;
    private List<CriterioEvaluacionAlumno> criteriosNota;

    public AsignacionAlumnoResponse(AsignacionLista asignacion, EntregaAsignacion entrega, List<CriterioEvaluacionAlumno> criteriosNota) {
        this.asignacion = asignacion;
        this.entrega = entrega;
        this.criteriosNota = criteriosNota;
    }

    public AsignacionLista getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AsignacionLista asignacion) {
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
