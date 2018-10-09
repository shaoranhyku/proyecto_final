<?php
require 'conect.php';

if (isset($match['params']['asignacion'])) {
    $codAsignacion = $match['params']['asignacion'];

    // Como la FK esta en DELETE CASCADE, no hace falta hacer nada más
    $eliminarTemaQuery = sprintf("DELETE FROM asignacion WHERE cod_asignacion=\"%s\";", $codAsignacion);

    if (!$conn->query($eliminarTemaQuery) === TRUE) {
        http_response_code(401);
        die();
    }

} else {
    http_response_code(400);
}

$conn->close();
