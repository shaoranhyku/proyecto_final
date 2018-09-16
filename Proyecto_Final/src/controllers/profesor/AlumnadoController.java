package controllers.profesor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import models.ItemListAlumno;
import models.ProfesorApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlumnadoController {
    public ListView lst_tusAlumnos;
    public ListView lst_todosAlumnos;
    public ProfesorController callback;
    private List<ItemListAlumno> alumnosTodosOriginal;
    private List<ItemListAlumno> alumnosAsignaturaTodosOriginal;
    private ObservableList<ItemListAlumno> observableList_ItemList_alumnosTodos;
    private ObservableList<ItemListAlumno> observableList_ItemList_alumnosTuyos;

    public void initialize() {

        // Se inicializan las listas vacías

        observableList_ItemList_alumnosTodos = FXCollections.observableArrayList();

        observableList_ItemList_alumnosTuyos = FXCollections.observableArrayList();

        // Se indica el tipo de elementos que mostrará y sus funciones


        lst_todosAlumnos.setCellFactory(param -> new CellAlumno(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if(mouseEvent.getClickCount() == 2){
                    btnAñadirClick(null);
                }
            }
        }));

        lst_tusAlumnos.setCellFactory(param -> new CellAlumno(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if(mouseEvent.getClickCount() == 2){
                    btnQuitarClick(null);
                }
            }
        }));

        lst_todosAlumnos.setItems(observableList_ItemList_alumnosTodos);
        lst_tusAlumnos.setItems(observableList_ItemList_alumnosTuyos);
    }

    public void setCallback(ProfesorController callback){

        this.callback = callback;

        // Se accede a la API para obtener los alumnos globales y de la asignatura

        ProfesorApiService.obtenerAlumnosPorAsignatura(callback.asignaturaSeleccionada).subscribe(alumnosAsignatura -> {
            alumnosAsignaturaTodosOriginal = alumnosAsignatura;
            observableList_ItemList_alumnosTuyos.addAll(alumnosAsignatura);
            ProfesorApiService.obtenerAlumnos().subscribe(alumnosTodos -> {
                alumnosTodosOriginal = alumnosTodos;
                alumnosTodosOriginal.removeAll(alumnosAsignaturaTodosOriginal);
                observableList_ItemList_alumnosTodos.addAll(alumnosTodos);
            });
        });
    }

    public void btnAñadirClick(ActionEvent actionEvent) {
        if(lst_todosAlumnos.getSelectionModel().getSelectedItem() != null){
            ItemListAlumno item = (ItemListAlumno) lst_todosAlumnos.getSelectionModel().getSelectedItem();
            observableList_ItemList_alumnosTodos.remove(item);
            observableList_ItemList_alumnosTuyos.add(item);
        }
    }

    public void btnQuitarClick(ActionEvent actionEvent) {
        if(lst_tusAlumnos.getSelectionModel().getSelectedItem() != null){
            ItemListAlumno item = (ItemListAlumno) lst_tusAlumnos.getSelectionModel().getSelectedItem();
            observableList_ItemList_alumnosTuyos.remove(item);
            observableList_ItemList_alumnosTodos.add(item);
        }
    }

    public void btnGuardarClick(ActionEvent actionEvent) {

        // Primero se borran los alumnos que ya no están en la lista de tus alumnos

        // Obtengo una copia de la lista original de alumnos tuyos
        List<ItemListAlumno> alumnosBorrar = new ArrayList<>(alumnosAsignaturaTodosOriginal);
        // Borro los que siguen en la lista al final de las operaciones
        alumnosBorrar.removeAll(observableList_ItemList_alumnosTuyos);
        // Los elementos que quedan ya no están en la lista despues de las operaciones, así que han sido borrados
        for (ItemListAlumno alumnoBorrar : alumnosBorrar) {
            ProfesorApiService.eliminarAlumnosAsignatura(callback.asignaturaSeleccionada, alumnoBorrar.getId()).subscribe();
        }

        // Luego, se añaden los alumnos que están nuevos en la lista de tus alumnos

        // Obtengo una copia de la lista de tus alumnos tras las operaciones
        List<ItemListAlumno> alumnosAgregar = new ArrayList<>(observableList_ItemList_alumnosTuyos);
        // Borro los que ya estaban en la lista de tus alumnos
        alumnosAgregar.removeAll(alumnosAsignaturaTodosOriginal);
        // Los elementos que quedan son elementos nuevos que necesitan ser agregados
        for (ItemListAlumno alumnoAgregar : alumnosAgregar) {
            ProfesorApiService.agregarAlumnosAsignatura(callback.asignaturaSeleccionada, alumnoAgregar.getId()).subscribe();
        }

        // Como desspués de las operaciones se sigue en la misma pantalla, las listas de elementos originales son las listas actuales
        alumnosTodosOriginal.clear();
        alumnosTodosOriginal.addAll(observableList_ItemList_alumnosTodos);

        alumnosAsignaturaTodosOriginal.clear();
        alumnosAsignaturaTodosOriginal.addAll(observableList_ItemList_alumnosTuyos);
    }

    public static class CellAlumno extends ListCell<ItemListAlumno> {
        private Parent itemRoot;
        private Label lbl_nombre;
        private EventHandler<MouseEvent> callback;

        public CellAlumno(EventHandler<MouseEvent> clickCallback) {
            callback = clickCallback;
        }

        @Override
        protected void updateItem(ItemListAlumno itemListAlumno, boolean empty) {
            super.updateItem(itemListAlumno, empty);
            if (itemListAlumno == null) {
                setText(null);
                setGraphic(null);
                return;
            }
            if (null == itemRoot) {
                try {
                    itemRoot = FXMLLoader.load(getClass().getResource(("/fxml/item/listItem_alumno.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lbl_nombre = (Label) itemRoot.lookup("#lbl_nombre");
                itemRoot.setOnMouseClicked(callback);
            }
            itemRoot.setUserData(itemListAlumno.getId());
            itemRoot.setOnMouseEntered(event -> itemRoot.getScene().setCursor(Cursor.HAND));
            itemRoot.setOnMouseExited(event -> itemRoot.getScene().setCursor(Cursor.DEFAULT));
            lbl_nombre.setText(String.format("%s %s", itemListAlumno.getNombre(), itemListAlumno.getApellidos()));
            setGraphic(itemRoot);
        }
    }
}
