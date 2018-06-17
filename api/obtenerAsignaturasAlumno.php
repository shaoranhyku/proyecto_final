<?php
require 'conect.php';
require 'models/itemListAsignatura.php';

if(isset($match['params']['alumno'])){
    $alumno = $match['params']['alumno'];

    $alumnoQuery = sprintf("select s.cod_asig codigo, s.nombre nombre from alumno_asignatura als join alumno a on als.cod_alumno = a.nombre_login join asignatura s on als.cod_asignatura = s.cod_asig where als.cod_alumno = \"%s\";", $alumno);

    $result = $conn->query($alumnoQuery);

    // Si existen resultados se envia, en caso contrario se pone un mensaje de error
    if ($result->num_rows > 0) {

        $asignaturas = array();

        while($row = $result->fetch_assoc()) {
            $asignatura = new itemListAsignatura();
            $asignatura->codigoAsignatura = $row["codigo"];
            $asignatura->nombreAsignatura = $row["nombre"];
            array_push($asignaturas, $asignatura);
        }

        echo json_encode($asignaturas);

    }else{
        http_response_code(401);
    }

}else{
    http_response_code(400);
}

$conn->close();
