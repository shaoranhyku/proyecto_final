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

try {
    $router->map('DELETE', '/api/temas/[a:asignatura]/[:tema]/', 'endpoints/profesor/borrarTema.php');
} catch (Exception $e){}

try {
    $router->map('DELETE', '/api/temas/[a:asignatura]/[:tema]/[i:enlace]/', 'endpoints/profesor/borrarEnlace.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/temas/[a:asignatura]/[:tema]/', 'endpoints/profesor/obtenerTemaProfesor.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/temas/[a:asignatura]/[:tema]/editar/', 'endpoints/profesor/editarTema.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/asignaciones/', 'endpoints/profesor/crearAsignacion.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/asignacion/[a:asignacion]/tema/', 'endpoints/profesor/asignarTemaAsignacion.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/asignacion/[a:asignacion]/criterio/', 'endpoints/profesor/crearCriterio.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignaciones/[a:asignatura]/', 'endpoints/profesor/obtenerAsignacionesProfesor.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignaciones/[a:asignatura]/[:tema]/', 'endpoints/profesor/obtenerAsignacionesPorTemaProfesor.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignacion/[a:asignacion]/', 'endpoints/profesor/obtenerAsignacionProfesor.php');
} catch (Exception $e){}

try {
    $router->map('DELETE', '/api/asignacion/[a:asignacion]/', 'endpoints/profesor/borrarAsignacion.php');
} catch (Exception $e){}

try {
    $router->map('DELETE', '/api/asignacion/[a:asignacion]/criterio/[a:criterio]/', 'endpoints/profesor/borrarCriterio.php');
} catch (Exception $e){}

try {
    $router->map('DELETE', '/api/asignacion/[a:asignacion]/tema/[:tema]/', 'endpoints/profesor/desasignarTemaAsignacion.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/asignacion/[a:asignacion]/', 'endpoints/profesor/editarAsignacion.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignaciones/[a:asignatura]/[:tema]/[a:alumno]/', 'endpoints/profesor/obtenerAsignacionesPorTemaYAlumnoProfesor.php');
} catch (Exception $e){}

try {
    $router->map('GET', '/api/asignacion/[a:asignacion]/[a:alumno]/', 'endpoints/profesor/obtenerAsignacionEvaluarProfesor.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/asignacion/[a:asignacion]/[a:alumno]/', 'endpoints/profesor/evaluarAsignacion.php');
} catch (Exception $e){}

try {
    $router->map('POST', '/api/asignacion/[a:asignacion]/[a:alumno]/[a:criterio]/', 'endpoints/profesor/evaluarCriterio.php');
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