package controllers.profesor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ProfesorController {

    public BorderPane rootPane;
    public Label lbl_bienvenidaUsuario;
    public Button btn_salir;
    public Button btn_elegirAsignatura;
    public Button btn_elegirAsignatura1;

    public void initialize() {
        setCenterElegirAsignaturas(null);
    }

    public void cerrarSesion(ActionEvent actionEvent) {

    }

    public void setCenterElegirAsignaturas(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_alumnado.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ElegirAsignaturaController controller = loader.getController();
        rootPane.setCenter(center);
    }

    public void setCenterOperaciones(ActionEvent actionEvent) {

    }
}
