<?php
require 'conect.php';
require 'responses/temaAlumnoResponse.php';
require 'models/tema.php';
require 'models/enlace.php';
require 'models/itemListAsignacionAlumno.php';

if (isset($match['params']['asignatura']) && isset($match['params']['tema']) && isset($match['params']['alumno'])) {
    $codAsignatura = $match['params']['asignatura'];
    $codTema = $match['params']['tema'];
    $alumno = $match['params']['alumno'];

    $datosTemaQuery = sprintf("select t.cod_asignatura, t.cod_temario, t.nombre, t.descripcion, t.fecha_comienzo from tema t where t.cod_asignatura = \"%s\" and t.cod_temario=\"%s\";", $codAsignatura, $codTema);
    $datosEnlacesQuery = sprintf("select e.cod_enlace, e.url, e.descripcion from enlace e where e.cod_asignatura = \"%s\" and e.cod_temario = \"%s\";", $codAsignatura, $codTema);
    $datosSubtemasQuery = sprintf("select t.cod_asignatura, t.cod_temario, t.nombre, t.descripcion, t.fecha_comienzo from tema t where t.cod_temario like \"%s.%%\";", $codTema);
    $datosAsignacionesQuery = sprintf("select asig.cod_asignacion, asig.nombre, asig2.nombre nombre_asignatura, asig.descripcion, asig.fecha_entrega from asignacion asig
join asignacion_tema asigt on asig.cod_asignacion = asigt.cod_asignacion
join tema t on asigt.cod_temario = t.cod_temario and asigt.cod_asignatura = t.cod_asignatura
join asignatura asig2 on asig2.cod_asig = t.cod_asignatura
where t.cod_asignatura = \"%s\" and t.cod_temario = \"%s\" order by asig.fecha_creacion desc;", $codAsignatura, $codTema);
    $datosAsignacionAlumnoQuery = "select * from alumno_asignacion alasig where alasig.cod_alumno = \"%s\" and alasig.cod_asignacion = %d;";


    $result = $conn->query($datosTemaQuery);

    // Si existen resultados se envia, en caso contrario se pone un mensaje de error
    if ($result->num_rows > 0) {

        $row = $result->fetch_assoc();

        $temaAlumnoResponse = new temaAlumnoResponse();

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

        $temaAlumnoResponse->tema = $nuevoTema;

        // Se recuperan los subtemas de la BD

        $result = $conn->query($datosSubtemasQuery);

        $subtemas = array();

        while ($row = $result->fetch_assoc()) {
            $subtema = new tema();

            $subtema->codigoAsignatura = $row["cod_asignatura"];
            $subtema->codigoTema = $row["cod_temario"];
            $subtema->nombreTema = $row["nombre"];
            $subtema->descripcion = $row["descripcion"];
            $subtema->fechaComienzo = $row["fecha_comienzo"];

            array_push($subtemas, $subtema);
        }

        $temaAlumnoResponse->subtemas = $subtemas;

        // Se recuperan las asignaciones de la BD

        $result = $conn->query($datosAsignacionesQuery);

        $asignaciones = array();

        while ($row = $result->fetch_assoc()) {
            $asignacion = new itemListAsignacionAlumno();

            $asignacion->codigoAsignacion = $row["cod_asignacion"];
            $asignacion->nombreAsignacion = $row["nombre"];
            $asignacion->asignatura = $row["nombre_asignatura"];
            $asignacion->descripcion = $row["descripcion"];
            $asignacion->fechaEntrega = $row["fecha_entrega"];

            $result2 = $conn->query(sprintf($datosAsignacionAlumnoQuery, $alumno, $row["cod_asignacion"]));

            if ($result2->num_rows > 0) {
                $asignacion->entregado = true;
            } else {
                $asignacion->entregado = false;
            }

            array_push($asignaciones, $asignacion);
        }

        $temaAlumnoResponse->asignaciones = $asignaciones;

        echo json_encode($temaAlumnoResponse);

    } else {
        http_response_code(401);
    }

} else {
    http_response_code(400);
}

$conn->close();
