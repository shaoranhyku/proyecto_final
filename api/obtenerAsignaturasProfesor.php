<?php
require 'conect.php';
require 'models/itemListAsignatura.php';

if(isset($match['params']['profesor'])){
    $profesor = $match['params']['profesor'];

    $profesorQuery = sprintf("select s.cod_asig codigo, s.nombre nombre from profesor_asignatura profasig join profesor p on profasig.nombre_login = p.nombre_login join asignatura s on profasig.cod_asig = s.cod_asig where profasig.nombre_login = \"%s\";;", $profesor);

    $result = $conn->query($profesorQuery);

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
