package controllers.alumno;

import javafx.application.Application;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.ItemListEnlace;

public class ListItem_EnlaceAlumno extends Application{
    public Label lbl_descripcion;
    public Hyperlink lbl_url;

    public void setEnlace(ItemListEnlace enlace){
        lbl_descripcion.setText(enlace.getDescripcion());
        lbl_url.setText(enlace.getUrl());
        lbl_url.setOnMouseClicked(event -> getHostServices().showDocument(lbl_url.getText()));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
