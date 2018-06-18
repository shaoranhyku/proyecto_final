package controllers.alumno;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.ApiService;
import models.CriterioEvaluacionAlumno;
import models.Sesion;
import models.Usuario;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class AsignacionController extends Application {
    public Label lbl_nombreAsignatura;
    public Label lbl_nombreAsignacion;
    public Label lbl_descripcion;
    public Label lbl_fechaEntrega;
    public Label lbl_urlEjemplo;
    public TextArea txt_comentario;
    public Label lbl_notaTotal;
    public TextField txt_notaAuto;
    public TextField txt_notaEval;
    public Button btn_entregar;
    public VBox vbox_criteriosEvaluacion;
    public PrincipalController callBack;

    private boolean editar;

    public void setAsignacion(int codigoAsignacion) {

        ApiService.obtenerAsignacionAlumno(codigoAsignacion, Sesion.getInstance().getUsuario().getNombreLogin()).subscribe(asignacionAlumnoResponse -> {
            lbl_nombreAsignatura.setText(asignacionAlumnoResponse.getAsignacion().getAsignatura());
            lbl_nombreAsignacion.setText(asignacionAlumnoResponse.getAsignacion().getNombreAsignacion());
            lbl_descripcion.setText(asignacionAlumnoResponse.getAsignacion().getDescripcion());

            // Creo la cadena que indica la hora y el dia en la que se entrega
            String horaEntrega = String.format("Entrega: %s/%s/%s %s:%s - ", asignacionAlumnoResponse.getAsignacion().getFechaEntrega().getDayOfMonth(),
                    asignacionAlumnoResponse.getAsignacion().getFechaEntrega().getMonthValue(),
                    asignacionAlumnoResponse.getAsignacion().getFechaEntrega().getYear(),
                    asignacionAlumnoResponse.getAsignacion().getFechaEntrega().getHour(),
                    asignacionAlumnoResponse.getAsignacion().getFechaEntrega().getMinute());

            // Obtengo los dias, horas y minutos restantes
            LocalDateTime actual = LocalDateTime.now();
            LocalDateTime tempDateTime = LocalDateTime.from(actual);
            long diasRestantes = tempDateTime.until(asignacionAlumnoResponse.getAsignacion().getFechaEntrega(), ChronoUnit.DAYS);
            tempDateTime = tempDateTime.plusDays(diasRestantes);
            long horasRestanttes = tempDateTime.until(asignacionAlumnoResponse.getAsignacion().getFechaEntrega(), ChronoUnit.HOURS);
            tempDateTime = tempDateTime.plusHours(horasRestanttes);
            long minutosRestantes = tempDateTime.until(asignacionAlumnoResponse.getAsignacion().getFechaEntrega(), ChronoUnit.MINUTES);

            // Creo cadenas para las hora, dias y minutos restantes si son mayores que 1
            if (diasRestantes > 1) {
                horaEntrega = horaEntrega.concat(String.format("%d dias ", diasRestantes));
            }
            if (horasRestanttes > 1) {
                horaEntrega = horaEntrega.concat(String.format("%d horas ", horasRestanttes));
            }
            if (minutosRestantes > 1) {
                horaEntrega = horaEntrega.concat(String.format("%d minutos ", minutosRestantes));
            }
            horaEntrega = horaEntrega.concat("restantes.");
            lbl_fechaEntrega.setText(horaEntrega);

            lbl_urlEjemplo.setText(String.format("URL: https://github.com/%s/%s", Sesion.getInstance().getUsuario().getNombreGit(), asignacionAlumnoResponse.getAsignacion().getNombreGit()));

            editar = asignacionAlumnoResponse.getEntrega() != null;

            if (editar) {
                btn_entregar.setText("Volver a entregar");

                txt_comentario.setText(asignacionAlumnoResponse.getEntrega().getComentario());
                txt_notaEval.setText(asignacionAlumnoResponse.getEntrega().getNotaEval() == -1 ? "" : String.valueOf(asignacionAlumnoResponse.getEntrega().getNotaEval()));
                txt_notaAuto.setText(asignacionAlumnoResponse.getEntrega().getNotaAuto() == -1 ? "" : String.valueOf(asignacionAlumnoResponse.getEntrega().getNotaAuto()));
            }

            ArrayList<Parent> criteriosLista = new ArrayList<>();

            for (CriterioEvaluacionAlumno criterio : asignacionAlumnoResponse.getCriteriosNota()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/listItem_criterioEvaluacion.fxml"));
                Parent node = null;
                try {
                    node = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ListItem_CriterioEvaluacion controller = loader.getController();
                controller.setCriterio(criterio);

                criteriosLista.add(node);
            }

            vbox_criteriosEvaluacion.getChildren().setAll(criteriosLista);
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
