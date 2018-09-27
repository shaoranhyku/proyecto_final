<?php
require 'conect.php';

if (isset($match['params']['asignatura']) && isset($match['params']['tema'])) {
    $asignatura = $match['params']['asignatura'];
    $codTemario = $match['params']['tema'];

    // Como la FK esta en DELETE CASCADE, no hace falta hacer nada más
    $eliminarTemaQuery = sprintf("DELETE FROM tema WHERE cod_temario=\"%s\" and cod_asignatura=\"%s\";", $codTemario, $asignatura);

    if (!$conn->query($eliminarTemaQuery) === TRUE) {
        http_response_code(401);
        die();
    }

} else {
    http_response_code(400);
}

$conn->close();
