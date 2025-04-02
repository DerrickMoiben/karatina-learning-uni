<?php
// Get values from the form in login.php
$name = $_POST['name'];  // Changed from 'user' to 'name'
$email = $_POST['email'];  // Changed from 'pass' to 'email'

// Connect to MySQL (Use `mysqli` instead of old `mysql` functions)
$conn = new mysqli("localhost", "phpuser", "P@ssword", "test_db");

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Secure the inputs to prevent SQL Injection
$name = $conn->real_escape_string($name);
$email = $conn->real_escape_string($email);

// Fix the SQL query
$sql = "SELECT * FROM users WHERE name = '$name' AND email = '$email'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    echo "Login successful! Welcome " . $row["name"];
} else {
    echo "Failed to login. Invalid name or email.";
}

// Close the connection
$conn->close();
?>
