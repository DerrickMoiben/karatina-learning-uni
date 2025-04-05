<?php
include "db_config.php";

$result = $conn->query("SELECT * FROM images");

echo "<h2>Stored Images</h2>";
while ($row = $result->fetch_assoc()) {
    echo "<div style='border:1px solid #ccc; padding:10px; margin:10px; width:300px'>";
    echo "<img src='uploads/{$row['image']}' style='width:100%; height:auto'><br>";
    echo "<strong>Name:</strong> " . $row['name'] . "<br>";
    echo "<strong>Price:</strong> $" . $row['price'] . "<br>";
    echo "<strong>Description:</strong> " . $row['description'] . "<br>";
    echo "</div>";
}

$conn->close();
?>
