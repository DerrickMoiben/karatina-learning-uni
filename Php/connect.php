<?php
//Database records. The records we have in the database

$firstname = $_POST['firstname'];
$lastname = $_POST['lastname'];
$email = $_POST['email'];
$phone = $_POST['phone'];

$servername = 'localhost';
$username = 'phpuser';
$password = 'P@ssword';
$database = 'school';
$conn = new mysqli("localhost", "phpuser", "P@ssword", "school");

//checking for errors  and insertin data into the database
if($conn -> connect_error ) {
    die('connection failed :'.$conn-> $connect_error);
}
else {
    $sql = ("INSERT INTO students(firstname,lastname,email,phone)
    VALUES ('$firstname', '$lastname', '$email', '$phone')");
}
$sql = mysqli_query($conn, $sql);

// Sql comditon which will be displayed after clicking the save button
if ($sql ==  true) {
    echo "Records saved";
} else {
    echo "Recors not saved";
}
?>