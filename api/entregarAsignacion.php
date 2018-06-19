<?php
require 'conect.php';
require 'utils.php';

if (isset($match['params']['asignacion']) && isset($_POST['alumno']) && isset($_POST['clave']) && isset($_POST['rutaArchivo']) && isset($_POST['comentario'])) {
    $alumno = $_POST['alumno'];
    $clave = $_POST['clave'];
    $codAsignacion = $match['params']['asignacion'];
    $rutaArchivo = $_POST['rutaArchivo'];
    $comentario = $_POST['comentario'];

    // Comprobamos que es un alumno existente

    if(comprobarCredencialesAlumno($alumno, $clave, $conn)){

        // Calculamos la nota notal
        $sql = sprintf("select alcri.nota_autoevaluacion, cri.porcentaje from alumno_criterio alcri
join criterio_evaluacion cri on alcri.cod_criterio = cri.cod_criterio and alcri.cod_asignacion = cri.cod_asignacion
where alcri.cod_asignacion = %d and alcri.cod_alumno = \"%s\";", $codAsignacion, $alumno);

        $result = $conn->query($sql);

        if($result->num_rows > 0){
            $notaAutoTotal = 0;

            while($row = $result->fetch_assoc()){
                $notaAutoCriterio = (int)$row["nota_autoevaluacion"];
                $porcentaje = (int)$row["porcentaje"];

                $notaAutoTotal = $notaAutoTotal + ($notaAutoCriterio * ($porcentaje / 100) );
            }

            $sql = sprintf("insert into alumno_asignacion (cod_alumno, cod_asignacion, ruta_archivo, comentario, nota_autoevaluacion) values (\"%s\", %d, \"%s\", \"%s\", %d);", $alumno, $codAsignacion, $rutaArchivo, $comentario, (int)$notaAutoTotal);

            if ($conn->query($sql) === TRUE) {

            } else {
                http_response_code(500);
            }
        }else{
            http_response_code(500);
        }
    } else {
        http_response_code(401);
    }

}else{
    http_response_code(400);
}

$conn->close();