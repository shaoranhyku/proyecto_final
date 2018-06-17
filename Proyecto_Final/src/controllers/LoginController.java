package controllers;

import io.reactivex.Observable;
import models.ApiService;
import models.Sesion;
import models.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import responses.LoginResponse;
import retrofit2.HttpException;

import java.io.IOException;
import java.sql.*;

public class LoginController {


    public Label lbl_mensaje;
    public TextField txt_usuario;
    public PasswordField txt_clave;

    public void loginClick(MouseEvent actionEvent) {

        String usuario = txt_usuario.getText();
        String clave = txt_clave.getText();

        ApiService.login(usuario, clave).subscribe(loginResponse -> {
            login(loginResponse);
        }, error -> {
            int code = ((HttpException) error).code();
            handleError(code);
        });
    }

    private void handleError(int code) {
        lbl_mensaje.setText("Usuario o contraseña incorrecto.");
    }

    private void login(LoginResponse loginResponse) throws IOException {

        Sesion.crearSesion(loginResponse.getUsuario());

        Stage stage = (Stage) lbl_mensaje.getScene().getWindow();
        Parent root = null;

        switch (loginResponse.getTipo()){
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
