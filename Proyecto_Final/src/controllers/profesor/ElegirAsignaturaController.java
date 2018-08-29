package controllers.profesor;

import controllers.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import models.ItemListAsignatura;
import models.ProfesorApiService;
import models.Sesion;

public class ElegirAsignaturaController {
    public ListView lst_asignaturas;
    public Button btn_seleccionar;
    public ProfesorController callback;
    private ObservableList<ItemListAsignatura> observableList_ItemList_asignaturas;

    public void initialize() {
        observableList_ItemList_asignaturas = FXCollections.observableArrayList();

        ProfesorApiService.obtenerAsignaturasProfesor(Sesion.getInstance().getUsuario().getNombreLogin()).subscribe(asignaturas -> {
            observableList_ItemList_asignaturas.addAll(asignaturas);

            lst_asignaturas.setItems(observableList_ItemList_asignaturas);
        });

        lst_asignaturas.setCellFactory(param -> new Utils.CellAsignatura(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if(mouseEvent.getClickCount() == 2){
                    seleccionarAsignatura(null);
                }
            }
        }));
    }

    public void seleccionarAsignatura(ActionEvent actionEvent) {
        if(lst_asignaturas.getSelectionModel().getSelectedItem() != null){
            ItemListAsignatura seleccion = (ItemListAsignatura) lst_asignaturas.getSelectionModel().getSelectedItem();
            callback.setAsignaturaSeleccionada(seleccion.getCodigoAsignatura());
            callback.setCenterOperaciones(null);
        }
    }
}
