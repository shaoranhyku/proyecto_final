<?php

function comprobarCredencialesAlumno($usuario, $clave, $conexion){
    $usuarioQuery = sprintf("SELECT * FROM usuario WHERE nombre_login = \"%s\" AND clave = \"%s\";", $usuario, $clave);
    $alumnoQuery = sprintf("SELECT * FROM alumno WHERE nombre_login = \"%s\";", $usuario);

    $result = $conexion->query($usuarioQuery);

    // Si existe en la tabla usuario existe el usuario y se comprueba que tipo de usuario es
    if ($result->num_rows > 0) {

        $result = $conexion->query($alumnoQuery);

        // Si existe en la tabla usuario existe el usuario y se comprueba que tipo de usuario es
        if ($result->num_rows > 0) {
            return true;
        }else{
            return false;
        }
    }else{
        return false;
    }
}