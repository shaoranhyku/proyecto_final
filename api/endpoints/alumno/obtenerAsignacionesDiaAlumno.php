<?php
require 'conect.php';
require 'models/itemListAsignacion.php';

if(isset($match['params']['alumno']) && isset($match['params']['fecha']) ){
    $alumno = $match['params']['alumno'];
    $fecha = $match['params']['fecha'];

    $alumnoQuery = sprintf("select asig.cod_asignacion codigo, asig.nombre nombre , asig2.nombre asignatura , asig.descripcion descripcion, asig.fecha_entrega fecha_entrega from asignacion asig
join asignacion_tema ast on asig.cod_asignacion = ast.cod_asignacion
join tema t on ast.cod_temario = t.cod_temario
join asignatura asig2 on t.cod_asignatura = asig2.cod_asig
join alumno_asignatura almasig2 on asig2.cod_asig = almasig2.cod_asignatura
join alumno alum on almasig2.cod_alumno = alum.nombre_login
where alum.nombre_login = \"%s\" and asig.fecha_entrega like \"%s%%\" order by fecha_entrega;", $alumno, $fecha);
    $datosAsignacionAlumnoQuery = "select * from alumno_asignacion alasig where alasig.cod_alumno = \"%s\" and alasig.cod_asignacion = %d;";

    $result = $conn->query($alumnoQuery);

    // Si existen resultados se envia, en caso contrario se pone un mensaje de error
    if ($result->num_rows > 0) {

        $asignaciones = array();

        while($row = $result->fetch_assoc()) {
            $asignacion = new itemListAsignacion();
            $asignacion->codigoAsignacion = $row["codigo"];
            $asignacion->nombreAsignacion = $row["nombre"];
            $asignacion->asignatura = $row["asignatura"];
            $asignacion->descripcion = $row["descripcion"];
            $asignacion->fechaEntrega = $row["fecha_entrega"];

            $result2 = $conn->query(sprintf($datosAsignacionAlumnoQuery, $alumno, $row["codigo"]));

            if($result2->num_rows > 0){
                $asignacion->entregado = true;
            }else{
                $asignacion->entregado = false;
            }

            array_push($asignaciones, $asignacion);
        }

        echo json_encode($asignaciones);

    }else{
        echo json_encode(array());
    }

}else{
    http_response_code(400);
}

$conn->close();
