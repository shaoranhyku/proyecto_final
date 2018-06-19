package controllers.alumno;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.ApiService;
import models.AsignacionLista;
import models.Sesion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        this.diaActual = diaActual;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'de' yyyy");
        lbl_diaActual.setText(diaActual.format(formatter));

        ApiService.obtenerAsignacionesDia(diaActual, Sesion.getInstance().getUsuario().getNombreLogin()).subscribe(asignacionLista -> {
            setAsignacionesInVBox(asignacionLista, vbox_asiginaciones, callBack, getClass());
        });
    }

}
