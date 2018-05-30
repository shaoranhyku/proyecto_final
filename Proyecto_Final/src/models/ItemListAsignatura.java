package models;

public class ItemListAsignatura {
    private String codigoAsignatura;
    private String nombreAsignatura;

    public ItemListAsignatura(String codigoAsignatura, String nombreAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
        this.nombreAsignatura = nombreAsignatura;
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
}
