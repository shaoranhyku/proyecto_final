<?php
require 'conect.php';
require 'responses/asignacionAlumnoResponse.php';
require 'models/itemListAsignacion.php';
require 'models/entregaAsignacion.php';
require 'models/criterioNota.php';
require 'models/criterio.php';

if (isset($match['params']['asignacion']) && isset($match['params']['alumno'])) {
    $codAsignacion = $match['params']['asignacion'];
    $codAlumno = $match['params']['alumno'];

    $datosAsignacionQuery = sprintf("select asig.cod_asignacion, asig.nombre, asig.nombre_git, asig.descripcion, asig.fecha_entrega, asig2.nombre nombre_asignatura from asignacion asig
join asignacion_tema asigt on asig.cod_asignacion = asigt.cod_asignacion
join tema t on asigt.cod_asignatura = t.cod_asignatura and asigt.cod_temario = t.cod_temario
join asignatura asig2 on t.cod_asignatura = asig2.cod_asig
where asig.cod_asignacion = \"%s\";", $codAsignacion);
    $datosEntregaQuery = sprintf("select alasig.comentario, alasig.nota_evaluacion, alasig.nota_autoevaluacion, alasig.ruta_archivo from alumno_asignacion alasig
where alasig.cod_asignacion = %d and alasig.cod_alumno = \"%s\";" , $codAsignacion, $codAlumno);
    $datosCriteriosQuery = sprintf("select cri.cod_asignacion, cri.cod_criterio, cri.nombre, cri.porcentaje from criterio_evaluacion cri
where cri.cod_asignacion = \"%s\";", $codAsignacion);
    $datosCriterioNotaQuery = "select a.nota_evaluacion, a.nota_autoevaluacion from alumno_criterio a
where a.cod_alumno = \"%s\" and a.cod_asignacion = %d and a.cod_criterio = %d;";

    $result = $conn->query($datosAsignacionQuery);

    // Si existen resultados se envia, en caso contrario se pone un mensaje de error
    if ($result->num_rows > 0) {

        $row = $result->fetch_assoc();

        $asignacionAlumnoResponse = new asignacionAlumnoResponse();

        $asignacion = new itemListAsignacion();

        $asignacion->codigoAsignacion = $row["cod_asignacion"];
        $asignacion->nombreAsignacion = $row["nombre"];
        $asignacion->nombreGit = $row["nombre_git"];
        $asignacion->descripcion = $row["descripcion"];
        $asignacion->fechaEntrega = $row["fecha_entrega"];
        $asignacion->asignatura = $row["nombre_asignatura"];

        $asignacionAlumnoResponse->asignacion = $asignacion;

        $result = $conn->query($datosEntregaQuery);

        // Si existen resultados, el alumno ha entregado la asignación
        if ($result->num_rows > 0) {

            $row = $result->fetch_assoc();

            $entregaAsignacion = new entregaAsignacion();

            $entregaAsignacion->comentario = $row['comentario'];
            $entregaAsignacion->notaEval = $row['nota_evaluacion'];
            $entregaAsignacion->notaAuto = $row['nota_autoevaluacion'];
            $entregaAsignacion->rutaArchivo = $row['ruta_archivo'];

            $asignacionAlumnoResponse->entrega = $entregaAsignacion;
        }

        $result = $conn->query($datosCriteriosQuery);

        if ($result->num_rows > 0) {

            $criteriosNota = array();

            while($row = $result->fetch_assoc()){

                $criterioNota = new criterioNota();
                $criterio = new criterio();

                $criterio->codAsig = $row["cod_asignacion"];
                $criterio->codCriterio = $row["cod_criterio"];
                $criterio->nombre = $row["nombre"];
                $criterio->porcentaje = $row["porcentaje"];

                $criterioNota->criterio = $criterio;

                // Recuperamos los datos de las notas puestas a este criterio si las hay

                $result2 = $conn->query(sprintf($datosCriterioNotaQuery, $codAlumno, $codAsignacion, $row["cod_criterio"]));

                if ($result2->num_rows > 0) {

                    $row2 = $result2->fetch_assoc();

                    $criterioNota->notaEval = $row2["nota_evaluacion"];
                    $criterioNota->notaAuto = $row2["nota_autoevaluacion"];

                }else{
                    // Pongo -1 de valor para que no de problemas con los null y detectar que no ha sido puntuado

                    $criterioNota->notaEval = -1;
                    $criterioNota->notaAuto = -1;
                }

                array_push($criteriosNota, $criterioNota);
            }

            $asignacionAlumnoResponse->criteriosNota = $criteriosNota;

        }

        echo json_encode($asignacionAlumnoResponse);

    } else {
        http_response_code(401);
    }

} else {
    http_response_code(400);
}

$conn->close();
