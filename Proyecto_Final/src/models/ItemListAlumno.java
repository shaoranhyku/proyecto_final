package models;

public class ItemListAlumno {
    private final String id;
    private final String nombre;
    private String apellidos;

    public ItemListAlumno(String id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return String.format("%s %s", nombre, apellidos);
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;

        if(obj instanceof ItemListAlumno){
            isEqual = id.equals(((ItemListAlumno)obj).id);
        }

        return isEqual;
    }
}
