package models;

public class Sesion {
    private static Sesion ourInstance;
    private Usuario usuario;

    private Sesion(Usuario usuario) {
        this.usuario = usuario;
    }

    public static Sesion getInstance() {
        return ourInstance;
    }

    public static Sesion crearSesion(Usuario usuario){
        ourInstance = new Sesion(usuario);
        return ourInstance;
    }

    public static boolean cerrarSesion(){
        ourInstance = null;
        return true;
    }

    public static boolean comprobarSesion(){
        return ourInstance != null;
    }


    public Usuario getUsuario() {
        return usuario;
    }
}
