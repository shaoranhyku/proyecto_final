package models;

public class TemaLista {

    private String asignaturaClave;
    private String clave;
    private String nombre;

    public TemaLista(String asignaturaClave, String clave, String nombre) {
        this.asignaturaClave = asignaturaClave;
        this.clave = clave;
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAsignaturaClave() {
        return asignaturaClave;
    }

    public void setAsignaturaClave(String asignaturaClave) {
        this.asignaturaClave = asignaturaClave;
    }

    @Override
    public String toString() {
        return String.format("%s %s", clave, nombre);
    }
}
