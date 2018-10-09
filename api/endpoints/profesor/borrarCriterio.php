<?php
require 'conect.php';

if (isset($match['params']['asignacion']) && isset($match['params']['criterio'])) {
    $codAsignacion = $match['params']['asignacion'];
    $criterio = $match['params']['criterio'];

    // Como la FK esta en DELETE CASCADE, no hace falta hacer nada más
    $eliminarTemaQuery = sprintf("DELETE FROM criterio_evaluacion WHERE cod_criterio=\"%s\" and cod_asignacion=\"%s\";", $criterio, $codAsignacion);

    if (!$conn->query($eliminarTemaQuery) === TRUE) {
        http_response_code(401);
        die();
    }

} else {
    http_response_code(400);
}

$conn->close();
