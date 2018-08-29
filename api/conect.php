<?php
//$servername = "iessaladillo.000webhostapp.com/localhost";
//$username = "id6248946_iessaladilloroot";
//$password = "iessaladilloroot12345";
//$dbname = "id6248946_planificador";

$servername = "localhost";
$username = "proyecto";
$password = "proyecto12345";
$dbname = "proyecto_final";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

?>