package responses;

import models.AsignacionAlumnoLista;
import models.CriterioEvaluacionAlumno;
import models.EntregaAsignacion;

import java.util.List;

public class AsignacionEvaluarProfesorResponse {

    private String nombreAlumno;
    private AsignacionAlumnoLista asignacion;
    private EntregaAsignacion entrega;
    private List<CriterioEvaluacionAlumno> criteriosNota;

    public AsignacionEvaluarProfesorResponse(String nombreAlumno, AsignacionAlumnoLista asignacion, EntregaAsignacion entrega, List<CriterioEvaluacionAlumno> criteriosNota) {
        this.nombreAlumno = nombreAlumno;
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

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }
}
