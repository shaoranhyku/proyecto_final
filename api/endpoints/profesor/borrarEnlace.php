<?php
require 'conect.php';

if (isset($match['params']['asignatura']) && isset($match['params']['tema']) && isset($match['params']['enlace'])) {
    $asignatura = $match['params']['asignatura'];
    $codTemario = $match['params']['tema'];
    $codEnlace = $match['params']['enlace'];

    // Como la FK esta en DELETE CASCADE, no hace falta hacer nada más
    $eliminarTemaQuery = sprintf("DELETE FROM enlace WHERE cod_enlace=\"%s\"  and cod_asignatura=\"%s\" and`cod_temario`=\"%s\";", $codEnlace, $asignatura, $codTemario);

    if (!$conn->query($eliminarTemaQuery) === TRUE) {
        http_response_code(401);
        die();
    }

} else {
    http_response_code(400);
}

$conn->close();
