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

import java.io.IOException;

public class AlumnadoController {
    public ListView lst_tusAlumnos;
    public ListView lst_todosAlumnos;
    public ProfesorController callback;
    private ObservableList<ItemListAlumno> observableList_ItemList_alumnosTodos;
    private ObservableList<ItemListAlumno> observableList_ItemList_alumnosTuyos;

    public void initialize() {

        // TODO: Buscar alumnos en api

        observableList_ItemList_alumnosTodos = FXCollections.observableArrayList(
                new ItemListAlumno("usu1", "Baldomero", "Llegate Ligero"),
                new ItemListAlumno("usu2", "Germán", "Ginés"),
                new ItemListAlumno("usu4", "Otro", "Tio"),
                new ItemListAlumno("usu5", "Lorem ipsum dolor sit", "amet viverra fusce.")
        );

        observableList_ItemList_alumnosTuyos = FXCollections.observableArrayList(
                new ItemListAlumno("usu3", "Dolores", "Fuertes")
        );

        lst_todosAlumnos.setItems(observableList_ItemList_alumnosTodos);

        lst_todosAlumnos.setCellFactory(param -> new CellAlumno(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if(mouseEvent.getClickCount() == 2){
                    btnAñadirClick(null);
                }
            }
        }));

        lst_tusAlumnos.setItems(observableList_ItemList_alumnosTuyos);

        lst_tusAlumnos.setCellFactory(param -> new CellAlumno(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if(mouseEvent.getClickCount() == 2){
                    btnQuitarClick(null);
                }
            }
        }));
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
