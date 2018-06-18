<?php
require 'AltoRouter.php';

$router = new AltoRouter();

// map homepage
try {
    $router->map('POST', '/api/login/', 'login.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignaturas/[a:alumno]/', 'obtenerAsignaturasAlumno.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/ultimasAsignaciones/[a:alumno]/', 'obtenerUltimasAsignacionesAlumno.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignaturaAlumno/[a:asignatura]/[a:alumno]/', 'obtenerAsignaturaAlumno.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/temaAlumno/[a:asignatura]/[a:tema]/[a:alumno]/', 'obtenerTemaAlumno.php');
} catch (Exception $e){}

/* Match the current request */
$match = $router->match();
if($match) {
    require $match['target'];
}
else {
    header("HTTP/1.0 404 Not Found");
    echo 'error';
}