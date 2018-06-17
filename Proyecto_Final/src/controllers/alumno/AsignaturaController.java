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

import static controllers.Utils.setAsignacionesInVBox;
import static controllers.Utils.setTemasInVBox;

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
                LocalDateTime.of(2018, 9, 12, 15, 23)));
        asignaciones.add(new AsignacionLista(2, "EXPOSICIÓN FINAL DE PROYECTO EMPRESARIAL",
                "Libre Configuración: PHP MySQL",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga la",
                LocalDateTime.of(2018, 9, 12, 15, 23)));
        asignaciones.add(new AsignacionLista(3, "kk",
                "pp",
                "Descripcion",
                LocalDateTime.of(2018, 9, 12, 15, 23)));
        asignaciones.add(new AsignacionLista(4, "AsignacionLista 2",
                "Asignatura 2",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 5, 30, 21, 0)));
        asignaciones.add(new AsignacionLista(5, "AsignacionLista 3",
                "Asignatura 3",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 5, 30, 21, 30)));
        asignaciones.add(new AsignacionLista(6, "AsignacionLista 4",
                "Asignatura 4",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 5, 30, 23, 12)));
        asignaciones.add(new AsignacionLista(7, "AsignacionLista 5",
                "Asignatura 5",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 6, 10, 23, 12)));
        asignaciones.add(new AsignacionLista(8, "AsignacionLista 5",
                "Asignatura 5",
                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
                LocalDateTime.of(2018, 7, 10, 23, 12)));

        setAsignacionesInVBox(asignaciones, vbox_asiginaciones, callBack, getClass());

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

        setTemasInVBox(temas, vbox_temas, callBack, getClass());
    }



}
