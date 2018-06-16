package controllers.profesor;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import models.CriterioEvaluacionProfesor;
import models.TemaLista;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.util.Optional;

public class AsignacionController {
    public TextField txt_nombreGit;
    public DateTimePicker datetimepck_fecha;
    public TextArea txt_descripcion;
    public ListView lst_criterios;
    public TextField txt_nombre;
    public ProfesorController callback;
    public ListView lst_temas;

    private String asignacionActual;
    private ObservableList<TemaLista> observableList_todosTemas;
    private String asignaturaActual;
    private ObservableList<Object> observableList_criterios;
    private ObservableList<Object> observableList_temas;

    public void initialize() {

        // TODO: Traer los temas de la API

        observableList_todosTemas = FXCollections.observableArrayList(
                new TemaLista(asignaturaActual, "1", "Tema Primero"),
                new TemaLista(asignaturaActual, "1.1", "Subtema Primero"),
                new TemaLista(asignaturaActual, "2", "Tema 2"),
                new TemaLista(asignaturaActual, "3", "Tema 3"),
                new TemaLista(asignaturaActual, "3.1", "Tema 3 sub"),
                new TemaLista(asignaturaActual, "3.2", "Tema 3 sub 2"),
                new TemaLista(asignaturaActual, "4", "Tema Primero"),
                new TemaLista(asignaturaActual, "5", "Tema Primero"),
                new TemaLista(asignaturaActual, "5.1", "Tema Primero"),
                new TemaLista(asignaturaActual, "5.2", "Tema Primero"),
                new TemaLista(asignaturaActual, "5.3", "Tema Primero"),
                new TemaLista(asignaturaActual, "5.2", "Tema Primero"),
                new TemaLista(asignaturaActual, "5.3", "Tema Primero"),
                new TemaLista(asignaturaActual, "5.2", "Tema Primero"),
                new TemaLista(asignaturaActual, "5.3", "Tema Primero")
        );

        observableList_temas = FXCollections.observableArrayList();

        observableList_criterios = FXCollections.observableArrayList();

        lst_criterios.setItems(observableList_criterios);

        lst_temas.setItems(observableList_temas);

        lst_criterios.setCellFactory(param -> new CellCriterio(mouseEvent -> {
            // TODO: Añadir ventana de editar si da tiempo
        }));

        lst_temas.setCellFactory(param -> new CellTema(mouseEvent -> {
            // TODO: Añadir ventana de editar si da tiempo
        }));
    }

    public void btnAgregarCriterioClick(ActionEvent actionEvent) {
        mostrarDialogoNuevoCriterio();
    }

    private void mostrarDialogoNuevoCriterio() {

        // Creo en dialogo
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Nuevo criterio");

        // Indico los botones
        ButtonType dialogBtnAceptar = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
        ButtonType dialogBtnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(dialogBtnAceptar, dialogBtnCancelar);

        // Creo los labels y los input text para los campos
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        TextField dialogTxtUrl = new TextField();
        dialogTxtUrl.setPromptText("Nombre");
        TextField dialogTxtDescripcion = new TextField();
        dialogTxtDescripcion.setPromptText("Porcentaje");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(dialogTxtUrl, 1, 0);
        grid.add(new Label("Porcentaje:"), 0, 1);
        grid.add(dialogTxtDescripcion, 1, 1);

        // Desactivo el boton por defecto
        Node nodeBtnAceptar = dialog.getDialogPane().lookupButton(dialogBtnAceptar);
        nodeBtnAceptar.setDisable(true);

        // Si el campo de url no est vacio, lo activo
        dialogTxtUrl.textProperty().addListener((observable, oldValue, newValue) -> {
            nodeBtnAceptar.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Pongo el foco en el campo de UR
        Platform.runLater(() -> dialogTxtUrl.requestFocus());

        // Convierto el resultado en un par string, string
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == dialogBtnAceptar) {
                return new Pair<>(dialogTxtUrl.getText(), dialogTxtDescripcion.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            crearNuevoCriterio(usernamePassword.getKey(), usernamePassword.getValue());
        });

    }

    private void crearNuevoCriterio(String key, String value) {
        observableList_criterios.add(new CriterioEvaluacionProfesor(-1, key, Integer.parseInt(value)));
    }

    public void btnEliminarCriterioClick(ActionEvent actionEvent) {
        if(lst_criterios.getSelectionModel().getSelectedItem() != null){
            observableList_criterios.remove(lst_criterios.getSelectionModel().getSelectedItem());
        }
    }

    public void btnAgregarTemaClick(ActionEvent actionEvent) {
        mostrarDialogoAgregarTema();
    }

    private void mostrarDialogoAgregarTema() {

        ChoiceDialog<TemaLista> dialog = new ChoiceDialog<>(observableList_todosTemas.get(0), observableList_todosTemas);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Agregar tema");
        dialog.setContentText("Elige un tema:");

        Optional<TemaLista> result = dialog.showAndWait();

        result.ifPresent(letter -> agregarNuevoTema(letter));
    }

    private void agregarNuevoTema(TemaLista letter) {
        observableList_temas.add(letter);
    }

    public void btnEliminarTemaClick(ActionEvent actionEvent) {
        if(lst_temas.getSelectionModel().getSelectedItem() != null){
            observableList_temas.remove(lst_temas.getSelectionModel().getSelectedItem());
        }
    }

    public void btnGuardarClick(ActionEvent actionEvent) {
    }

    public void btnEliminarClick(ActionEvent actionEvent) {
    }

    public void setAsignacionActual(String asignaturaActual, String asignacionActual) {
        this.asignaturaActual = asignaturaActual;
        this.asignacionActual = asignacionActual;
    }

    public class CellCriterio extends ListCell<CriterioEvaluacionProfesor> {
        private Parent itemRoot;
        private Label lbl_nombre;
        private Label lbl_porcentaje;
        private EventHandler<MouseEvent> callback;

        public CellCriterio(EventHandler<MouseEvent> clickCallback) {
            callback = clickCallback;
        }

        @Override
        protected void updateItem(CriterioEvaluacionProfesor criterioEvaluacionProfesor, boolean empty) {
            super.updateItem(criterioEvaluacionProfesor, empty);
            if (criterioEvaluacionProfesor == null) {
                setText(null);
                setGraphic(null);
                return;
            }
            if (null == itemRoot) {
                try {
                    itemRoot = FXMLLoader.load(getClass().getResource(("/fxml/item/listItem_criterioProfesorCrear.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lbl_nombre = (Label) itemRoot.lookup("#lbl_nombreCriterio");
                lbl_porcentaje = (Label) itemRoot.lookup("#lbl_porcentajeCriterio");
                itemRoot.setOnMouseClicked(callback);
            }
            itemRoot.setUserData(criterioEvaluacionProfesor);
            itemRoot.setOnMouseEntered(event -> itemRoot.getScene().setCursor(Cursor.HAND));
            itemRoot.setOnMouseExited(event -> itemRoot.getScene().setCursor(Cursor.DEFAULT));
            lbl_nombre.setText(criterioEvaluacionProfesor.getNombre());
            lbl_porcentaje.setText(String.format("%d%%", criterioEvaluacionProfesor.getPorcentaje()));
            setGraphic(itemRoot);
        }
    }

    public class CellTema extends ListCell<TemaLista> {
        private Parent itemRoot;
        private Label lbl_nombre;
        private EventHandler<MouseEvent> callback;

        public CellTema(EventHandler<MouseEvent> clickCallback) {
            callback = clickCallback;
        }

        @Override
        protected void updateItem(TemaLista temaLista, boolean empty) {
            super.updateItem(temaLista, empty);
            if (temaLista == null) {
                setText(null);
                setGraphic(null);
                return;
            }
            if (null == itemRoot) {
                try {
                    itemRoot = FXMLLoader.load(getClass().getResource(("/fxml/item/listItem_temaProfesor.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lbl_nombre = (Label) itemRoot.lookup("#lbl_nombreTema");
                itemRoot.setOnMouseClicked(callback);
            }
            itemRoot.setUserData(temaLista);
            itemRoot.setOnMouseEntered(event -> itemRoot.getScene().setCursor(Cursor.HAND));
            itemRoot.setOnMouseExited(event -> itemRoot.getScene().setCursor(Cursor.DEFAULT));
            lbl_nombre.setText(String.format("%s %s", temaLista.getClave(), temaLista.getNombre()));
            setGraphic(itemRoot);
        }
    }

}
