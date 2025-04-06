<?php

require_once "db_config.php";

$result = $conn->query("SELECT * FROM images");

echo "<h2>Stored Images</h2>";
while ($row = $result->fetch_assoc()) {
    echo "<div style='border:1px solid #ccc; padding:10px; margin:10px; width:300px'>";
    echo "<img src='uploads/{$row['image']}' style='width:100%; height:auto'><br>";
    echo "<strong>Name:</strong> " . $row['name'] . "<br>";
    echo "<strong>Price:</strong> $" . $row['price'] . "<br>";
    echo "<strong>Description:</strong> " . $row['description'] . "<br>";
    echo "<form action='delete_image.php' method='post'>";
    echo "<input type='hidden' name='image_id' value='{$row['id']}'>";
    echo "<input type='submit' value='Delete' class='btn btn-danger'>";
    echo "</form>";
    echo "</div>";
}
echo "data uploaded . <a href='upload.php'>View Images</a>";
$conn->close();
?>