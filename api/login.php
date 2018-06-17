<?php
require 'conect.php';
require 'responses/loginResponse.php';
require 'models/usuario.php';

if(isset($_POST['usuario']) && isset($_POST['password'])){

    $usuarioQuery = sprintf("SELECT * FROM usuario WHERE nombre_login = \"%s\" AND clave = \"%s\";", $_POST['usuario'], $_POST['password']);
    $alumnoQuery = sprintf("SELECT * FROM alumno WHERE nombre_login = \"%s\";", $_POST['usuario']);
    $profesorQuery = sprintf("SELECT * FROM profesor WHERE nombre_login = \"%s\";", $_POST['usuario']);

    $result = $conn->query($usuarioQuery);

    // Si existe en la tabla usuario existe el usuario y se comprueba que tipo de usuario es
    if ($result->num_rows > 0) {

        $response = new loginResponse();

        $row = $result->fetch_assoc();

        $usuario = new usuario();

        $usuario->nombreLogin = $row["nombre_login"];
        $usuario->clave = $row["clave"];
        $usuario->nombre = $row["nombre"];
        $usuario->apellidos = $row["apellidos"];
        $usuario->nombreGit = "";

        // Se comprueba si es un alumno
        $result = $conn->query($alumnoQuery);

        if ($result->num_rows > 0) {

            // Es un alumno. Se crea la respuesta y se envía
            $response->tipo = "alumno";

            $row = $result->fetch_assoc();
            $usuario->nombreGit = $row["usuario_git"];

            $response->usuario = $usuario;

            echo json_encode($response);

        }else{

            // Se comprueba si es un profesor
            $result = $conn->query($profesorQuery);

            if ($result->num_rows > 0) {

                // Es un profesor. Se crea la respuesta y se envía
                $response->tipo = "profesor";

                $response->usuario = $usuario;

                echo json_encode($response);

            }else{
                http_response_code(401);
            }

        }

    } else {
        http_response_code(401);
    }

}else{
    http_response_code(400);
}

$conn->close();