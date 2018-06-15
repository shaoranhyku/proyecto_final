package controllers.alumno;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public class TemarioController {

    public Label lbl_nombreTemario;
    public Label lbl_fechaInicio;
    public Label lbl_descripcion;
    public VBox vbox_enlaces;
    public VBox vbox_asiginaciones;
    public VBox vbox_subtemas;

    public PrincipalController callBack;

    public void setTema(String asignaturaClave, String clave) {

        // TODO: Buscar datos en la BD
    }
}
