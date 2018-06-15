package controllers.alumno;

import javafx.scene.control.Label;
import models.TemaLista;

public class ListItem_TemaController {
    public Label lbl_nombreTema;

    public void setTema(TemaLista tema) {
        lbl_nombreTema.setText(String.format("%s %s", tema.getClave(), tema.getNombre()));
    }
}
