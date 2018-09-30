package controllers.profesor;

import controllers.Utils;
import javafx.scene.layout.VBox;
import models.ProfesorApiService;

public class SeleccionarTemaController {
    public ProfesorController callback;
    public VBox vbox_temas;

    public void buscarTemas() {
        // Se buscan los temas en la base de datos
        ProfesorApiService.obtenerTemasAsignatura(callback.asignaturaSeleccionada).subscribe(temas -> {
            Utils.setTemasInVBox(temas, vbox_temas, callback, getClass());
        });
    }
}
