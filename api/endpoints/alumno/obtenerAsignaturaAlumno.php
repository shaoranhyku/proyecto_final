<?php
require 'conect.php';
require 'responses/asignaturaResponse.php';
require 'models/tema.php';
require 'models/itemListAsignacionAlumno.php';

if (isset($match['params']['asignatura']) && isset($match['params']['alumno'])) {
    $codAsignatura = $match['params']['asignatura'];
    $alumno = $match['params']['alumno'];

    $datosAsignaturaQuery = sprintf("select a.cod_asig, a.nombre from asignatura a where a.cod_asig = \"%s\";", $codAsignatura);
    $datosTemaQuery = sprintf("select t.cod_asignatura, t.cod_temario, t.nombre, t.descripcion, t.fecha_comienzo from tema t where t.cod_asignatura = \"%s\";", $codAsignatura);
    $datosAsignacionQuery = sprintf("select asig.cod_asignacion, asig.nombre, asig.descripcion, asig.fecha_entrega from asignacion asig
join asignacion_tema asigt on asig.cod_asignacion = asigt.cod_asignacion
where asigt.cod_asignatura = \"%s\" order by asig.fecha_creacion desc;", $codAsignatura);
    $datosAsignacionAlumnoQuery = "select * from alumno_asignacion alasig where alasig.cod_alumno = \"%s\" and alasig.cod_asignacion = %d;";

    $result = $conn->query($datosAsignaturaQuery);

    // Si existen resultados se envia, en caso contrario se pone un mensaje de error
    if ($result->num_rows > 0) {

        $row = $result->fetch_assoc();

        $asignaturaResponse = new asignaturaResponse();

        $asignaturaResponse->codigoAsignatura = $row["cod_asig"];
        $asignaturaResponse->nombreAsignatura = $row["nombre"];

        // Se recuperan los temas de la BD
        $result = $conn->query($datosTemaQuery);

        $temas = array();

        while($row = $result->fetch_assoc()){
            $tema = new tema();

            $tema->codigoAsignatura = $codAsignatura;
            $tema->codigoTema = $row["cod_temario"];
            $tema->nombreTema = $row["nombre"];
            $tema->descripcion = $row["descripcion"];
            $tema->fechaComienzo = $row["fecha_comienzo"];

            array_push($temas, $tema);
        }

        $asignaturaResponse->temas = $temas;

        // Se recuperan las asignaciones de la BD

        $result = $conn->query($datosAsignacionQuery);

        $asignaciones = array();

        while($row = $result->fetch_assoc()){
            $asignacion = new itemListAsignacionAlumno();

            $asignacion->codigoAsignacion = $row["cod_asignacion"];
            $asignacion->nombreAsignacion = $row["nombre"];
            $asignacion->asignatura = $asignaturaResponse->nombreAsignatura;
            $asignacion->descripcion = $row["descripcion"];
            $asignacion->fechaEntrega = $row["fecha_entrega"];

            $result2 = $conn->query(sprintf($datosAsignacionAlumnoQuery, $alumno, $row["cod_asignacion"]));

            if($result2->num_rows > 0){
                $asignacion->entregado = true;
            }else{
                $asignacion->entregado = false;
            }

            array_push($asignaciones, $asignacion);
        }

        $asignaturaResponse->asignaciones = $asignaciones;

        echo json_encode($asignaturaResponse);

    } else {
        http_response_code(401);
    }

} else {
    http_response_code(400);
}

$conn->close();
