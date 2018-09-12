<?php
require 'conect.php';

if (isset($match['params']['asignatura']) && isset($match['params']['alumno'])) {
    $asignatura = $match['params']['asignatura'];
    $alumno = $match['params']['alumno'];

    $profesorQuery = sprintf("SELECT * FROM alumno_asignatura als where als.cod_alumno = \"%s\" and als.cod_asignatura = \"%s\";", $alumno, $asignatura);

    $result = $conn->query($profesorQuery);

    if($result->num_rows > 0){
        $profesorQuery = sprintf("DELETE FROM alumno_asignatura WHERE cod_alumno=\"%s\" and cod_asignatura=\"%s\";", $alumno, $asignatura);

        if (!$conn->query($profesorQuery) === TRUE) {
            http_response_code(401);
            die();
        }
    }else{
        http_response_code(400);
        echo "Aqui";
        die();
    }

} else {
    echo "Aqui";
    http_response_code(400);
}

$conn->close();


