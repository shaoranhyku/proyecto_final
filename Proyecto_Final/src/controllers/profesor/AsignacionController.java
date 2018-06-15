package controllers.profesor;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tornadofx.control.DateTimePicker;

public class AsignacionController {
    public TextField txt_nombreGit;
    public DateTimePicker datetimepck_fecha;
    public TextArea txt_descripcion;
    public ListView lst_criterios;
    public TextField txt_nombre;
    public ProfesorController callback;
    private String asignacionActual;

    public void btnAñadirCriterioClick(ActionEvent actionEvent) {
    }

    public void btnEliminarCriterioClick(ActionEvent actionEvent) {
    }

    public void btnGuardarClick(ActionEvent actionEvent) {
    }

    public void btnEliminarClick(ActionEvent actionEvent) {
    }

    public void setAsignacionActual(String asignacionActual) {
        this.asignacionActual = asignacionActual;
    }
}
