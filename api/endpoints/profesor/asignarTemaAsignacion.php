<?php
require 'conect.php';

if (isset($match['params']['asignacion']) && isset($_POST['asignatura']) && isset($_POST['tema'])) {
    $asignacion = $match['params']['asignacion'];
    $asignatura = $_POST['asignatura'];
    $codTemario = $_POST['tema'];

    $agregarTemaQuery = sprintf("INSERT INTO asignacion_tema (cod_asignatura, cod_temario, cod_asignacion) VALUES (\"%s\", \"%s\", \"%s\");
", $asignatura, $codTemario, $asignacion);

    if (!$conn->query($agregarTemaQuery) === TRUE) {
        http_response_code(401);
        die();
    }

} else {
    http_response_code(400);
}

$conn->close();
