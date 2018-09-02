<?php
require 'conect.php';
require 'models/itemListAlumno.php';

if (isset($match['params']['asignatura'])) {
    $asignatura = $match['params']['asignatura'];

    $profesorQuery = sprintf("select u.nombre_login, u.nombre, u.apellidos from usuario u
join alumno a on u.nombre_login = a.nombre_login
join alumno_asignatura alumasig on a.nombre_login = alumasig.cod_alumno
where alumasig.cod_asignatura = \"%s\";", $asignatura);

    $result = $conn->query($profesorQuery);

    $alumnos = array();

    while ($row = $result->fetch_assoc()) {
        $alumno = new alumno();
        $alumno->id = $row["nombre_login"];
        $alumno->nombre = $row["nombre"];
        $alumno->apellidos = $row["apellidos"];
        array_push($alumnos, $alumno);
    }

    echo json_encode($alumnos);

} else {
    http_response_code(400);
}

$conn->close();
