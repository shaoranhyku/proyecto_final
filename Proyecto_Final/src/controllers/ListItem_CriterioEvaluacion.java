package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.CriterioEvaluacionAlumno;

public class ListItem_CriterioEvaluacion {

    @FXML
    private Label lbl_nombreCriterio;
    @FXML
    private TextField txt_notaCriterioAuto;
    @FXML
    private TextField txt_notaCriterioEvaluacion;

    public void setCriterio(CriterioEvaluacionAlumno criterio) {
        lbl_nombreCriterio.setText(criterio.getNombreCriterio());

        if (criterio.getNotaAuto() >= 0) {
            txt_notaCriterioAuto.setText(String.valueOf(criterio.getNotaAuto()));
        }

        if (criterio.getNotaEvaluacion() >= 0) {
            txt_notaCriterioEvaluacion.setText(String.valueOf(criterio.getNotaEvaluacion()));
        }

    }

}
