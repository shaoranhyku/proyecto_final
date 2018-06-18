package models;

public class CriterioEvaluacionProfesor {

    private int codigoCriterio;
    private String nombre;
    private int porcentaje;

    public CriterioEvaluacionProfesor(int codigoCriterio, String nombre, int porcentaje) {
        this.codigoCriterio = codigoCriterio;
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

    public int getCodigoCriterio() {
        return codigoCriterio;
    }

    public void setCodigoCriterio(int codigoCriterio) {
        this.codigoCriterio = codigoCriterio;
    }
}