package controllers.profesor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.CallbackAsignacion;
import models.CallbackAsignacionEvaluar;
import models.CallbackTema;
import models.Sesion;

import java.io.IOException;

public class ProfesorController implements CallbackTema, CallbackAsignacion, CallbackAsignacionEvaluar {

    public BorderPane rootPane;
    public Label lbl_bienvenidaUsuario;
    public Button btn_salir;
    public Button btn_elegirAsignatura;
    public Button btn_elegirOperacion;
    public Label lbl_asignatura;
    public String asignaturaSeleccionada;

    public void initialize() {
        lbl_bienvenidaUsuario.setText(String.format("Bienvenido/a %s %s", Sesion.getInstance().getUsuario().getNombre(), Sesion.getInstance().getUsuario().getApellidos()));
        setCenterElegirAsignaturas(null);
    }

    public void cerrarSesion(ActionEvent actionEvent) {
        Sesion.cerrarSesion();
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = null;/* Exception */
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/ui/scene_login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setMaxWidth(800);
        stage.setMaxHeight(600);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.centerOnScreen();
    }

    public void setCenterElegirAsignaturas(ActionEvent actionEvent) {
        btn_elegirOperacion.setDisable(true);
        lbl_asignatura.setText("");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_elegirProfesorAsignatura.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ElegirAsignaturaController controller = loader.getController();
        controller.callback = this;
        rootPane.setCenter(center);
    }

    public void setCenterOperaciones(ActionEvent actionEvent) {
        btn_elegirOperacion.setDisable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_operaciones.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OperacionesController controller = loader.getController();
        controller.callback = this;
        rootPane.setCenter(center);
    }

    public void setAsignaturaSeleccionada(String claveAsignaturaSeleccionada) {
        this.asignaturaSeleccionada = claveAsignaturaSeleccionada;
        lbl_asignatura.setText(claveAsignaturaSeleccionada);
    }

    public void setCenterAlumnos() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_alumnado.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlumnadoController controller = loader.getController();
        controller.setCallback(this);
        rootPane.setCenter(center);
    }

    public void setCenterCrearTema() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_tema.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TemaController controller = loader.getController();
        controller.callback = this;
        controller.setTemaActual(asignaturaSeleccionada, "");
        rootPane.setCenter(center);
    }

    public void setCenterListaTemas() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_seleccionarTema.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SeleccionarTemaController controller = loader.getController();
        controller.callback = this;
        controller.buscarTemas();
        rootPane.setCenter(center);
    }

    public void setCenterCrearAsignacion() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_asignacion.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AsignacionController controller = loader.getController();
        controller.callback = this;
        controller.setAsignacionActual(asignaturaSeleccionada, -1);
        rootPane.setCenter(center);
    }

    public void setCenterListaAsignaciones() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_seleccionarAsignacion.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SeleccionarAsignacionController controller = loader.getController();
        controller.callback = this;
        controller.buscarAsignaciones();
        rootPane.setCenter(center);
    }

    public void setCenterListaEvaluarAsignaciones() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_seleccionarAsignacionEvaluar.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SeleccionarAsignacionEvaluarController controller = loader.getController();
        controller.callback = this;
        controller.buscarAlumnosYTemas();
        rootPane.setCenter(center);
    }

    @Override
    public void setCenterTema(String asignaturaClave, String clave) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_tema.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TemaController controller = loader.getController();
        controller.callback = this;
        controller.setTemaActual(asignaturaClave, clave);
        rootPane.setCenter(center);
    }

    @Override
    public void setCenterAsignacion(int codigoAsignacion) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/profesor/node_asignacion.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AsignacionController controller = loader.getController();
        controller.callback = this;
        controller.setAsignacionActual(asignaturaSeleccionada, codigoAsignacion);
        rootPane.setCenter(center);
    }

    @Override
    public void setCenterAsignacionEvaluar(int codigoAsignacion) {
        System.out.printf("Seleccionado asignacion evaluar %d%n", codigoAsignacion);
    }
}
