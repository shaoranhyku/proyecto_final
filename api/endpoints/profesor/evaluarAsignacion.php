<?php
require 'conect.php';
require 'utils.php';

if (isset($match['params']['asignacion']) && isset($match['params']['alumno']) && isset($_POST['comentario'])) {
    $alumno = $match['params']['alumno'];
    $codAsignacion = $match['params']['asignacion'];
    $comentario = $_POST['comentario'];

    // Calculamos la nota notal
    $sql = sprintf("select alcri.nota_evaluacion, cri.porcentaje from alumno_criterio alcri
join criterio_evaluacion cri on alcri.cod_criterio = cri.cod_criterio and alcri.cod_asignacion = cri.cod_asignacion
where alcri.cod_asignacion = %d and alcri.cod_alumno = \"%s\";", $codAsignacion, $alumno);

    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $notaEvalTotal = 0;

        while ($row = $result->fetch_assoc()) {
            $notaEvalCriterio = (int)$row["nota_evaluacion"];
            $porcentaje = (int)$row["porcentaje"];

            $notaEvalTotal = $notaEvalTotal + ($notaEvalCriterio * ($porcentaje / 100));
        }

        // Comprobamos si la asignacion ya ha sido entregada para saber si insertarla o actualizarla

        $result = $conn->query(sprintf("select * from alumno_asignacion a where a.cod_alumno = \"%s\" and a.cod_asignacion = \"%s\"", $alumno, $codAsignacion));

        if ($result->num_rows > 0) {
            $sql = sprintf("update alumno_asignacion a set a.comentario_profesor = \"%s\", a.nota_evaluacion = %d
where a.cod_alumno = \"%s\" and a.cod_asignacion = \"%s\";", $comentario, (int)$notaEvalTotal, $alumno, $codAsignacion);
        }

        if ($conn->query($sql) === TRUE) {

        } else {
            http_response_code(500);
        }
    } else {
        http_response_code(500);
    }


} else {
    http_response_code(400);
}

$conn->close();