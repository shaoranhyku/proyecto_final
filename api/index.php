<?php
require 'AltoRouter.php';

$router = new AltoRouter();

// map homepage
try {
    $router->map('POST', '/api/login/', 'endpoints/login.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignaturas/[a:alumno]/', 'endpoints/alumno/obtenerAsignaturasAlumno.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/ultimasAsignaciones/[a:alumno]/', 'endpoints/alumno/obtenerUltimasAsignacionesAlumno.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignaturaAlumno/[a:asignatura]/[a:alumno]/', 'endpoints/alumno/obtenerAsignaturaAlumno.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/temaAlumno/[a:asignatura]/[:tema]/[a:alumno]/', 'endpoints/alumno/obtenerTemaAlumno.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignacionAlumno/[a:asignacion]/[a:alumno]/', 'endpoints/alumno/obtenerAsignacionAlumno.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/entregarAsignacion/[a:asignacion]/', 'endpoints/alumno/entregarAsignacion.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/entregarCriterio/[a:asignacion]/[a:criterio]/', 'endpoints/alumno/entregarCriterio.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignacionesDia/[:fecha]/[a:alumno]/', 'endpoints/alumno/obtenerAsignacionesDiaAlumno.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/numAsignacionesDia/[:fecha]/[a:alumno]/', 'endpoints/alumno/obtenerNumeroAsignacionesDiaAlumno.php');
} catch (Exception $e){}

// EndPoints Profesor

try {
    $router->map('GET', '/api/asignaturasProfesor/[a:profesor]/', 'endpoints/profesor/obtenerAsignaturasProfesor.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/alumnos/', 'endpoints/profesor/obtenerAlumnos.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/alumnos/[a:asignatura]/', 'endpoints/profesor/obtenerAlumnosPorAsignatura.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/alumnos/[a:asignatura]/[a:alumno]/', 'endpoints/profesor/agregarAlumnoAsignatura.php');
} catch (Exception $e){}

try {
    $router->map('DELETE', '/api/alumnos/[a:asignatura]/[a:alumno]/', 'endpoints/profesor/eliminarAlumnoAsignatura.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/temas/[a:asignatura]/', 'endpoints/profesor/obtenerTemasAsignatura.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/temas/[a:asignatura]/', 'endpoints/profesor/crearTema.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/temas/[a:asignatura]/[:tema]/', 'endpoints/profesor/crearEnlace.php');
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