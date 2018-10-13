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
import models.ItemListTema;
import models.ProfesorApiService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private ObservableList<ItemListTema> observableList_temas;
    private ObservableList<ItemListEnlace> observableList_enlaces;
    private List<ItemListEnlace> enlacesOriginales;

    public void initialize() {
        // Creamos las listas vacías
        observableList_temas = FXCollections.observableArrayList();
        observableList_enlaces = FXCollections.observableArrayList();

        // Asignamos las listas a sus respectivos objetos
        cmb_temasPadre.setItems(observableList_temas);
        lst_enlaces.setItems(observableList_enlaces);

        lst_enlaces.setCellFactory(param -> new CellEnlace(mouseEvent -> {
            // TODO: Añadir ventana de editar si da tiempo
        }));
    }

    public void setTemaActual(String asignaturaActual, String temaActual) {
        this.asignaturaActual = asignaturaActual;
        this.temaActual = temaActual;
        btn_Eliminar.setDisable(temaActual.isEmpty());
        if (!temaActual.isEmpty()) {
            ProfesorApiService.obtenerTemasAsignatura(asignaturaActual).subscribe(temas -> {
                observableList_temas.addAll(temas);
                ProfesorApiService.obtenerTema(asignaturaActual, temaActual).subscribe(tema -> {
                    ponerDatosTema(tema);
                });
            });
        }else{
            // Obtenemos los temas de la API y seleccionamos el primero por defecto
            ProfesorApiService.obtenerTemasAsignatura(asignaturaActual).subscribe(temas -> {
                observableList_temas.addAll(temas);
                cmb_temasPadre.getSelectionModel().selectFirst();
            });
            datepck_fecha.setValue(LocalDate.now());
        }
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
        String codigoTemaPadre = ((ItemListTema)cmb_temasPadre.getSelectionModel().getSelectedItem()).getCodigoTema();
        String numeroTemaString = txt_numeroTema.getText();
        int numeroTema = 0;
        String nombreTema = txt_nombre.getText();
        String descripcionTema = txt_descripcion.getText();
        LocalDate fechaTemaDate = datepck_fecha.getValue();
        String fechaTema = null;
        List<ItemListEnlace> enlaces = observableList_enlaces;

        Boolean datosCorrectos = true;

        // Verificamos que cada valor es correcto

        // Verificación tema padre
        if(codigoTemaPadre.isEmpty()){
            datosCorrectos = false;
        }

        // Verificación numero tema
        try{
            numeroTema = Integer.parseInt(numeroTemaString);
        } catch (NumberFormatException e){
            datosCorrectos = false;
        }
        if(numeroTema > 99 || numeroTema < 1){
            datosCorrectos = false;
        }

        // Verificación nombre tema
        if(nombreTema.isEmpty() || nombreTema.length() > 45){
            datosCorrectos = false;
        }

        // Verificación descripción tema
        if(descripcionTema.isEmpty() || descripcionTema.length() > 400){
            datosCorrectos = false;
        }

        // Verificación fecha
        if(fechaTemaDate != null){
            fechaTema = fechaTemaDate.toString();
            if(fechaTema.isEmpty()){
                datosCorrectos = false;
            }
        }else{
            datosCorrectos = false;
        }



        for (ItemListEnlace enlace : enlaces) {
            String url = enlace.getUrl();
            String descripcionEnlace = enlace.getDescripcion();

            // Verificación url
            if(url.isEmpty() || url.length() > 100){
                datosCorrectos = false;
            }

            // Verificación descripcion enlace
            if(descripcionEnlace.isEmpty() || descripcionEnlace.length() > 100){
                datosCorrectos = false;
            }
        }

        if(datosCorrectos){
            // Si los datos son correctos, se comprueba si se va a crear un tema nuevo o actualizar uno existente
            String codNuevoTema = String.format("%s-%s", codigoTemaPadre, numeroTema);

            if(btn_Eliminar.isDisabled()){
                agregarTema(codNuevoTema, nombreTema, descripcionTema, fechaTema, enlaces);
            }else{
                actualizarTema(codNuevoTema, nombreTema, descripcionTema, fechaTema, enlaces);
            }

        }else{
            System.out.println("Datos Incorrectos.");
        }
    }

    private void agregarTema(String codNuevoTema, String nombreTema, String descripcionTema, String fechaTema, List<ItemListEnlace> enlaces){

        ProfesorApiService.crearTema(asignaturaActual, codNuevoTema, nombreTema, descripcionTema, fechaTema).subscribe(() -> {
            // Una vez creado el tema, se crean sus enlaces
            for (ItemListEnlace enlace : enlaces) {
                ProfesorApiService.crearEnlaceTema(asignaturaActual, codNuevoTema, enlace.getUrl(), enlace.getDescripcion()).subscribe();
            }
        });
    }

    private void actualizarTema(String codNuevoTema, String nombreTema, String descripcionTema, String fechaTema, List<ItemListEnlace> enlaces){

        ProfesorApiService.editarTema(asignaturaActual, temaActual, codNuevoTema, nombreTema, descripcionTema, fechaTema).subscribe(() -> {
            // Para saber que enlaces borro y agrego, se crean listas y se borran elementos que ya no están o que son nuevos
            // Obtengo una copia de la lista original de enlaces del tema
            List<ItemListEnlace> enlacesBorrar = new ArrayList<>(enlacesOriginales);
            // Borro los que siguen en la lista al final de las operaciones
            enlacesBorrar.removeAll(observableList_enlaces);
            // Los elementos que quedan ya no están en la lista despues de las operaciones, así que han sido borrados
            for (ItemListEnlace enlaceBorrar : enlacesBorrar) {
                ProfesorApiService.eliminarEnlace(asignaturaActual, codNuevoTema, enlaceBorrar.getCodigoEnlace()).subscribe();
            }

            // Obtengo una copia de la lista de los enlaces tras las operaciones
            List<ItemListEnlace> temasAgregar = new ArrayList<>(observableList_enlaces);
            // Borro los que ya estaban en la lista de enlaces
            temasAgregar.removeAll(enlacesOriginales);
            // Los elementos que quedan son elementos nuevos que necesitan ser agregados
            for (ItemListEnlace enlaceAgregar : temasAgregar) {
                ProfesorApiService.crearEnlaceTema(asignaturaActual, codNuevoTema, enlaceAgregar.getUrl(), enlaceAgregar.getDescripcion()).subscribe();
            }

            // Una vez acabadas las operaciones, redirigimos la misma página para ver los cambios
            callback.setCenterTema(asignaturaActual, codNuevoTema);
        });


    }

    public void btnEliminarClick(ActionEvent actionEvent) {
        ProfesorApiService.eliminarTema(asignaturaActual, temaActual).subscribe(() -> {
            callback.setCenterListaTemas();
        });
    }

    private void ponerDatosTema(ItemListTema tema){
        // Primero sacamos el codigo de tema y el codigo de padre
        // Obtenemos el índice del último guión y separamos el padre del hijo
        int auxUltimoGuion = tema.getCodigoTema().lastIndexOf('-');
        String codTemaPadre = tema.getCodigoTema().substring(0, auxUltimoGuion);
        String codTema = tema.getCodigoTema().substring(auxUltimoGuion+1, tema.getCodigoTema().length());

        // Buscamos el tema padre en la lista y lo seleccionamos
        ItemListTema temaPadre = null;
        for (ItemListTema temaLista: observableList_temas) {
            if(temaLista.getCodigoTema().equals(codTemaPadre)){
                temaPadre = temaLista;
                break;
            }
        }
        cmb_temasPadre.getSelectionModel().select(temaPadre);

        // Asignamos el resto de valores
        txt_numeroTema.setText(codTema);
        txt_nombre.setText(tema.getNombreTema());
        datepck_fecha.setValue(tema.getFechaComienzo());
        txt_descripcion.setText(tema.getDescripcion());
        enlacesOriginales = tema.getEnlaces();
        observableList_enlaces.addAll(tema.getEnlaces());
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
