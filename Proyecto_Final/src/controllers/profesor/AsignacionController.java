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
import models.AsignacionAlumnoLista;
import models.CriterioEvaluacionProfesor;
import models.ItemListTema;
import models.ProfesorApiService;
import responses.AsignacionProfesorResponse;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AsignacionController {
    public TextField txt_nombreGit;
    public DateTimePicker datetimepck_fecha;
    public TextArea txt_descripcion;
    public ListView lst_criterios;
    public TextField txt_nombre;
    public ProfesorController callback;
    public ListView lst_temas;
    public Button btn_eliminar;

    private int asignacionActual;
    private String asignaturaActual;
    private ObservableList<ItemListTema> observableList_todosTemas;
    private ObservableList<ItemListTema> observableList_temas;
    private ObservableList<CriterioEvaluacionProfesor> observableList_criterios;
    private List<ItemListTema> temasAsignadosOriginales;
    private List<CriterioEvaluacionProfesor> criteriosOriginales;

    public void initialize() {

        // Creo las listas vacias
        observableList_todosTemas = FXCollections.observableArrayList();
        observableList_temas = FXCollections.observableArrayList();
        observableList_criterios = FXCollections.observableArrayList();

        // Asigno las listas
        lst_criterios.setItems(observableList_criterios);
        lst_temas.setItems(observableList_temas);

        lst_criterios.setCellFactory(param -> new CellCriterio(mouseEvent -> {
            // TODO: Añadir ventana de editar si da tiempo
        }));

        lst_temas.setCellFactory(param -> new CellTema(mouseEvent -> {
            // TODO: Añadir ventana de editar si da tiempo
        }));
    }

    public void setAsignacionActual(String asignaturaActual, int asignacionActual) {
        this.asignaturaActual = asignaturaActual;
        this.asignacionActual = asignacionActual;
        btn_eliminar.setDisable(asignacionActual < 0);

        // Obtengo los datos necesarios de la API

        // Busco los temas para el dialogo
        ProfesorApiService.obtenerTemasAsignatura(asignaturaActual).subscribe(temas -> {
            observableList_todosTemas.addAll(temas);
        });

        if(asignacionActual >= 0){
            // Busco los datos de la asignacion, sus temas asignados y sus criterios
            ProfesorApiService.obtenerAsignacion(String.valueOf(asignacionActual)).subscribe(asignacionProfesorResponse -> {
                ponerDatosAsignacion(asignacionProfesorResponse);
            });
        }else{
            // Ponemos datos por defecto en los campos requeridos
            datetimepck_fecha.setValue(LocalDate.now());
        }
    }

    private void ponerDatosAsignacion(AsignacionProfesorResponse asignacionResponse){

        AsignacionAlumnoLista asignacion = asignacionResponse.getAsignacion();

        // Primero, ponemos los valores en los campos
        txt_nombre.setText(asignacion.getNombreAsignacion());
        txt_nombreGit.setText(asignacion.getNombreGit());
        datetimepck_fecha.setDateTimeValue(asignacion.getFechaEntrega());
        txt_descripcion.setText(asignacion.getDescripcion());

        // Luego, guardamos las listas de los criterios y temas actuales y las colocamos en sus zonas.
        temasAsignadosOriginales = asignacionResponse.getTemas();
        criteriosOriginales = asignacionResponse.getCriterios();
        observableList_temas.addAll(temasAsignadosOriginales);
        observableList_criterios.addAll(criteriosOriginales);
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

        int porcentaje;

        try{
            porcentaje = Integer.parseInt(value);
        } catch (NumberFormatException e){
            porcentaje = 0;
        }

        if (porcentaje < 0) {
            porcentaje = 0;
        }

        if (porcentaje > 100){
            porcentaje = 100;
        }

        observableList_criterios.add(new CriterioEvaluacionProfesor(asignacionActual, -1, key, porcentaje));
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

        ChoiceDialog<ItemListTema> dialog = new ChoiceDialog<>(observableList_todosTemas.get(0), observableList_todosTemas);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Agregar tema");
        dialog.setContentText("Elige un tema:");

        Optional<ItemListTema> result = dialog.showAndWait();

        result.ifPresent(letter -> agregarNuevoTema(letter));
    }

    private void agregarNuevoTema(ItemListTema letter) {
        observableList_temas.add(letter);
    }

    public void btnEliminarTemaClick(ActionEvent actionEvent) {
        if(lst_temas.getSelectionModel().getSelectedItem() != null){
            observableList_temas.remove(lst_temas.getSelectionModel().getSelectedItem());
        }
    }

    public void btnGuardarClick(ActionEvent actionEvent) {
        String nombreAsignacion = txt_nombre.getText();
        String nombreGit = txt_nombreGit.getText();
        LocalDateTime fechaEntregaDate = datetimepck_fecha.getDateTimeValue();
        String fechaEntrega = "";
        String descripcion = txt_descripcion.getText();
        String fechaCreacion = LocalDate.now().toString();
        List<ItemListTema> temasAsignados = observableList_temas;
        List<CriterioEvaluacionProfesor> criterios = observableList_criterios;

        boolean datosCorrectos = true;

        // Verificamos que cada valor es correcto

        // Verificación nombre asignación
        if(nombreAsignacion.isEmpty() || nombreAsignacion.length() > 40){
            datosCorrectos = false;
        }

        // Verificación nombre git
        if(nombreGit.isEmpty() || nombreGit.length() > 45){
            datosCorrectos = false;
        }

        // Verificación descripción
        if(descripcion.isEmpty() || descripcion.length() > 255){
            datosCorrectos = false;
        }

        // Verificación fecha
        if(fechaEntregaDate != null){
            fechaEntrega = fechaEntregaDate.toString();
            if(fechaEntrega.isEmpty()){
                datosCorrectos = false;
            }
        }else{
            datosCorrectos = false;
        }

        // Verificación criterios
        if(criterios.size() > 0){
            int sumaPorcentajes = 0;
            for (CriterioEvaluacionProfesor criterio : criterios) {
                // Verificación nombre criterio
                if(criterio.getNombre().isEmpty() || criterio.getNombre().length() > 40){
                    datosCorrectos = false;
                }

                // No hay que comprobar que el número este entre 0 y 100 porque ya se controla al introducirlo en la lista,
                // solo hay que comprobar que sumen 100
                sumaPorcentajes += criterio.getPorcentaje();
            }
            if(sumaPorcentajes != 100){
                datosCorrectos = false;
            }
        }else{
            datosCorrectos = false;
        }

        // Verificación temas asignados
        if(temasAsignados.size() == 0){
            datosCorrectos = false;
        }

        // Si los datos son correctos, se agrega o se actualiza la asignación
        if (datosCorrectos){
            if(asignacionActual < 0){
                crearAsignacion(nombreAsignacion, nombreGit, fechaEntrega, descripcion, fechaCreacion, temasAsignados, criterios);
            }else{
                modificarAsignacion(nombreAsignacion, nombreGit, fechaEntrega, descripcion);
            }
        }
    }

    private void modificarAsignacion(String nombreAsignacion, String nombreGit, String fechaEntrega, String descripcion) {
        ProfesorApiService.editarAsignacion(String.valueOf(asignacionActual), nombreAsignacion, nombreGit, descripcion, fechaEntrega).subscribe(() -> {
            // Comienza la lógica para saber que temas borro y cuales añado:

            // Obtengo una copia de la lista original de los temas asignados
            List<ItemListTema> temasBorrar = new ArrayList<>(temasAsignadosOriginales);
            // Borro los que siguen en la lista al final de las operaciones
            temasBorrar.removeAll(observableList_temas);
            // Los elementos que quedan ya no están en la lista despues de las operaciones, así que han sido borrados
            for (ItemListTema temaBorrar : temasBorrar) {
                ProfesorApiService.desasignarTemaAsignacion(String.valueOf(asignacionActual), temaBorrar.getCodigoTema()).subscribe();
            }

            // Obtengo una copia de la lista de los temas asignados tras las operaciones
            List<ItemListTema> temasAgregar = new ArrayList<>(observableList_temas);
            // Borro los que ya estaban en la lista de temas
            temasAgregar.removeAll(temasAsignadosOriginales);
            // Los elementos que quedan son elementos nuevos que necesitan ser agregados
            for (ItemListTema temaAgregar : temasAgregar) {
                ProfesorApiService.asignarTemaAsignacion(String.valueOf(asignacionActual), asignaturaActual, temaAgregar.getCodigoTema()).subscribe();
            }

            // Comienza la lógica para saber que criterios borro y cuales añado:

            // Obtengo una copia de la lista original de los criterios asignados
            List<CriterioEvaluacionProfesor> criteriosBorrar = new ArrayList<>(criteriosOriginales);
            // Borro los que siguen en la lista al final de las operaciones
            criteriosBorrar.removeAll(observableList_criterios);
            // Los elementos que quedan ya no están en la lista despues de las operaciones, así que han sido borrados
            for (CriterioEvaluacionProfesor criterioBorrar : criteriosBorrar) {
                ProfesorApiService.borrarCriterioAsignacion(String.valueOf(asignacionActual), String.valueOf(criterioBorrar.getCodCriterio())).subscribe();
            }

            // Obtengo una copia de la lista de los criterios asignados tras las operaciones
            List<CriterioEvaluacionProfesor> criteriosAgregar = new ArrayList<>(observableList_criterios);
            // Borro los que ya estaban en la lista de criterios
            criteriosAgregar.removeAll(criteriosOriginales);
            // Los elementos que quedan son elementos nuevos que necesitan ser agregados
            for (CriterioEvaluacionProfesor criterioAgregar : criteriosAgregar) {
                ProfesorApiService.crearCriterio(String.valueOf(asignacionActual), criterioAgregar.getNombre(), String.valueOf(criterioAgregar.getPorcentaje())).subscribe();
            }

            callback.setCenterAsignacion(asignacionActual);
        });
    }

    private void crearAsignacion(String nombreAsignacion, String nombreGit, String fechaEntrega, String descripcion, String fechaCreacion, List<ItemListTema> temasAsignados, List<CriterioEvaluacionProfesor> criterios) {
        // Creamos la asignacion, sus temas asignados y sus criterios
        ProfesorApiService.crearAsignacion(nombreAsignacion, nombreGit, descripcion, fechaEntrega, fechaCreacion).subscribe(idAsignacion -> {
            // Una vez creado la asignación, creamos sus temas asignados y sus criterios
            for (CriterioEvaluacionProfesor criterio : criterios) {
                ProfesorApiService.crearCriterio(String.valueOf(idAsignacion), criterio.getNombre(), String.valueOf(criterio.getPorcentaje())).subscribe();
            }

            for(ItemListTema tema : temasAsignados){
                ProfesorApiService.asignarTemaAsignacion(String.valueOf(idAsignacion), asignaturaActual, tema.getCodigoTema()).subscribe();
            }

            callback.setCenterAsignacion(idAsignacion);
        });
    }

    public void btnEliminarClick(ActionEvent actionEvent) {
        ProfesorApiService.borrarAsignacion(String.valueOf(asignacionActual)).subscribe(() -> {
            callback.setCenterListaAsignaciones();
        });
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

    public class CellTema extends ListCell<ItemListTema> {
        private Parent itemRoot;
        private Label lbl_nombre;
        private EventHandler<MouseEvent> callback;

        public CellTema(EventHandler<MouseEvent> clickCallback) {
            callback = clickCallback;
        }

        @Override
        protected void updateItem(ItemListTema itemListTema, boolean empty) {
            super.updateItem(itemListTema, empty);
            if (itemListTema == null) {
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
            itemRoot.setUserData(itemListTema);
            itemRoot.setOnMouseEntered(event -> itemRoot.getScene().setCursor(Cursor.HAND));
            itemRoot.setOnMouseExited(event -> itemRoot.getScene().setCursor(Cursor.DEFAULT));
            lbl_nombre.setText(String.format("%s %s", itemListTema.getCodigoTema(), itemListTema.getNombreTema()));
            setGraphic(itemRoot);
        }
    }

}
