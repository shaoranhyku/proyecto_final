<?php
require 'conect.php';

if (isset($match['params']['asignacion']) && isset($_POST['nombre']) && isset($_POST['porcentaje'])) {
    $asignacion = $match['params']['asignacion'];
    $nombre = $_POST['nombre'];
    $porcentaje = $_POST['porcentaje'];

    $agregarTemaQuery = sprintf("INSERT INTO criterio_evaluacion (cod_asignacion, nombre, porcentaje) VALUES (\"%s\", \"%s\", \"%s\");", $asignacion, $nombre, $porcentaje);

    if (!$conn->query($agregarTemaQuery) === TRUE) {
        http_response_code(401);
        die();
    }

} else {
    http_response_code(400);
}

$conn->close();
