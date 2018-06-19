<?php
require 'conect.php';
require 'utils.php';

if (isset($match['params']['asignacion']) && isset($match['params']['criterio']) && isset($_POST['alumno']) && isset($_POST['clave']) && isset($_POST['notaAuto'])) {
    $alumno = $_POST['alumno'];
    $clave = $_POST['clave'];
    $codAsignacion = $match['params']['asignacion'];
    $codCriterio = $match['params']['criterio'];
    $notaAuto = $_POST['notaAuto'];

    // Comprobamos que es un alumno existente

    if(comprobarCredencialesAlumno($alumno, $clave, $conn)){

        // Comprobamos si existe el criterio para saber si hay que insertarlo o actualizarlo

        $result = $conn->query(sprintf("select * from alumno_criterio a where a.cod_alumno = \"%s\" and a.cod_asignacion = \"%s\" and a.cod_criterio = \"%s\";", $alumno, $codAsignacion, $codCriterio));

        if($result->num_rows > 0){
            $sql = sprintf("update alumno_criterio a set a.nota_autoevaluacion = %d
where a.cod_alumno = \"%s\" and a.cod_asignacion = %d and a.cod_criterio = %d;",$notaAuto, $alumno, $codAsignacion, $codCriterio);
        }else{
            $sql = sprintf("insert into alumno_criterio (cod_alumno, cod_asignacion, cod_criterio, nota_autoevaluacion) values (\"%s\", %d, %d, %d);", $alumno, $codAsignacion, $codCriterio, $notaAuto);
        }

        if (!$conn->query($sql) === TRUE) {
            http_response_code(401);
            die();
        }

    } else {
        http_response_code(401);
    }

}else{
    http_response_code(400);
}

$conn->close();