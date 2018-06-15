package controllers;

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

import java.io.IOException;
import java.sql.*;

public class LoginController {


    public Label lbl_mensaje;
    public TextField txt_usuario;
    public PasswordField txt_clave;

    public void login(MouseEvent actionEvent) throws IOException, SQLException {

        String usuario = txt_usuario.getText();
        String clave = txt_clave.getText();

        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/proyecto_final?allowMultiQueries=true&useSSL=false"
                , "proyecto"
                , "proyecto12345");

        //Creamos la sentencia que vamos a ejecutar
        Statement instruccion = conexion.createStatement();

        String consulta = String.format("SELECT * FROM usuario WHERE nombre_login = \"%s\" AND clave = \"%s\";", usuario, clave);

        ResultSet resultado = instruccion.executeQuery(consulta);

        if (resultado.next()) {
            String nombre = resultado.getString(2);
            String apellidos = resultado.getString(3);
            // TODO: Sacar usuario git
            String nombreGit = "pruebausuariogit";
            Sesion.crearSesion(new Usuario(usuario, clave, nombre, apellidos, nombreGit));
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //Parent root = FXMLLoader.load(getClass().getResource("/fxml/ui/profesor/scene_profesor.fxml"));/* Exception */
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ui/alumno/scene_principal.fxml"));/* Exception */
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
            stage.setMinWidth(1280);
            stage.setMinHeight(740);
            stage.setWidth(1280);
            stage.setHeight(740);
        } else {
            lbl_mensaje.setText("Usuario o contraseña incorrecto.");
        }
    }
}
