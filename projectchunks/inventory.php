<?php
require_once "dbconfig.php";

// Query to select all fields from the product table
$query = "SELECT name, price, description, image FROM product";
$result = $conn->query($query);

if ($result->num_rows > 0) {
    // Display data in a table
    echo "<table border='1'>";
    echo "<tr><th>Name</th><th>Price</th><th>Description</th><th>Image</th></tr>";

    while ($row = $result->fetch_assoc()) {
        echo "<tr>";
        echo "<td>" . htmlspecialchars($row["name"]) . "</td>";
        echo "<td>" . htmlspecialchars($row["price"]) . "</td>";
        echo "<td>" . htmlspecialchars($row["description"]) . "</td>";

        // Display the image
        if (!empty($row["image"])) {
            $imageData = base64_encode($row["image"]);
            echo "<td><img src='data:image/png;base64,$imageData' alt='Car Image' width='150' height='100'></td>";
        } else {
            echo "<td>No image available</td>";
        }

        echo "</tr>";
    }

    echo "</table>";
} else {
    echo "No products found.";
}

$conn->close();
?>
