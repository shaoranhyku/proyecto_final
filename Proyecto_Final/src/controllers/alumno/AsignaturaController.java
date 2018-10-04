package controllers.alumno;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.AlumnoApiService;
import models.AsignacionAlumnoLista;
import models.Sesion;
import models.ItemListTema;

import java.util.List;

import static controllers.Utils.setAsignacionesInVBox;
import static controllers.Utils.setTemasInVBox;

public class AsignaturaController {

    public Label lbl_nombreAsignatura;
    public VBox vbox_asiginaciones;
    public VBox vbox_temas;
    public PrincipalController callBack;

    private List<AsignacionAlumnoLista> asignaciones;
    private List<ItemListTema> temas;

    public void setAsignatura(String codigoAsignatura) {
        AlumnoApiService.obtenerAsignaturaAlumno(codigoAsignatura, Sesion.getInstance().getUsuario().getNombreLogin()).subscribe(asignaturaAlumnoResponse -> {
            lbl_nombreAsignatura.setText(asignaturaAlumnoResponse.getNombreAsignatura());
            asignaciones = asignaturaAlumnoResponse.getAsignaciones();
            temas = asignaturaAlumnoResponse.getTemas();
            setAsignacionesInVBox(asignaciones, vbox_asiginaciones, callBack, getClass());
            setTemasInVBox(temas, vbox_temas, callBack, getClass());
        });
    }
}
