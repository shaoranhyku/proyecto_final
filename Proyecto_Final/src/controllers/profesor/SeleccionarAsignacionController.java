package controllers.profesor;

import controllers.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import models.ItemListTema;
import models.ProfesorApiService;

public class SeleccionarAsignacionController {
    public ChoiceBox cmb_temas;
    public ProfesorController callback;
    public VBox vbox_asignaciones;
    private ObservableList<Object> observableList_temas;

    public void initialize() {
        // Creamos la lista de temas para filtrar vacia
        observableList_temas = FXCollections.observableArrayList();
        cmb_temas.setItems(observableList_temas);
    }

    public void buscarAsignaciones(){
        // Se buscan las asignaciones en la base de datos
        ProfesorApiService.obtenerAsignacionesAsignatura(callback.asignaturaSeleccionada).subscribe(asignaciones -> {
            Utils.setAsignacionesInVBox(asignaciones, vbox_asignaciones, callback, getClass());
        });

        // Se buscan los temas en la base de datos
        ProfesorApiService.obtenerTemasAsignatura(callback.asignaturaSeleccionada).subscribe(temas -> {
            observableList_temas.addAll(temas);
            cmb_temas.getSelectionModel().selectFirst();
        });
    }

    public void btnFiltrarClick(ActionEvent actionEvent) {
        String tema;
        tema = ((ItemListTema)cmb_temas.getSelectionModel().getSelectedItem()).getCodigoTema();
        vbox_asignaciones.getChildren().clear();
        ProfesorApiService.obtenerAsignacionesAsignaturaTema(callback.asignaturaSeleccionada, tema).subscribe(asignaciones -> {
            Utils.setAsignacionesInVBox(asignaciones, vbox_asignaciones, callback, getClass());
        });
    }
}
