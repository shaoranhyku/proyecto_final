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
import models.AsignacionAlumno;
import models.CriterioEvaluacionAlumno;
import models.Sesion;
import models.Usuario;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class AsignacionController extends Application {
    public Label lbl_nombreAsignatura;
    public Label lbl_tema;
    public Label lbl_nombreAsignacion;
    public Label lbl_descripcion;
    public Label lbl_fechaEntrega;
    public Label lbl_urlEjemplo;
    public TextField txt_urlGitHub;
    public TextArea txt_comentario;
    public Label lbl_notaTotal;
    public TextField txt_notaAuto;
    public TextField txt_notaEval;
    public Button btn_entregar;
    public VBox vbox_criteriosEvaluacion;
    public PrincipalController callBack;

    public void setAsignacion(int codigoAsignacion) {
        ArrayList<String> temas = new ArrayList<>();
        temas.add("Tema 1");
        temas.add("Tema 4");
        ArrayList<CriterioEvaluacionAlumno> criterios = new ArrayList<>();
        criterios.add(new CriterioEvaluacionAlumno("3FN", -1, -1));
        criterios.add(new CriterioEvaluacionAlumno("Entidades creadas correctamente", -1, -1));
        criterios.add(new CriterioEvaluacionAlumno("Paso a tabla", -1, -1));
        AsignacionAlumno asignacion = new AsignacionAlumno("Base de Datos", temas, "Trabajo Final Base de Datos", "Entregar el examen final de Base de Datos junto con el diagrama entidad relación.", LocalDateTime.of(2018, 10, 23, 11, 30), LocalDateTime.now(), "finaBaseDatos", null, null, -1, -1, criterios);

        lbl_nombreAsignatura.setText(asignacion.getNombreAsignatura());

        String temasString = asignacion.getTemas().get(0);
        for (String tema : asignacion.getTemas().subList(1, asignacion.getTemas().size())) {
            temasString = temasString.concat(String.format(", %s", tema));
        }

        lbl_tema.setText(temasString);

        lbl_nombreAsignacion.setText(asignacion.getNombreAsignacion());

        lbl_descripcion.setText(asignacion.getDescripcion());

        // Creo la cadena que indica la hora y el dia en la que se entrega
        String horaEntrega = String.format("Entrega: %s/%s/%s %s:%s - ", asignacion.getFechaEntrega().getDayOfMonth(),
                asignacion.getFechaEntrega().getMonthValue(),
                asignacion.getFechaEntrega().getYear(),
                asignacion.getFechaEntrega().getHour(),
                asignacion.getFechaEntrega().getMinute());

        // Obtengo los dias, horas y minutos restantes
        LocalDateTime actual = LocalDateTime.now();
        LocalDateTime tempDateTime = LocalDateTime.from(actual);
        long diasRestantes = tempDateTime.until(asignacion.getFechaEntrega(), ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(diasRestantes);
        long horasRestanttes = tempDateTime.until(asignacion.getFechaEntrega(), ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(horasRestanttes);
        long minutosRestantes = tempDateTime.until(asignacion.getFechaEntrega(), ChronoUnit.MINUTES);

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

        Usuario usuario = Sesion.getInstance().getUsuario();

        lbl_urlEjemplo.setText(String.format("URL: https://github.com/%s/%s", usuario.getNombreGit(), asignacion.getNombreGit()));

        // TODO: Comprobar si la consuta (haga como la haga) devolvera null o vacio.
        if(asignacion.getEnlaceGit() != null){
            txt_urlGitHub.setText(asignacion.getEnlaceGit());
        }

        if(asignacion.getComentario() != null){
            txt_comentario.setText(asignacion.getComentario());
        }

        if(asignacion.getNotaAuto() >= 0){
            txt_notaAuto.setText(String.valueOf(asignacion.getNotaAuto()));
        }

        if(asignacion.getNotaEvaluacion() >= 0){
            txt_notaEval.setText(String.valueOf(asignacion.getNotaEvaluacion()));
        }

        ArrayList<Parent> criteriosLista = new ArrayList<>();

        for(CriterioEvaluacionAlumno criterio : criterios){
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
    }

    public void abrirNavegador(ActionEvent actionEvent) {
        HostServicesDelegate hostServices = HostServicesFactory.getInstance(this);
        hostServices.showDocument("https://github.com/shaoranhyku/proyecto_final/blob/master/Documentacion/Proyecto%20Final.pdf");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
