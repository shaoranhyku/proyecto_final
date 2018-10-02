<?php
require 'conect.php';

if (isset($_POST['nombre']) && isset($_POST['nombreGit']) && isset($_POST['descripcion']) && isset($_POST['fechaEntrega']) && isset($_POST['fechaCreacion'])) {
    $nombre = $_POST['nombre'];
    $nombreGit = $_POST['nombreGit'];
    $descripcion = $_POST['descripcion'];
    $fechaEntrega = $_POST['fechaEntrega'];
    $fechaCreacion = $_POST['fechaCreacion'];

    // Si existe un nombregit repetido, no se permite añadir, ya que no se pueden crear dos repositorios iguales
    $comprobarExistenciaQuery = sprintf("SELECT * FROM asignacion where nombre_git = \"%s\";", $nombreGit);

    $result = $conn->query($comprobarExistenciaQuery);

    if($result->num_rows > 0){
        http_response_code(400);
        die();
    }else{
        $agregarTemaQuery = sprintf("INSERT INTO asignacion (nombre, nombre_git, descripcion, fecha_entrega, fecha_creacion) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\");",
            $nombre, $nombreGit, $descripcion, $fechaEntrega, $fechaCreacion);

        if (!$conn->query($agregarTemaQuery) === TRUE) {
            http_response_code(401);
            die();
        }

    }

} else {
    http_response_code(400);
}

$conn->close();
