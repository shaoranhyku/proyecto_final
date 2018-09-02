<?php
require 'conect.php';
require 'models/itemListAsignacion.php';

if(isset($match['params']['alumno']) && isset($match['params']['fecha']) ){
    $alumno = $match['params']['alumno'];
    $fecha = $match['params']['fecha'];

    $alumnoQuery = sprintf("select count(*) numAsig from asignacion asig
join asignacion_tema ast on asig.cod_asignacion = ast.cod_asignacion
join tema t on ast.cod_temario = t.cod_temario
join asignatura asig2 on t.cod_asignatura = asig2.cod_asig
join alumno_asignatura almasig2 on asig2.cod_asig = almasig2.cod_asignatura
join alumno alum on almasig2.cod_alumno = alum.nombre_login
where alum.nombre_login = \"%s\" and asig.fecha_entrega like \"%s%%\" order by fecha_entrega;", $alumno, $fecha);

    $result = $conn->query($alumnoQuery);

    // Si existen resultados se envia, en caso contrario se pone un mensaje de error
    if ($result->num_rows > 0) {

        $asignaciones = array();

        $row = $result->fetch_assoc();

        echo (int)$row["numAsig"];

    }else{
        echo json_encode(array());
    }

}else{
    http_response_code(400);
}

$conn->close();
