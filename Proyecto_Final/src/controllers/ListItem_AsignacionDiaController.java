package controllers;

import com.mysql.jdbc.StringUtils;
import controllers.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.Asignacion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ListItem_AsignacionDiaController {

    @FXML
    private Label lbl_asignatura;
    @FXML
    private Label lbl_hora;
    @FXML
    private Label lbl_nombreAsignacion;
    @FXML
    private Label lbl_descripcion;
    @FXML
    private AnchorPane parent;

    private double lbl_descripcion_height;
    private double parent_height;

    public void initialize() {
        lbl_descripcion_height = lbl_descripcion.getPrefHeight();
        parent_height = parent.getPrefHeight();
    }

    public void setAsignacion(Asignacion asignacion) {
        int sumatorio = 0;
        lbl_descripcion.setPrefHeight(lbl_descripcion_height);
        parent.setPrefHeight(parent_height);

        lbl_asignatura.setText(asignacion.getAsignatura());
        lbl_nombreAsignacion.setText(asignacion.getNombre());
        lbl_descripcion.setText(asignacion.getDescripcion());

        // Creo la cadena que indica la hora del dia en la que se entrega
        String horaEntrega = String.format("%s:%s - ", asignacion.getFechaEntrega().getHour(), asignacion.getFechaEntrega().getMinute());

        // Obtengo los dias, horas y minutos restantes
        LocalDateTime actual = LocalDateTime.now();
        LocalDateTime tempDateTime = LocalDateTime.from(actual);
        long diasRestantes = tempDateTime.until(asignacion.getFechaEntrega(), ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(diasRestantes);
        long horasRestanttes = tempDateTime.until(asignacion.getFechaEntrega(), ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(horasRestanttes);
        long minutosRestantes = tempDateTime.until(asignacion.getFechaEntrega(), ChronoUnit.MINUTES);

        // Creo cadenas para las hora, dias y minutos restantes si son mayores que 1
        if(diasRestantes>1){
            horaEntrega = horaEntrega.concat(String.format("%d dias ",diasRestantes));
        }
        if(horasRestanttes>1){
            horaEntrega = horaEntrega.concat(String.format("%d horas ",horasRestanttes));
        }
        if(minutosRestantes>1){
            horaEntrega = horaEntrega.concat(String.format("%d minutos ",minutosRestantes));
        }
        horaEntrega = horaEntrega.concat("restantes.");
        lbl_hora.setText(horaEntrega);

        while (Utils.isLabelOverflow(lbl_descripcion)){
            lbl_descripcion.setPrefHeight(lbl_descripcion.getPrefHeight() + lbl_descripcion_height);
            sumatorio += lbl_descripcion_height;
        }

        parent.setPrefHeight(parent.getPrefHeight() + sumatorio);
    }

    public double getHeight() {
        return parent.getPrefHeight();
    }
}
