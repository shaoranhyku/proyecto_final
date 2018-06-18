package controllers.alumno;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.AsignacionLista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static controllers.Utils.setAsignacionesInVBox;

public class DiaController {

    public Label lbl_diaActual;
    public VBox vbox_asiginaciones;
    LocalDate diaActual;
    public PrincipalController callBack;

    public void initialize() {

    }

    public void setDiaActual(LocalDate diaActual) {
        // TODO: Poner bien la fecha actual con su formato
        this.diaActual = diaActual;
        lbl_diaActual.setText(diaActual.toString());

        // TODO: Buscar en la base de datos y hacer cosas
        ArrayList<AsignacionLista> asignaciones = new ArrayList<>();
//        asignaciones.add(new AsignacionLista(1, "EXPOSICIÓN FINAL DE PROYECTO EMPRESARIAL",
//                "Libre Configuración: PHP MySQL",
//                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga la",
//                LocalDateTime.of(2018, 9, 12, 15, 23), entregado));
//        asignaciones.add(new AsignacionLista(2, "EXPOSICIÓN FINAL DE PROYECTO EMPRESARIAL",
//                "Libre Configuración: PHP MySQL",
//                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga la",
//                LocalDateTime.of(2018, 9, 12, 15, 23), entregado));
//        asignaciones.add(new AsignacionLista(3, "kk",
//                "pp",
//                "Descripcion",
//                LocalDateTime.of(2018, 9, 12, 15, 23), entregado));
//        asignaciones.add(new AsignacionLista(4, "AsignacionLista 2",
//                "Asignatura 2",
//                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
//                LocalDateTime.of(2018, 5, 30, 21, 0), entregado));
//        asignaciones.add(new AsignacionLista(5, "AsignacionLista 3",
//                "Asignatura 3",
//                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
//                LocalDateTime.of(2018, 5, 30, 21, 30), entregado));
//        asignaciones.add(new AsignacionLista(6, "AsignacionLista 4",
//                "Asignatura 4",
//                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
//                LocalDateTime.of(2018, 5, 30, 23, 12), entregado));
//        asignaciones.add(new AsignacionLista(7, "AsignacionLista 5",
//                "Asignatura 5",
//                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
//                LocalDateTime.of(2018, 6, 10, 23, 12), entregado));
//        asignaciones.add(new AsignacionLista(8, "AsignacionLista 5",
//                "Asignatura 5",
//                "Descripcionc larga larga larga larga larga larga larga larga larga larga larga larga larga larga larga",
//                LocalDateTime.of(2018, 7, 10, 23, 12), entregado));

        setAsignacionesInVBox(asignaciones, vbox_asiginaciones, callBack, getClass());
    }

}
