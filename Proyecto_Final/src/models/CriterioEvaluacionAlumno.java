package models;

public class CriterioEvaluacionAlumno {

    private CriterioEvaluacionProfesor criterio;
    private int notaEval;
    private int notaAuto;

    public CriterioEvaluacionAlumno(CriterioEvaluacionProfesor criterio, int notaAuto, int notaEval) {
        this.criterio = criterio;
        this.notaAuto = notaAuto;
        this.notaEval = notaEval;
    }

    public CriterioEvaluacionProfesor getCriterio() {
        return criterio;
    }

    public void setCriterio(CriterioEvaluacionProfesor criterio) {
        this.criterio = criterio;
    }

    public int getNotaAuto() {
        return notaAuto;
    }

    public void setNotaAuto(int notaAuto) {
        this.notaAuto = notaAuto;
    }

    public int getNotaEval() {
        return notaEval;
    }

    public void setNotaEval(int notaEval) {
        this.notaEval = notaEval;
    }
}
