package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.AlumnoApiService;
import models.Sesion;
import retrofit2.HttpException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class LoginController {


    public Label lbl_mensaje;
    public TextField txt_usuario;
    public PasswordField txt_clave;

    public void loginClick(MouseEvent actionEvent) {

        String usuario = txt_usuario.getText();
        String clave = txt_clave.getText();
        String autorizacion = "";

        try {
            autorizacion = String.format("Basic %s",Base64.getEncoder().encodeToString(String.format("%s:%s", usuario, clave).getBytes("UTF-8")));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AlumnoApiService.login(autorizacion).subscribe(loginResponse -> {
            login(loginResponse.response().headers().get("Authorization"));
        }, error -> {
            int code = ((HttpException) error).code();
            handleError(code);
        });
    }

    private void handleError(int code) {
        lbl_mensaje.setText("Usuario o contraseña incorrecto.");
    }

    private void login(String jwtToken) throws IOException {

        String cleanToken = jwtToken.substring(7, jwtToken.length());

        Sesion.crearSesion(cleanToken);

        Stage stage = (Stage) lbl_mensaje.getScene().getWindow();
        Parent root = null;

        switch (Sesion.getInstance().getUsuario().getRol()){
            case "alumno":
                root = FXMLLoader.load(getClass().getResource("/fxml/ui/alumno/scene_principal.fxml"));/* Exception */
                break;
            case "profesor":
                root = FXMLLoader.load(getClass().getResource("/fxml/ui/profesor/scene_profesor.fxml"));/* Exception */
                break;
        }

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(1280);
        stage.setMinHeight(740);
        stage.setMaxWidth(Double.MAX_VALUE);
        stage.setMaxHeight(Double.MAX_VALUE);
        stage.setWidth(1280);
        stage.setHeight(740);
        stage.centerOnScreen();
    }
}
