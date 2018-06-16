package controllers.profesor;

import javafx.application.Application;
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
import javafx.stage.Stage;
import javafx.util.Pair;
import models.ItemListEnlace;
import models.TemaLista;

import java.io.IOException;
import java.util.Optional;

public class TemaController extends Application{
    public ChoiceBox cmb_temasPadre;
    public TextField txt_numeroTema;
    public TextField txt_nombre;
    public DatePicker datepck_fecha;
    public TextArea txt_descripcion;
    public ListView lst_enlaces;
    public ProfesorController callback;
    public Button btn_Eliminar;
    private String temaActual;
    private String asignaturaActual;
    private ObservableList<TemaLista> observableList_temas;
    private ObservableList<ItemListEnlace> observableList_enlaces;

    public void initialize() {

        // TODO: Recibir asignaturas de la api

        observableList_temas = FXCollections.observableArrayList(
                new TemaLista(asignaturaActual, "", "- Sin padre -"),
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

        observableList_enlaces = FXCollections.observableArrayList();

        cmb_temasPadre.setItems(observableList_temas);

        cmb_temasPadre.getSelectionModel().selectFirst();

        lst_enlaces.setItems(observableList_enlaces);

        lst_enlaces.setCellFactory(param -> new CellEnlace(mouseEvent -> {
            // TODO: Añadir ventana de editar si da tiempo
        }));
    }

    public void btnAgregarEnlaceClick(ActionEvent actionEvent) {
        mostrarDialogoNuevoEnlace();
    }

    public void btnEliminarEnlaceClick(ActionEvent actionEvent) {
        if(lst_enlaces.getSelectionModel().getSelectedItem() != null){
            observableList_enlaces.remove(lst_enlaces.getSelectionModel().getSelectedItem());
        }
    }

    public void btnGuardarClick(ActionEvent actionEvent) {
    }

    public void btnEliminarClick(ActionEvent actionEvent) {
    }

    public void setTemaActual(String asignaturaActual, String temaActual) {
        this.asignaturaActual = asignaturaActual;
        this.temaActual = temaActual;
        btn_Eliminar.setDisable(temaActual.isEmpty());
        if (!temaActual.isEmpty()) {
            // TODO: Rellenar datos con lo recogido de la web api
        }
    }

    private void mostrarDialogoNuevoEnlace() {
        // Creo en dialogo
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Nuevo enlace");

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
        dialogTxtUrl.setPromptText("URL");
        TextField dialogTxtDescripcion = new TextField();
        dialogTxtDescripcion.setPromptText("Descripción");

        grid.add(new Label("URL:"), 0, 0);
        grid.add(dialogTxtUrl, 1, 0);
        grid.add(new Label("Descripcion:"), 0, 1);
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
            crearNuevoEnlace(usernamePassword.getKey(), usernamePassword.getValue());
        });
    }

    private void crearNuevoEnlace(String key, String value) {
        observableList_enlaces.add(new ItemListEnlace("", key, value));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public class CellEnlace extends ListCell<ItemListEnlace> {
        private Parent itemRoot;
        private Hyperlink lbl_url;
        private Label lbl_descripcion;
        private EventHandler<MouseEvent> callback;

        public CellEnlace(EventHandler<MouseEvent> clickCallback) {
            callback = clickCallback;
        }

        @Override
        protected void updateItem(ItemListEnlace itemListEnlace, boolean empty) {
            super.updateItem(itemListEnlace, empty);
            if (itemListEnlace == null) {
                setText(null);
                setGraphic(null);
                return;
            }
            if (null == itemRoot) {
                try {
                    itemRoot = FXMLLoader.load(getClass().getResource(("/fxml/item/listItem_enlace.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lbl_url = (Hyperlink) itemRoot.lookup("#lbl_url");
                lbl_descripcion = (Label) itemRoot.lookup("#lbl_descripcion");
                itemRoot.setOnMouseClicked(callback);
            }
            itemRoot.setUserData(itemListEnlace);
            itemRoot.setOnMouseEntered(event -> itemRoot.getScene().setCursor(Cursor.HAND));
            itemRoot.setOnMouseExited(event -> itemRoot.getScene().setCursor(Cursor.DEFAULT));
            lbl_url.setText(itemListEnlace.getUrl());
            lbl_descripcion.setText(itemListEnlace.getDescripcion());
            lbl_url.setOnMouseClicked(event -> getHostServices().showDocument(lbl_url.getText()));
            setGraphic(itemRoot);
        }
    }
}
