package controllers;

import javafx.scene.control.Button;
import models.ItemListAsignacion;
import models.ItemListAsignatura;
import models.Sesion;
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

public class PrincipalController {

    @FXML
    private Label lbl_bienvenidaUsuario;
    @FXML
    private ListView listView_asignaturas;
    @FXML
    private ListView listView_ultimasAsignaciones;
    @FXML
    private BorderPane rootPane;
    private ObservableList<ItemListAsignatura> observableList_ItemList_asignaturas;
    private ObservableList<ItemListAsignacion> observableList_ItemList_asignaciones;

    public void initialize() {
        lbl_bienvenidaUsuario.setText(String.format("Bienvenido/a %s %s", Sesion.getInstance().getUsuario().getNombre(), Sesion.getInstance().getUsuario().getApellidos()));

        observableList_ItemList_asignaturas = FXCollections.observableArrayList(
                new ItemListAsignatura("ItemListAsignatura 1", "ASG1"),
                new ItemListAsignatura("ItemListAsignatura 2", "ASG2"),
                new ItemListAsignatura("ItemListAsignatura 3", "ASG3"),
                new ItemListAsignatura("ItemListAsignatura 4", "ASG4"));

        observableList_ItemList_asignaciones = FXCollections.observableArrayList(
                new ItemListAsignacion(1111, "Examen noseque 1", "99999999999999999999999999999999999999999999999999999999999999", LocalDate.now()),
                new ItemListAsignacion(1111, "Examen Final Programación", "Asginatura 187654321345687", LocalDate.of(2018, 2, 3)),
                new ItemListAsignacion(1111, "EXPOSICIÓN FINAL PROYECTO EMPRESARIAL", "Asginatura 2", LocalDate.of(2018, 7, 10)),
                new ItemListAsignacion(1111, "1234512345123451234512345123451234512345", "Asginatura 2", LocalDate.of(2018, 9, 23)),
                new ItemListAsignacion(1111, "Examen noseque 1", "99999999999999999999999999999999999999999999999999999999999999", LocalDate.now()),
                new ItemListAsignacion(1111, "Examen Final Programación", "Asginatura 187654321345687", LocalDate.of(2018, 2, 3)),
                new ItemListAsignacion(1111, "EXPOSICIÓN FINAL PROYECTO EMPRESARIAL", "Asginatura 2", LocalDate.of(2018, 7, 10)),
                new ItemListAsignacion(1111, "1234512345123451234512345123451234512345", "Asginatura 2", LocalDate.of(2018, 9, 23)),
                new ItemListAsignacion(1111, "Examen noseque 1", "99999999999999999999999999999999999999999999999999999999999999", LocalDate.now()),
                new ItemListAsignacion(1111, "Examen Final Programación", "Asginatura 187654321345687", LocalDate.of(2018, 2, 3)),
                new ItemListAsignacion(1111, "EXPOSICIÓN FINAL PROYECTO EMPRESARIAL", "Asginatura 2", LocalDate.of(2018, 7, 10)),
                new ItemListAsignacion(1111, "1234512345123451234512345123451234512345", "Asginatura 2", LocalDate.of(2018, 9, 23)),
                new ItemListAsignacion(1111, "Examen noseque 1", "99999999999999999999999999999999999999999999999999999999999999", LocalDate.now()),
                new ItemListAsignacion(1111, "Examen Final Programación", "Asginatura 187654321345687", LocalDate.of(2018, 2, 3)),
                new ItemListAsignacion(1111, "EXPOSICIÓN FINAL PROYECTO EMPRESARIAL", "Asginatura 2", LocalDate.of(2018, 7, 10)),
                new ItemListAsignacion(1111, "1234512345123451234512345123451234512345", "Asginatura 2", LocalDate.of(2018, 9, 23))
        );

        listView_asignaturas.setItems(observableList_ItemList_asignaturas);

        listView_asignaturas.setCellFactory(param -> new CellAsignatura(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    System.out.print(((Parent) mouseEvent.getSource()).getUserData().toString());
                }
            }
        }));

        listView_ultimasAsignaciones.setItems(observableList_ItemList_asignaciones);

        listView_ultimasAsignaciones.setCellFactory(param -> new CellUltimaAsignacion(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    System.out.print(((Parent) mouseEvent.getSource()).getUserData().toString());
                }
            }
        }));

        setCenterCalendario();
    }

    public void setCenterCalendario() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/node_calendario.fxml"));

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/node_dia.fxml"));

        Parent center = null;

        try {
            center = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiaController controller = loader.getController();
        controller.setDiaActual(fechaDia);
        rootPane.setCenter(center);
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
    }

    private class CellAsignatura extends ListCell<ItemListAsignatura> {
        private Parent itemRoot;
        private Label lbl_nombre;
        private EventHandler<MouseEvent> callback;

        public CellAsignatura(EventHandler<MouseEvent> clickCallback) {
            callback = clickCallback;
        }

        @Override
        protected void updateItem(ItemListAsignatura itemListAsignatura, boolean empty) {
            super.updateItem(itemListAsignatura, empty);
            if (itemListAsignatura == null) {
                setText(null);
                setGraphic(null);
                return;
            }
            if (null == itemRoot) {
                try {
                    itemRoot = FXMLLoader.load(getClass().getResource(("/fxml/item/listItem_asignatura.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lbl_nombre = (Label) itemRoot.lookup("#lbl_nombre");
                itemRoot.setOnMouseClicked(callback);
            }
            itemRoot.setUserData(itemListAsignatura);
            lbl_nombre.setText(itemListAsignatura.getNombreAsignatura());
            setGraphic(itemRoot);
        }
    }

    private class CellUltimaAsignacion extends ListCell<ItemListAsignacion> {
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
        protected void updateItem(ItemListAsignacion itemListAsignacion, boolean empty) {
            super.updateItem(itemListAsignacion, empty);
            if (itemListAsignacion == null) {
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
            itemRoot.setUserData(itemListAsignacion);
            lbl_fechaEntrega.setText(itemListAsignacion.getFechaEntrega().toString());
            lbl_nombreAsignatura.setText(itemListAsignacion.getNombreAsignatura());
            lbl_nombreAsignacion.setText(itemListAsignacion.getNombreAsignacion());
            controller.recalculateSize();
            setGraphic(itemRoot);
        }
    }
}
