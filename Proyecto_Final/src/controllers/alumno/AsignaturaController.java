package controllers.alumno;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.AsignacionLista;
import models.TemaLista;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AsignaturaController {

    public Label lbl_nombreAsignatura;
    public VBox vbox_asiginaciones;
    public VBox vbox_temas;
    public PrincipalController callBack;

    public void setAsignatura(String codigoAsignatura) {

        // TODO: Obtener datos asignatura y poner nombre
        lbl_nombreAsignatura.setText("NOMBRE ASIGNATURA");

        // TODO: Buscar en la base de datos y hacer cosas
        ArrayList<AsignacionLista> asignaciones = new ArrayList<>();
        asignaciones.add(new AsignacionLista(1, "EXPOSICIÓN FINAL DE PROYECTO EMPRESARIAL",
                "Libre Configuración: PHP MySQL",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga la",
                LocalDateTime.of(2018, 9, 12, 15, 23), false));
        asignaciones.add(new AsignacionLista(2, "EXPOSICIÓN FINAL DE PROYECTO EMPRESARIAL",
                "Libre Configuración: PHP MySQL",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga la",
                LocalDateTime.of(2018, 9, 12, 15, 23), true));
        asignaciones.add(new AsignacionLista(3, "kk",
                "pp",
                "Descripcion",
                LocalDateTime.of(2018, 9, 12, 15, 23), false));
        asignaciones.add(new AsignacionLista(4, "AsignacionLista 2",
                "Asignatura 2",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 5, 30, 21, 0), true));
        asignaciones.add(new AsignacionLista(5, "AsignacionLista 3",
                "Asignatura 3",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 5, 30, 21, 30), true));
        asignaciones.add(new AsignacionLista(6, "AsignacionLista 4",
                "Asignatura 4",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 5, 30, 23, 12), false));
        asignaciones.add(new AsignacionLista(7, "AsignacionLista 5",
                "Asignatura 5",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 6, 10, 23, 12),false));
        asignaciones.add(new AsignacionLista(8, "AsignacionLista 5",
                "Asignatura 5",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 7, 10, 23, 12), true));

        ArrayList<Parent> asignacionesItemList = new ArrayList<>();
        double sumarioHeight = 0;

        for (AsignacionLista asignacionLista : asignaciones) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/listItem_asignacionDia.fxml"));
            Parent node = null;
            try {
                node = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ListItem_AsignacionDiaController controller = loader.getController();
            controller.setAsignacion(asignacionLista);
            node.setOnMouseEntered(event -> vbox_asiginaciones.getScene().setCursor(Cursor.HAND));
            node.setOnMouseExited(event -> vbox_asiginaciones.getScene().setCursor(Cursor.DEFAULT));
            node.setOnMouseClicked(event -> callBack.setCenterAsignacion(asignacionLista.getCodigoAsignacion()));

            asignacionesItemList.add(node);
            sumarioHeight += controller.getHeight() + 20;
        }

        vbox_asiginaciones.getChildren().setAll(asignacionesItemList);

        for (Parent node : asignacionesItemList) {
            VBox.setMargin(node, new Insets(10, 10, 10, 10));
        }

        vbox_asiginaciones.setPrefHeight(sumarioHeight);

        // TODO: Poner temas

        ArrayList<TemaLista> temas = new ArrayList<>();
        temas.add(new TemaLista(codigoAsignatura, "1", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "1.1", "Subtema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "2", "Tema 2"));
        temas.add(new TemaLista(codigoAsignatura, "3", "Tema 3"));
        temas.add(new TemaLista(codigoAsignatura, "3.1", "Tema 3 sub"));
        temas.add(new TemaLista(codigoAsignatura, "3.2", "Tema 3 sub 2"));
        temas.add(new TemaLista(codigoAsignatura, "4", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.1", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.2", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.3", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.2", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.3", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.2", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.3", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.2", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.3", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.2", "Tema Primero"));
        temas.add(new TemaLista(codigoAsignatura, "5.3", "Tema Primero"));

        ArrayList<Parent> temasItemList = new ArrayList<>();
        sumarioHeight = 0;

        for (TemaLista temaLista : temas) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/listItem_tema.fxml"));
            Parent node = null;
            try {
                node = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ListItem_TemaController controller = loader.getController();
            controller.setTema(temaLista);
            node.setOnMouseEntered(event -> vbox_asiginaciones.getScene().setCursor(Cursor.HAND));
            node.setOnMouseExited(event -> vbox_asiginaciones.getScene().setCursor(Cursor.DEFAULT));
            node.setOnMouseClicked(event -> callBack.setCenterTema(temaLista.getAsignaturaClave(), temaLista.getClave()));

            temasItemList.add(node);
            // Altura del elemento + margen
            sumarioHeight += 70 + 20;
        }

        vbox_temas.getChildren().setAll(temasItemList);

        for (Parent node : temasItemList) {
            VBox.setMargin(node, new Insets(10, 10, 10, 10));
        }

        vbox_temas.setPrefHeight(sumarioHeight);
    }

}
