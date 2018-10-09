<?php
require 'conect.php';

if (isset($match['params']['asignacion']) && isset($match['params']['tema'])) {
    $codAsignacion = $match['params']['asignacion'];
    $tema = $match['params']['tema'];

    // Como la FK esta en DELETE CASCADE, no hace falta hacer nada más
    $eliminarTemaQuery = sprintf("DELETE FROM asignacion_tema WHERE cod_temario=\"%s\" and cod_asignacion=\"%s\";", $tema, $codAsignacion);

    if (!$conn->query($eliminarTemaQuery) === TRUE) {
        http_response_code(401);
        die();
    }

} else {
    http_response_code(400);
}

$conn->close();
