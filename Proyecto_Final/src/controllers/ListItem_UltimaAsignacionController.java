package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ListItem_UltimaAsignacionController {

    @FXML
    private Label lbl_nombreAsignatura;
    @FXML
    private Label lbl_nombreAsignacion;
    @FXML
    private AnchorPane parent;

    private double lbl_nombreAsignatura_height;
    private double lbl_nombreAsignacion_height;
    private double lbl_nombreAsignacion_layoutY;
    private double parent_height;

    public void initialize() {
        lbl_nombreAsignatura_height = lbl_nombreAsignatura.getPrefHeight();
        lbl_nombreAsignacion_height = lbl_nombreAsignacion.getPrefHeight();
        lbl_nombreAsignacion_layoutY = lbl_nombreAsignacion.getLayoutY();
        parent_height = parent.getPrefHeight();
    }

    public void recalculateSize() {
        lbl_nombreAsignatura.setPrefHeight(lbl_nombreAsignatura_height);
        lbl_nombreAsignacion.setPrefHeight(lbl_nombreAsignacion_height);
        AnchorPane.setTopAnchor(lbl_nombreAsignacion, lbl_nombreAsignacion_layoutY);
        lbl_nombreAsignacion.setLayoutY(lbl_nombreAsignacion_layoutY);
        parent.setPrefHeight(parent_height);

        int sumaTotalHeight = 0;

        while (Utils.isLabelOverflow(lbl_nombreAsignatura)){
            lbl_nombreAsignatura.setPrefHeight(lbl_nombreAsignatura.getPrefHeight() + lbl_nombreAsignatura_height);
            sumaTotalHeight += lbl_nombreAsignatura_height;
        }

        AnchorPane.setTopAnchor(lbl_nombreAsignacion, lbl_nombreAsignacion.getLayoutY() + sumaTotalHeight);
        lbl_nombreAsignacion.setLayoutY(lbl_nombreAsignacion.getLayoutY() + sumaTotalHeight);

        while (Utils.isLabelOverflow(lbl_nombreAsignacion)){
            lbl_nombreAsignacion.setPrefHeight(lbl_nombreAsignacion.getPrefHeight() + lbl_nombreAsignacion_height);
            sumaTotalHeight += lbl_nombreAsignacion_height;
        }

        parent.setPrefHeight(parent.getPrefHeight() + sumaTotalHeight);

    }

}
