package controllers.profesor;

import controllers.alumno.ListItem_CriterioEvaluacionAlumno;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.CriterioEvaluacionAlumno;
import models.ProfesorApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvaluarAsignacionController {
    public Label lbl_nombreAlumno;
    public Label lbl_fechaEntrega;
    public Label lbl_asignacion;
    public Hyperlink lbl_enlace;
    public TextField lbl_notaTotal;
    public TextField lbl_notaTotalAuto;
    public Label lbl_comentario;
    public TextArea txt_comentario;
    public VBox vbox_criteriosEvaluacion;

    public ProfesorController callback;
    private List<CriterioEvaluacionAlumno> listaCriterios;
    private int contador;
    private int codigoAsignacion;
    private String alumno;

    public void setAsignacionEvaluarActual(String asignaturaSeleccionada, int codigoAsignacion, String alumno) {
        this.codigoAsignacion = codigoAsignacion;
        this.alumno = alumno;

        ProfesorApiService.obtenerAsignacionEvaluar(String.valueOf(codigoAsignacion), alumno).subscribe(asignacionEvaluarProfesorResponse -> {
            // Se ponen los valores actuales
            lbl_nombreAlumno.setText(asignacionEvaluarProfesorResponse.getNombreAlumno());
            lbl_fechaEntrega.setText(String.format("%s/%s/%s %s:%s", asignacionEvaluarProfesorResponse.getAsignacion().getFechaEntrega().getDayOfMonth(),
                    asignacionEvaluarProfesorResponse.getAsignacion().getFechaEntrega().getMonthValue(),
                    asignacionEvaluarProfesorResponse.getAsignacion().getFechaEntrega().getYear(),
                    asignacionEvaluarProfesorResponse.getAsignacion().getFechaEntrega().getHour(),
                    asignacionEvaluarProfesorResponse.getAsignacion().getFechaEntrega().getMinute()));
            lbl_asignacion.setText(asignacionEvaluarProfesorResponse.getAsignacion().getNombreAsignacion());
            lbl_enlace.setText(asignacionEvaluarProfesorResponse.getEntrega().getRutaArchivo());
            lbl_notaTotal.setText(String.valueOf(asignacionEvaluarProfesorResponse.getEntrega().getNotaEval()));
            lbl_notaTotalAuto.setText(String.valueOf(asignacionEvaluarProfesorResponse.getEntrega().getNotaAuto()));
            lbl_comentario.setText(asignacionEvaluarProfesorResponse.getEntrega().getComentario());
            txt_comentario.setText(asignacionEvaluarProfesorResponse.getEntrega().getComentarioProfesor());

            // Se ponen los criterios
            ArrayList<Parent> criteriosLista = new ArrayList<>();
            listaCriterios = asignacionEvaluarProfesorResponse.getCriteriosNota();

            for (CriterioEvaluacionAlumno criterio : asignacionEvaluarProfesorResponse.getCriteriosNota()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/listItem_criterioEvaluacionProfesor.fxml"));
                Parent node = null;
                try {
                    node = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ListItem_CriterioEvaluacionProfesor controller = loader.getController();
                controller.setCriterio(criterio);

                criteriosLista.add(node);
            }

            vbox_criteriosEvaluacion.getChildren().setAll(criteriosLista);
        });
    }

    public void btnEvaluarClick(ActionEvent actionEvent) {
        // Se recogen los valores de la entrega y se validan
        String comentarioProfesor;

        // Si existe un comentario
        if (txt_comentario.getText() != null) {
            if (txt_comentario.getText().length() > 255) {
                mostrarDialogoDatoNoValido("Comentario profesor demasiado largo.", "El tamaño del comentario de profesor supera los 255 caracteres.");
                return;
            } else {
                comentarioProfesor = txt_comentario.getText();
            }
        } else {
            comentarioProfesor = "";
        }

        ObservableList<Node> listaCriteriosNode = vbox_criteriosEvaluacion.getChildren();

        for (int i = 0; i < listaCriteriosNode.size(); i++) {
            Node criterioNode = listaCriteriosNode.get(i);

            int notaEvaluacin;

            try {
                notaEvaluacin = Integer.parseInt(((TextField) criterioNode.lookup("#txt_notaCriterioEvaluacion")).getText());
            } catch (NumberFormatException nfe) {
                mostrarDialogoDatoNoValido("Nota criterio incorrecta.", "La nota de los criterios ha de ser un número entre 0 y 10.");
                return;
            }

            if (notaEvaluacin < 0 || notaEvaluacin > 10) {
                mostrarDialogoDatoNoValido("Nota criterio incorrecta.", "La nota de los criterios ha de ser un número entre 0 y 10.");
                return;
            }

            listaCriterios.get(i).setNotaEval(notaEvaluacin);
        }
        // Primero, por cuestiones de como se calcula la nota total en el servidor, debemos insertar los criterios

        contador = 0;

        for (CriterioEvaluacionAlumno criterio : listaCriterios) {
            ProfesorApiService.evaluarCriterio(String.valueOf(codigoAsignacion), alumno, String.valueOf(criterio.getCriterio().getCodCriterio()), criterio.getNotaEval()).subscribe(() -> {
                if (contador < listaCriterios.size() - 1) {
                    contador++;
                } else {
                    ProfesorApiService.evaluarAsignacion(String.valueOf(codigoAsignacion), alumno, comentarioProfesor).subscribe(() -> {
                        callback.setCenterListaEvaluarAsignaciones();
                    });
                }
            });
        }
    }

    public void mostrarDialogoDatoNoValido(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}
