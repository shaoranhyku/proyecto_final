<?php
require 'conect.php';

if (isset($match['params']['asignatura']) && isset($match['params']['tema']) && isset($_POST['url']) && isset($_POST['descripcion'])) {
    $asignatura = $match['params']['asignatura'];
    $codTemario = $match['params']['tema'];
    $url = $_POST['url'];
    $descripcion = $_POST['descripcion'];

    $agregarTemaQuery = sprintf("INSERT INTO enlace (cod_asignatura, cod_temario, url, descripcion) VALUES (\"%s\", \"%s\", \"%s\", \"%s\");", $asignatura, $codTemario, $url, $descripcion);

    if (!$conn->query($agregarTemaQuery) === TRUE) {
        http_response_code(401);
        die();
    }

} else {
    http_response_code(400);
}

$conn->close();
