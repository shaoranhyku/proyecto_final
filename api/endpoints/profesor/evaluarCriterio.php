<?php
require 'conect.php';
require 'utils.php';

if (isset($match['params']['asignacion']) && isset($match['params']['criterio']) && isset($match['params']['alumno']) && isset($_POST['notaEval'])) {
    $alumno = $match['params']['alumno'];
    $codAsignacion = $match['params']['asignacion'];
    $codCriterio = $match['params']['criterio'];
    $notaEval = $_POST['notaEval'];

    // Comprobamos si existe el criterio para saber si hay que insertarlo o actualizarlo

    $result = $conn->query(sprintf("select * from alumno_criterio a where a.cod_alumno = \"%s\" and a.cod_asignacion = \"%s\" and a.cod_criterio = \"%s\";", $alumno, $codAsignacion, $codCriterio));

    if ($result->num_rows > 0) {
        $sql = sprintf("update alumno_criterio a set a.nota_evaluacion = %d
where a.cod_alumno = \"%s\" and a.cod_asignacion = %d and a.cod_criterio = %d;", $notaEval, $alumno, $codAsignacion, $codCriterio);
    }

    if (!$conn->query($sql) === TRUE) {
        http_response_code(401);
        die();
    }

} else {
    http_response_code(400);
}

$conn->close();