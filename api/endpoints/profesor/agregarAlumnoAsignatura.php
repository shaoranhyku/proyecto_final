<?php
require 'conect.php';

if (isset($match['params']['asignatura']) && isset($match['params']['alumno'])) {
    $asignatura = $match['params']['asignatura'];
    $alumno = $match['params']['alumno'];

    $profesorQuery = sprintf("SELECT * FROM alumno_asignatura als where als.cod_alumno = \"%s\" and als.cod_asignatura = \"%s\";", $alumno, $asignatura);

    $result = $conn->query($profesorQuery);

    if($result->num_rows > 0){
        http_response_code(400);
        die();
    }else{
        $profesorQuery = sprintf("INSERT INTO alumno_asignatura (cod_alumno, cod_asignatura) VALUES (\"%s\", \"%s\");", $alumno, $asignatura);

        if (!$conn->query($profesorQuery) === TRUE) {
            http_response_code(401);
            die();
        }

    }

} else {
    http_response_code(400);
}

$conn->close();
