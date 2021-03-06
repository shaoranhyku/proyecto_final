package models;

public class EntregaAsignacion {

    private String comentario;
    private String comentarioProfesor;
    private int notaEval;
    private int notaAuto;
    private String rutaArchivo;

    public EntregaAsignacion(String comentario, String comentarioProfesor, int notaEval, int notaAuto, String rutaArchivo) {
        this.comentario = comentario;
        this.comentarioProfesor = comentarioProfesor;
        this.notaEval = notaEval;
        this.notaAuto = notaAuto;
        this.rutaArchivo = rutaArchivo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getNotaEval() {
        return notaEval;
    }

    public void setNotaEval(int notaEval) {
        this.notaEval = notaEval;
    }

    public int getNotaAuto() {
        return notaAuto;
    }

    public void setNotaAuto(int notaAuto) {
        this.notaAuto = notaAuto;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getComentarioProfesor() {
        return comentarioProfesor;
    }

    public void setComentarioProfesor(String comentarioProfesor) {
        this.comentarioProfesor = comentarioProfesor;
    }
}
