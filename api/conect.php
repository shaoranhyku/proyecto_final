<?php
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