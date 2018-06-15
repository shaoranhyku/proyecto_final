package controllers.profesor;

import javafx.event.ActionEvent;

public class OperacionesController {
    public ProfesorController callback;

    public void btnAlumnosClick(ActionEvent actionEvent) {
        callback.setCenterAlumnos();
    }

    public void btnCrearTemaClick(ActionEvent actionEvent) {
        callback.setCenterCrearTema();
    }

    public void btnModificarTemaClick(ActionEvent actionEvent) {
        callback.setCenterListaTemas();
    }

    public void btnCrearAsignacionClick(ActionEvent actionEvent) {
        callback.setCenterCrearAsignacion();
    }

    public void btnModificarAsignacionClick(ActionEvent actionEvent) {
        callback.setCenterListaAsignaciones();
    }

    public void btnEvaluarAsignacionClick(ActionEvent actionEvent) {
        callback.setCenterListaEvaluarAsignaciones();
    }
}
