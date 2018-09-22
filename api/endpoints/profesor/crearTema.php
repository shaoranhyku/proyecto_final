<?php
require 'conect.php';

if (isset($_POST['asignatura']) && isset($_POST['codTemario']) && isset($_POST['nombre']) && isset($_POST['descripcion']) && isset($_POST['fechaComienzo'])) {
    $asignatura = $_POST['asignatura'];
    $codTemario = $_POST['codTemario'];
    $nombre = $_POST['nombre'];
    $descripcion = $_POST['descripcion'];
    $fechaComienzo = $_POST['fechaComienzo'];

    $comprobarExistenciaQuery = sprintf("SELECT * FROM tema t where t.cod_asignatura = \"%s\" and t.cod_temario=\"%s\";", $asignatura, $codTemario);

    $result = $conn->query($comprobarExistenciaQuery);

    if($result->num_rows > 0){
        http_response_code(400);
        die();
    }else{
        $agregarTemaQuery = sprintf("INSERT INTO tema (cod_asignatura, cod_temario, nombre, descripcion, fecha_comienzo) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\");
", $asignatura, $codTemario, $nombre, $descripcion, $fechaComienzo);

        if (!$conn->query($agregarTemaQuery) === TRUE) {
            http_response_code(401);
            die();
        }

    }

} else {
    http_response_code(400);
}

$conn->close();
