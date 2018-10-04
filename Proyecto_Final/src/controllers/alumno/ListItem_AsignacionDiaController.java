package controllers.alumno;

import controllers.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.AsignacionAlumnoLista;

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
    private Label lbl_entregado;
    @FXML
    private AnchorPane parent;

    private double lbl_descripcion_height;
    private double parent_height;
    private String LBL_ENTREGADO_TRUE = "Entregado";
    private String LBL_ENTREGADO_FALSE = "";

    public void initialize() {
        lbl_descripcion_height = lbl_descripcion.getPrefHeight();
        parent_height = parent.getPrefHeight();
    }

    public void setAsignacion(AsignacionAlumnoLista asignacionAlumnoLista) {
        int sumatorio = 0;
        lbl_descripcion.setPrefHeight(lbl_descripcion_height);
        parent.setPrefHeight(parent_height);

        lbl_asignatura.setText(asignacionAlumnoLista.getAsignatura());
        lbl_nombreAsignacion.setText(asignacionAlumnoLista.getNombreAsignacion());
        lbl_descripcion.setText(asignacionAlumnoLista.getDescripcion());

        // Creo la cadena que indica la hora del dia en la que se entrega
        String horaEntrega = String.format("%s/%s/%s %s:%s - ", asignacionAlumnoLista.getFechaEntrega().getDayOfMonth(), asignacionAlumnoLista.getFechaEntrega().getMonthValue(), asignacionAlumnoLista.getFechaEntrega().getYear() , asignacionAlumnoLista.getFechaEntrega().getHour(), asignacionAlumnoLista.getFechaEntrega().getMinute());

        // Obtengo los dias, horas y minutos restantes
        LocalDateTime actual = LocalDateTime.now();
        LocalDateTime tempDateTime = LocalDateTime.from(actual);
        long diasRestantes = tempDateTime.until(asignacionAlumnoLista.getFechaEntrega(), ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(diasRestantes);
        long horasRestanttes = tempDateTime.until(asignacionAlumnoLista.getFechaEntrega(), ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(horasRestanttes);
        long minutosRestantes = tempDateTime.until(asignacionAlumnoLista.getFechaEntrega(), ChronoUnit.MINUTES);

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

        if(asignacionAlumnoLista.isEntregado()){
            lbl_entregado.setText(LBL_ENTREGADO_TRUE);
        }else{
            lbl_entregado.setText(LBL_ENTREGADO_FALSE);
        }
    }

    public double getHeight() {
        return parent.getPrefHeight();
    }
}
