package controllers.profesor;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class TemaController {
    public ChoiceBox cmb_temasPadre;
    public TextField txt_numeroTema;
    public TextField txt_nombre;
    public DatePicker datepck_fecha;
    public TextArea txt_descripcion;
    public ListView lst_enlaces;
    public ProfesorController callback;
    private String temaActual;

    public void btnAñadirEnlaceClick(ActionEvent actionEvent) {
    }

    public void btnEliminarEnlaceClick(ActionEvent actionEvent) {
    }

    public void btnGuardarClick(ActionEvent actionEvent) {
    }

    public void btnEliminarClick(ActionEvent actionEvent) {
    }

    public void setTemaActual(String temaActual) {
        this.temaActual = temaActual;
    }
}
