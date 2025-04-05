<?php

$servername = 'localhost';
$username = 'phpuser';
$password = 'P@ssword';
$database = 'test_db';

$conn = new mysqli("localhost", "phpuser", "P@ssword", "test_db");

//checking for errors  and insertin data into the database
if($conn -> connect_error ) {
    die('connection failed :'.$conn-> $connect_error);
}

?>