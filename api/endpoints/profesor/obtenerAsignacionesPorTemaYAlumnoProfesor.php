<?php
require 'conect.php';
require 'models/itemListAsignacion.php';

if (isset($match['params']['asignatura']) && isset($match['params']['tema']) && isset($match['params']['alumno'])) {
    $codAsignatura = $match['params']['asignatura'];
    $tema = $match['params']['tema'];
    $alumno = $match['params']['alumno'];

    $datosAsignacionQuery = sprintf("select asig.cod_asignacion, asig.nombre, asig.nombre_git, asig.descripcion, asig.fecha_entrega, asig2.nombre nombre_asignatura from asignacion asig
join asignacion_tema asigt on asig.cod_asignacion = asigt.cod_asignacion
join tema t on asigt.cod_asignatura = t.cod_asignatura and asigt.cod_temario = t.cod_temario
join asignatura asig2 on t.cod_asignatura = asig2.cod_asig
where asig2.cod_asig = \"%s\" and t.cod_temario =\"%s\";", $codAsignatura, $tema);
    $datosAsignacionAlumnoQuery = "select * from alumno_asignacion alasig where alasig.cod_alumno = \"%s\" and alasig.cod_asignacion = %d;";

    $result = $conn->query($datosAsignacionQuery);

    // Si existen resultados se envia, en caso contrario se pone un mensaje de error
    if ($result->num_rows > 0) {

        $asignaciones = array();

        while($row = $result->fetch_assoc()){
            $asignacion = new itemListAsignacion();

            $asignacion->codigoAsignacion = $row["cod_asignacion"];
            $asignacion->nombreAsignacion = $row["nombre"];
            $asignacion->nombreGit = $row["nombre_git"];
            $asignacion->descripcion = $row["descripcion"];
            $asignacion->fechaEntrega = $row["fecha_entrega"];
            $asignacion->asignatura = $row["nombre_asignatura"];

            $result2 = $conn->query(sprintf($datosAsignacionAlumnoQuery, $alumno, $row["cod_asignacion"]));

            if($result2->num_rows > 0){
                $asignacion->entregado = true;
            }else{
                $asignacion->entregado = false;
            }

            array_push($asignaciones, $asignacion);
        }

        echo json_encode($asignaciones);

    } else {
        http_response_code(401);
    }

} else {
    http_response_code(400);
}

$conn->close();
