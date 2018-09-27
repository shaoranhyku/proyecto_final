<?php
require 'conect.php';

if (isset($match['params']['asignatura']) && isset($match['params']['tema']) && isset($_POST['nuevoCodTemario']) && isset($_POST['nombre']) && isset($_POST['descripcion']) && isset($_POST['fechaComienzo'])) {
    $asignatura = $match['params']['asignatura'];
    $codTemario = $match['params']['tema'];
    $nuevoCodTemario = $_POST['nuevoCodTemario'];
    $nombre = $_POST['nombre'];
    $descripcion = $_POST['descripcion'];
    $fechaComienzo = $_POST['fechaComienzo'];

    $comprobarExistenciaQuery = sprintf("SELECT * FROM tema t where t.cod_asignatura = \"%s\" and t.cod_temario=\"%s\";", $asignatura, $codTemario);

    $result = $conn->query($comprobarExistenciaQuery);

    // Si no existe no se puede editar
    if($result->num_rows < 0){
        http_response_code(400);
        die();
    }else{
        $agregarTemaQuery = sprintf("UPDATE tema SET cod_temario=\"%s\", nombre=\"%s\", descripcion=\"%s\", fecha_comienzo=\"%s\" WHERE cod_temario=\"%s\" and cod_asignatura=\"%s\";
", $nuevoCodTemario, $nombre, $descripcion, $fechaComienzo, $codTemario, $asignatura);

        if (!$conn->query($agregarTemaQuery) === TRUE) {
            http_response_code(401);
            die();
        }

    }

} else {
    http_response_code(400);
}

$conn->close();
