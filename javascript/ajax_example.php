<?php
$servername = 'localhost';
$username = 'phpuser';
$password = 'P@ssword';
$database = 'test_db';
$conn = new mysqli($servername, $username, $password, $database);

// Check for connection errors
if($conn->connect_error) {
    die('Connection failed: ' . $conn->connect_error);
}

$age = $_GET['age'];
$wpm = $_GET['wpm'];

$query = "SELECT * FROM ajax_example WHERE age = '$age'";

if (is_numeric($age)) {
    $query .= " AND age <= $age";
}
if (is_numeric($wpm)) {
    $query .= " AND wpm <= $wpm";
}

// Execute query
$qry_result = mysqli_query($conn, $query) or die(mysqli_error($conn));

// Build Result String
$display_string = "<table>";
$display_string .= "<tr><th>Name</th><th>Age</th><th>WPM</th></tr>";

// Insert a new row in the table for each person returned
while ($row = mysqli_fetch_array($qry_result)) {
    $display_string .= "<tr><td>{$row['name']}</td><td>{$row['age']}</td><td>{$row['wpm']}</td></tr>";
}

$display_string .= "</table>";
echo $display_string;
?>
