package controllers.alumno;

import controllers.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import models.ApiService;
import models.ItemListEnlace;
import models.Sesion;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TemarioController {

    public Label lbl_nombreTemario;
    public Label lbl_fechaInicio;
    public Label lbl_descripcion;
    public VBox vbox_enlaces;
    public VBox vbox_asiginaciones;
    public VBox vbox_subtemas;

    public PrincipalController callBack;

    public void setTema(String asignaturaClave, String clave) {

        ApiService.obtenerTemaAlumno(asignaturaClave, clave, Sesion.getInstance().getUsuario().getNombreLogin()).subscribe(temaAlumnoResponse -> {
            lbl_nombreTemario.setText(temaAlumnoResponse.getTema().getNombreTema());
            lbl_fechaInicio.setText(String.format("Fecha comienzo: %s", temaAlumnoResponse.getTema().getFechaComienzo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            lbl_descripcion.setText(temaAlumnoResponse.getTema().getDescripcion());

            Utils.setAsignacionesInVBox(temaAlumnoResponse.getAsignaciones(), vbox_asiginaciones, callBack, getClass());
            Utils.setTemasInVBox(temaAlumnoResponse.getSubtemas(), vbox_subtemas, callBack, getClass());

            ArrayList<Parent> enlacesItemList = new ArrayList<>();
            double sumarioHeight = 0;

            for(ItemListEnlace enlace: temaAlumnoResponse.getTema().getEnlaces()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/listItem_enlaceAlumno.fxml"));
                Parent node = null;
                try {
                    node = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ListItem_EnlaceAlumno controller = loader.getController();
                controller.setEnlace(enlace);

                enlacesItemList.add(node);
                sumarioHeight += 75;
            }

            vbox_enlaces.getChildren().setAll(enlacesItemList);
            vbox_enlaces.setPrefHeight(sumarioHeight);
        });
    }
}
