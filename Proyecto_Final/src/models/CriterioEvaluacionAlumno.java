package models;

public class CriterioEvaluacionAlumno {

    private String nombreCriterio;
    private float notaAuto;
    private float notaEvaluacion;

    public CriterioEvaluacionAlumno(String nombreCriterio, float notaAuto, float notaEvaluacion) {
        this.nombreCriterio = nombreCriterio;
        this.notaAuto = notaAuto;
        this.notaEvaluacion = notaEvaluacion;
    }

    public String getNombreCriterio() {
        return nombreCriterio;
    }

    public void setNombreCriterio(String nombreCriterio) {
        this.nombreCriterio = nombreCriterio;
    }

    public float getNotaAuto() {
        return notaAuto;
    }

    public void setNotaAuto(float notaAuto) {
        this.notaAuto = notaAuto;
    }

    public float getNotaEvaluacion() {
        return notaEvaluacion;
    }

    public void setNotaEvaluacion(float notaEvaluacion) {
        this.notaEvaluacion = notaEvaluacion;
    }
}
