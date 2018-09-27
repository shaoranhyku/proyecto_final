<?php
require 'conect.php';
require 'models/tema.php';
require 'models/enlace.php';

if (isset($match['params']['asignatura']) && isset($match['params']['tema'])) {
    $codAsignatura = $match['params']['asignatura'];
    $codTema = $match['params']['tema'];

    $datosTemaQuery = sprintf("select t.cod_asignatura, t.cod_temario, t.nombre, t.descripcion, t.fecha_comienzo from tema t where t.cod_asignatura = \"%s\" and t.cod_temario=\"%s\";", $codAsignatura, $codTema);
    $datosEnlacesQuery = sprintf("select e.cod_enlace, e.url, e.descripcion from enlace e where e.cod_asignatura = \"%s\" and e.cod_temario = \"%s\";", $codAsignatura, $codTema);


    $result = $conn->query($datosTemaQuery);

    // Si existen resultados se envia, en caso contrario se pone un mensaje de error
    if ($result->num_rows > 0) {

        $row = $result->fetch_assoc();

        $nuevoTema = new tema();

        $nuevoTema->codigoAsignatura = $row["cod_asignatura"];
        $nuevoTema->codigoTema = $row["cod_temario"];
        $nuevoTema->nombreTema = $row["nombre"];
        $nuevoTema->descripcion = $row["descripcion"];
        $nuevoTema->fechaComienzo = $row["fecha_comienzo"];

        // Se recuperan los enlaces de la BD
        $result = $conn->query($datosEnlacesQuery);

        $enlaces = array();

        while ($row = $result->fetch_assoc()) {
            $enlace = new enlace();

            $enlace->codigoEnlace = $row["cod_enlace"];
            $enlace->url = $row["url"];
            $enlace->descripcion = $row["descripcion"];

            array_push($enlaces, $enlace);
        }

        $nuevoTema->enlaces = $enlaces;

        echo json_encode($nuevoTema);

    } else {
        http_response_code(401);
    }

} else {
    http_response_code(400);
}

$conn->close();
