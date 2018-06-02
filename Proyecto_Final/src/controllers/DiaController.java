package controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.Asignacion;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DiaController {

    public Label lbl_diaActual;
    public VBox vbox_asiginaciones;
    LocalDate diaActual;

    public void initialize() {

    }

    public void setDiaActual(LocalDate diaActual) {
        // TODO: Poner bien la fecha actual con su formato
        this.diaActual = diaActual;
        lbl_diaActual.setText(diaActual.toString());

        // TODO: Buscar en la base de datos y hacer cosas
        ArrayList<Asignacion> asignaciones = new ArrayList<>();
        asignaciones.add(new Asignacion("EXPOSICIÓN FINAL DE PROYECTO EMPRESARIAL",
                "Libre Configuración: PHP MySQL",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga la",
                LocalDateTime.of(2018, 9, 12, 15, 23), false));
        asignaciones.add(new Asignacion("EXPOSICIÓN FINAL DE PROYECTO EMPRESARIAL",
                "Libre Configuración: PHP MySQL",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga la",
                LocalDateTime.of(2018, 9, 12, 15, 23), true));
        asignaciones.add(new Asignacion("kk",
                "pp",
                "Descripcion",
                LocalDateTime.of(2018, 9, 12, 15, 23), false));
        asignaciones.add(new Asignacion("Asignacion 2",
                "Asignatura 2",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 5, 30, 21, 0), true));
        asignaciones.add(new Asignacion("Asignacion 3",
                "Asignatura 3",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 5, 30, 21, 30), true));
        asignaciones.add(new Asignacion("Asignacion 4",
                "Asignatura 4",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 5, 30, 23, 12), false));
        asignaciones.add(new Asignacion("Asignacion 5",
                "Asignatura 5",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 6, 10, 23, 12),false));
        asignaciones.add(new Asignacion("Asignacion 5",
                "Asignatura 5",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 7, 10, 23, 12), true));

        ArrayList<Parent> asignacionesItemList = new ArrayList<>();
        double sumarioHeight = 0;

        for (Asignacion asignacion : asignaciones) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/listItem_asignacionDia.fxml"));
            Parent node = null;
            try {
                node = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ListItem_AsignacionDiaController controller = loader.getController();
            controller.setAsignacion(asignacion);

            asignacionesItemList.add(node);
            sumarioHeight += controller.getHeight() + 20;
        }

        vbox_asiginaciones.getChildren().setAll(asignacionesItemList);

        for (Parent node : asignacionesItemList) {
            VBox.setMargin(node, new Insets(10, 10, 10, 10));
        }

        int elementos = vbox_asiginaciones.getChildren().size();

        vbox_asiginaciones.setPrefHeight(sumarioHeight);
    }

}
