package controllers.profesor;

import controllers.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import models.ItemListAlumno;
import models.ItemListTema;
import models.ProfesorApiService;

public class SeleccionarAsignacionEvaluarController {
    public ChoiceBox cmb_temas;
    public ChoiceBox cmb_alumnos;
    public VBox vbox_asignaciones;
    public ProfesorController callback;

    private ObservableList<ItemListTema> observableList_temas;
    private ObservableList<ItemListAlumno> observableList_alumnos;

    public void initialize() {
        // Creamos la lista de temas para filtrar vacia
        observableList_temas = FXCollections.observableArrayList();
        cmb_temas.setItems(observableList_temas);
        // Creamos la lista de alumnos para filtrar vacia
        observableList_alumnos = FXCollections.observableArrayList();
        cmb_alumnos.setItems(observableList_alumnos);
    }

    public void btnFiltrarClick(ActionEvent actionEvent) {
        String alumnoSeleccionado = ((ItemListAlumno)cmb_alumnos.getSelectionModel().getSelectedItem()).getId();
        String temaSeleccionado = ((ItemListTema)cmb_temas.getSelectionModel().getSelectedItem()).getCodigoTema();

        ProfesorApiService.obtenerAsignacionesAsignaturaTemaAlumno(callback.asignaturaSeleccionada, temaSeleccionado, alumnoSeleccionado).subscribe(asignaciones -> {
            Utils.setAsignacionesEvaluarInVBox(asignaciones, vbox_asignaciones, callback, getClass());
        });
    }

    public void buscarAlumnosYTemas() {
        ProfesorApiService.obtenerAlumnosPorAsignatura(callback.asignaturaSeleccionada).subscribe( alumnos -> {
            observableList_alumnos.addAll(alumnos);
            cmb_alumnos.getSelectionModel().selectFirst();
        });
        ProfesorApiService.obtenerTemasAsignatura(callback.asignaturaSeleccionada).subscribe(temas -> {
            observableList_temas.addAll(temas);
            cmb_temas.getSelectionModel().selectFirst();
        });
    }
}
