<?php
require 'conect.php';

if (isset($match['params']['asignacion']) && isset($_POST['nombre']) && isset($_POST['nombreGit']) && isset($_POST['descripcion']) && isset($_POST['fechaEntrega'])) {
    $codAsignacion = $match['params']['asignacion'];
    $nombre = $_POST['nombre'];
    $nombreGit = $_POST['nombreGit'];
    $descripcion = $_POST['descripcion'];
    $fechaEntrega = $_POST['fechaEntrega'];

    // Si existe un nombregit repetido, no se permite añadir, ya que no se pueden crear dos repositorios iguales
    $comprobarExistenciaQuery = sprintf("SELECT * FROM asignacion where cod_asignacion = \"%s\";", $codAsignacion);

    $result = $conn->query($comprobarExistenciaQuery);

    if($result->num_rows < 0){
        http_response_code(400);
        die();
    }else{
        $agregarTemaQuery = sprintf("UPDATE asignacion SET nombre=\"%s\", nombre_git=\"%s\", descripcion=\"%s\", fecha_entrega=\"%s\" WHERE cod_asignacion=\"%s\";",
            $nombre, $nombreGit, $descripcion, $fechaEntrega, $codAsignacion);

        if (!$conn->query($agregarTemaQuery) === TRUE) {
            http_response_code(401);
            die();
        }else{
            $idAsignacion = $conn->insert_id;
            echo $idAsignacion;
        }

    }

} else {
    http_response_code(400);
}

$conn->close();
