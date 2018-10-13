package controllers.alumno;

import controllers.Utils;
import javafx.scene.Cursor;
import models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrincipalController implements CallbackTema, CallbackAsignacion{

    @FXML
    private Label lbl_bienvenidaUsuario;
    @FXML
    private ListView listView_asignaturas;
    @FXML
    private ListView listView_ultimasAsignaciones;
    @FXML
    private BorderPane rootPane;
    private ObservableList<ItemListAsignatura> observableList_ItemList_asignaturas;
    private ObservableList<AsignacionAlumnoLista> observableList_ItemList_asignaciones;

    public void initialize() {
        lbl_bienvenidaUsuario.setText(String.format("Bienvenido/a %s %s", Sesion.getInstance().getUsuario().getNombre(), Sesion.getInstance().getUsuario().getApellidos()));

        observableList_ItemList_asignaturas = FXCollections.observableArrayList();

        AlumnoApiService.obtenerAsignaturasAlumno(Sesion.getInstance().getUsuario().getNombreLogin()).subscribe(asignaturas -> observableList_ItemList_asignaturas.addAll(asignaturas));

        observableList_ItemList_asignaciones = FXCollections.observableArrayList();

        AlumnoApiService.obtenerUltimasAsignacionesAlumno(Sesion.getInstance().getUsuario().getNombreLogin()).subscribe(asignaciones -> observableList_ItemList_asignaciones.addAll(asignaciones));

        listView_asignaturas.setItems(observableList_ItemList_asignaturas);

        listView_asignaturas.setCellFactory(param -> new Utils.CellAsignatura(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                setCenterAsignatura(((Parent) mouseEvent.getSource()).getUserData().toString());
            }
        }));

        listView_asignaturas.setOnMousePressed(event -> listView_asignaturas.getSelectionModel().clearSelection());

        listView_ultimasAsignaciones.setItems(observableList_ItemList_asignaciones);

        listView_ultimasAsignaciones.setCellFactory(param -> new CellUltimaAsignacion(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                setCenterAsignacion(Integer.parseInt(((Parent) mouseEvent.getSource()).getUserData().toString()));
            }
        }));

        listView_ultimasAsignaciones.setOnMousePressed(event -> listView_ultimasAsignaciones.getSelectionModel().clearSelection());

        setCenterCalendario();
    }

    public void setCenterCalendario() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/alumno/node_calendario.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CalendarioController controller = loader.getController();
        controller.callBack = this;
        rootPane.setCenter(center);
    }

    public void setCenterCalendario(ActionEvent actionEvent) {
        setCenterCalendario();
    }

    public void setCenterDia(LocalDate fechaDia) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/alumno/node_dia.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiaController controller = loader.getController();

        controller.callBack = this;
        controller.setDiaActual(fechaDia);
        rootPane.setCenter(center);
    }

    public void setCenterAsignatura(String codAsignatura) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/alumno/node_asignatura.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AsignaturaController controller = loader.getController();
        controller.callBack = this;
        controller.setAsignatura(codAsignatura);
        rootPane.setCenter(center);
    }

    public void setCenterAsignacion(int codigoAsignacion) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/alumno/node_asignacion.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AsignacionController controller = loader.getController();
        controller.callBack = this;
        rootPane.setCenter(center);
        controller.setAsignacion(codigoAsignacion);
    }

    public void setCenterTema(String asignaturaClave, String clave) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/alumno/node_temario.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TemarioController controller = loader.getController();
        controller.callBack = this;
        rootPane.setCenter(center);
        controller.setTema(asignaturaClave, clave);
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



    private class CellUltimaAsignacion extends ListCell<AsignacionAlumnoLista> {
        private Parent itemRoot;
        private Label lbl_fechaEntrega;
        private EventHandler<MouseEvent> callback;
        private Label lbl_nombreAsignatura;
        private Label lbl_nombreAsignacion;
        private ListItem_UltimaAsignacionController controller;

        public CellUltimaAsignacion(EventHandler<MouseEvent> clickCallback) {
            callback = clickCallback;
        }

        @Override
        protected void updateItem(AsignacionAlumnoLista asignacionUltimaLista, boolean empty) {
            super.updateItem(asignacionUltimaLista, empty);
            if (asignacionUltimaLista == null) {
                setText(null);
                setGraphic(null);
                return;
            }
            if (null == itemRoot) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(("/fxml/item/listItem_ultimaAsignacion.fxml")));
                    itemRoot = loader.load();
                    controller = loader.getController();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lbl_fechaEntrega = (Label) itemRoot.lookup("#lbl_fechaEntrega");
                lbl_nombreAsignatura = (Label) itemRoot.lookup("#lbl_nombreAsignatura");
                lbl_nombreAsignacion = (Label) itemRoot.lookup("#lbl_nombreAsignacion");
                itemRoot.setOnMouseClicked(callback);
            }
            itemRoot.setUserData(asignacionUltimaLista.getCodigoAsignacion());
            itemRoot.setOnMouseEntered(event -> itemRoot.getScene().setCursor(Cursor.HAND));
            itemRoot.setOnMouseExited(event -> itemRoot.getScene().setCursor(Cursor.DEFAULT));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            lbl_fechaEntrega.setText(asignacionUltimaLista.getFechaEntrega().format(formatter));
            lbl_nombreAsignatura.setText(asignacionUltimaLista.getAsignatura());
            lbl_nombreAsignacion.setText(asignacionUltimaLista.getNombreAsignacion());
            controller.recalculateSize();
            setGraphic(itemRoot);
        }
    }
}
