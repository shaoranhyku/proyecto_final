package models;

public class Usuario {

    private String nombreLogin;
    private String clave;
    private String nombre;
    private String apellidos;
    private String nombreGit;

    public Usuario(String nombreLogin, String clave, String nombre, String apellidos, String nombreGit) {
        this.nombreLogin = nombreLogin;
        this.clave = clave;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreGit = nombreGit;
    }

    public String getNombreLogin() {
        return nombreLogin;
    }

    public void setNombreLogin(String nombreLogin) {
        this.nombreLogin = nombreLogin;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreGit() {
        return nombreGit;
    }

    public void setNombreGit(String nombreGit) {
        this.nombreGit = nombreGit;
    }
}
