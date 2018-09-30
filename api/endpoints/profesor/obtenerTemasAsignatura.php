<?php
require 'conect.php';
require 'models/tema.php';

if (isset($match['params']['asignatura'])) {
    $asignatura = $match['params']['asignatura'];

    $profesorQuery = sprintf("SELECT t.cod_asignatura, t.cod_temario, t.nombre FROM tema t where t.cod_asignatura = \"%s\";;", $asignatura);

    $result = $conn->query($profesorQuery);

    $temas = array();

    while ($row = $result->fetch_assoc()) {
        $nuevoTema = new tema();

        $nuevoTema->codigoAsignatura = $row["cod_asignatura"];
        $nuevoTema->codigoTema = $row["cod_temario"];
        $nuevoTema->nombreTema = $row["nombre"];
        array_push($temas, $nuevoTema);
    }

    echo json_encode($temas);

} else {
    http_response_code(400);
}

$conn->close();
