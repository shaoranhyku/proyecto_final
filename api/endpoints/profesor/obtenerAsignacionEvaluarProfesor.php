<?php
require 'conect.php';
require 'responses/asignacionEvaluarProfesorResponse.php';
require 'models/itemListAsignacion.php';
require 'models/entregaAsignacion.php';
require 'models/criterioNota.php';
require 'models/criterio.php';

if (isset($match['params']['asignacion']) && isset($match['params']['alumno'])) {
    $codAsignacion = $match['params']['asignacion'];
    $codAlumno = $match['params']['alumno'];

    $datosAlumnoQuery = sprintf("SELECT u.nombre FROM usuario u where u.nombre_login=\"%s\";", $codAlumno);
    $datosAsignacionQuery = sprintf("select asig.cod_asignacion, asig.nombre, asig.nombre_git, asig.descripcion, asig.fecha_entrega, asig2.nombre nombre_asignatura from asignacion asig
join asignacion_tema asigt on asig.cod_asignacion = asigt.cod_asignacion
join tema t on asigt.cod_asignatura = t.cod_asignatura and asigt.cod_temario = t.cod_temario
join asignatura asig2 on t.cod_asignatura = asig2.cod_asig
where asig.cod_asignacion = \"%s\";", $codAsignacion);
    $datosEntregaQuery = sprintf("select alasig.comentario, alasig.nota_evaluacion, alasig.nota_autoevaluacion, alasig.ruta_archivo, alasig.comentario_profesor from alumno_asignacion alasig
where alasig.cod_asignacion = %d and alasig.cod_alumno = \"%s\";" , $codAsignacion, $codAlumno);
    $datosCriteriosQuery = sprintf("select cri.cod_asignacion, cri.cod_criterio, cri.nombre, cri.porcentaje from criterio_evaluacion cri
where cri.cod_asignacion = \"%s\";", $codAsignacion);
    $datosCriterioNotaQuery = "select a.nota_evaluacion, a.nota_autoevaluacion from alumno_criterio a
where a.cod_alumno = \"%s\" and a.cod_asignacion = %d and a.cod_criterio = %d;";

    $result = $conn->query($datosAsignacionQuery);

    // Si existen resultados se envia, en caso contrario se pone un mensaje de error
    if ($result->num_rows > 0) {

        $row = $result->fetch_assoc();

        $asignacionEvaluarProfesorResponse = new asignacionEvaluarProfesorResponse();

        $asignacion = new itemListAsignacion();

        $asignacion->codigoAsignacion = $row["cod_asignacion"];
        $asignacion->nombreAsignacion = $row["nombre"];
        $asignacion->nombreGit = $row["nombre_git"];
        $asignacion->descripcion = $row["descripcion"];
        $asignacion->fechaEntrega = $row["fecha_entrega"];
        $asignacion->asignatura = $row["nombre_asignatura"];

        $asignacionEvaluarProfesorResponse->asignacion = $asignacion;

        $result = $conn->query($datosAlumnoQuery);

        // Obtenemos el nombre del alumno
        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();

            $asignacionEvaluarProfesorResponse->nombreAlumno = $row['nombre'];
        }

        $result = $conn->query($datosEntregaQuery);

        // Si existen resultados, el alumno ha entregado la asignación
        if ($result->num_rows > 0) {

            $row = $result->fetch_assoc();

            $entregaAsignacion = new entregaAsignacion();

            $entregaAsignacion->comentario = $row['comentario'];
            $entregaAsignacion->comentarioProfesor = $row['comentario_profesor'];

            // Si alguna nota no ha sido puesta, se devolvera -1 como valor para que el cliente sepa que no tiene
            // valor asignado. Si dejamos el campo vacio, el cliente le pondra 0 automaticamente al decodificar el JSON
            // y no podremos saber si su valor era 0 o vacio

            $notaEval = $row['nota_evaluacion'];
            if ($notaEval == ""){
                $notaEval = -1;
            }
            $entregaAsignacion->notaEval = $notaEval;

            $notaAuto = $row['nota_autoevaluacion'];
            if ($notaAuto == ""){
                $notaAuto = -1;
            }
            $entregaAsignacion->notaAuto = $notaAuto;

            $entregaAsignacion->rutaArchivo = $row['ruta_archivo'];

            $asignacionEvaluarProfesorResponse->entrega = $entregaAsignacion;
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

                    $notaEval = $row2['nota_evaluacion'];
                    if ($notaEval == ""){
                        $notaEval = -1;
                    }
                    $criterioNota->notaEval = $notaEval;

                    $notaAuto = $row2['nota_autoevaluacion'];
                    if ($notaAuto == ""){
                        $notaAuto = -1;
                    }
                    $criterioNota->notaAuto = $notaAuto;

                }else{
                    // Pongo -1 de valor para que no de problemas con los null y detectar que no ha sido puntuado

                    $criterioNota->notaEval = -1;
                    $criterioNota->notaAuto = -1;
                }

                array_push($criteriosNota, $criterioNota);
            }

            $asignacionEvaluarProfesorResponse->criteriosNota = $criteriosNota;

        }

        echo json_encode($asignacionEvaluarProfesorResponse);

    } else {
        http_response_code(401);
    }

} else {
    http_response_code(400);
}

$conn->close();
