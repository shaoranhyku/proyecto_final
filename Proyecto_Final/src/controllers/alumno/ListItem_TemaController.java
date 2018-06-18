package controllers.alumno;

import javafx.scene.control.Label;
import models.ItemListTema;

public class ListItem_TemaController {
    public Label lbl_nombreTema;

    public void setTema(ItemListTema tema) {
        lbl_nombreTema.setText(String.format("%s %s", tema.getCodigoTema(), tema.getNombreTema()));
    }
}
