package models;

public class CriterioEvaluacionProfesor {

    private int codAsig;
    private int codCriterio;
    private String nombre;
    private int porcentaje;

    public CriterioEvaluacionProfesor(int codAsig, int codCriterio, String nombre, int porcentaje) {
        this.codAsig = codAsig;
        this.codCriterio = codCriterio;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getCodCriterio() {
        return codCriterio;
    }

    public void setCodCriterio(int codCriterio) {
        this.codCriterio = codCriterio;
    }

    public int getCodAsig() {
        return codAsig;
    }

    public void setCodAsig(int codAsig) {
        this.codAsig = codAsig;
    }
}
