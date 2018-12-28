package models;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Sesion {
    private static Sesion ourInstance;
    private Usuario usuario;
    private String token;

    private Sesion(Usuario usuario, String token) {
        this.token = token;
        this.usuario = usuario;
    }

    public static Sesion getInstance() {
        return ourInstance;
    }

    public static Sesion crearSesion(String jwt){

        DecodedJWT decodedJwt = JWT.decode(jwt);
        String nombreLogin = decodedJwt.getClaim("nombreLogin").asString();
        String nombre = decodedJwt.getClaim("nombre").asString();
        String apellidos = decodedJwt.getClaim("apellidos").asString();
        String nombreGit = decodedJwt.getClaim("nombreGit").asString();
        String rol = decodedJwt.getClaim("rol").asString();

        Usuario usuario = new Usuario(nombreLogin, "", nombre, apellidos, nombreGit, rol);

        ourInstance = new Sesion(usuario, jwt);
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

    public String getToken() {
        return token;
    }
}
