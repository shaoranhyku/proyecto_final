<?php
require 'conect.php';
require 'responses/asignacionProfesorResponse.php';
require 'models/itemListAsignacion.php';
require 'models/criterio.php';
require 'models/tema.php';

if (isset($match['params']['asignacion'])) {
    $codAsignacion = $match['params']['asignacion'];

    $datosAsignacionQuery = sprintf("select asig.cod_asignacion, asig.nombre, asig.nombre_git, asig.descripcion, asig.fecha_entrega, asig2.nombre nombre_asignatura from asignacion asig
join asignacion_tema asigt on asig.cod_asignacion = asigt.cod_asignacion
join tema t on asigt.cod_asignatura = t.cod_asignatura and asigt.cod_temario = t.cod_temario
join asignatura asig2 on t.cod_asignatura = asig2.cod_asig
where asig.cod_asignacion = \"%s\";", $codAsignacion);

    $datosCriteriosQuery = sprintf("select cri.cod_asignacion, cri.cod_criterio, cri.nombre, cri.porcentaje from criterio_evaluacion cri
where cri.cod_asignacion = \"%s\";", $codAsignacion);

    $datosTemasAsignadosQuery = sprintf("SELECT t.cod_temario, t.nombre FROM tema t
join asignacion_tema asigt on t.cod_temario = asigt.cod_temario
where asigt.cod_asignacion = \"%s\";", $codAsignacion);

    $result = $conn->query($datosAsignacionQuery);

    // Si existen resultados se envia, en caso contrario se pone un mensaje de error
    if ($result->num_rows > 0) {

        $asignacionResponse = new AsignacionProfesorResponse();
        $asignacionResponse->criterios = array();
        $asignacionResponse->temas = array();

        $row = $result->fetch_assoc();
        $asignacion = new itemListAsignacion();

        $asignacion->codigoAsignacion = $row["cod_asignacion"];
        $asignacion->nombreAsignacion = $row["nombre"];
        $asignacion->nombreGit = $row["nombre_git"];
        $asignacion->descripcion = $row["descripcion"];
        $asignacion->fechaEntrega = $row["fecha_entrega"];
        $asignacion->asignatura = $row["nombre_asignatura"];

        $asignacionResponse->asignacion = $asignacion;

        $result = $conn->query($datosCriteriosQuery);

        while ($row = $result->fetch_assoc()) {
            $criterio = new criterio();

            $criterio->codAsig = $row["cod_asignacion"];
            $criterio->codCriterio = $row["cod_criterio"];
            $criterio->nombre = $row["nombre"];
            $criterio->porcentaje = $row["porcentaje"];

            array_push($asignacionResponse->criterios, $criterio);
        }

        $result = $conn->query($datosTemasAsignadosQuery);

        while ($row = $result->fetch_assoc()) {
            $tema = new tema();

            $tema->codigoTema = $row["cod_temario"];
            $tema->nombreTema = $row["nombre"];

            array_push($asignacionResponse->temas, $tema);
        }

        echo json_encode($asignacionResponse);

    } else {
        http_response_code(401);
    }

} else {
    http_response_code(400);
}

$conn->close();
