package responses;

import models.Usuario;

public class LoginResponse {
    private String tipo;
    private Usuario usuario;

    public LoginResponse(String tipo, Usuario usuario) {
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
