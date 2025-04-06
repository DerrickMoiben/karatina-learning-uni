<?php
require_once "db_config.php"; // PHP code to connect to the database

$result = $conn->query("SELECT * FROM images"); // Fetch images from the database

$images = []; // Initialize an array to store images
while ($row = $result->fetch_assoc()) {
    $images[] = $row; // Store each row in the images array
}

$conn->close(); // Close the database connection
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Car Inventory</title>
    <link rel="stylesheet" href="styles.css"> <!-- Link to CSS -->
</head>
<body>
    <div class="container">
        <h2>Stored Images</h2>
        <div class="inventory">
            <?php foreach ($images as $image): ?> <!-- Loop through images -->
                <div class="card">
                    <img src="uploads/<?php echo $image['image']; ?>" alt="<?php echo $image['name']; ?>">
                    <div class="card-details">
                        <strong>Name:</strong> <?php echo $image['name']; ?><br>
                        <strong>Price:</strong> $<?php echo $image['price']; ?><br>
                        <strong>Description:</strong> <?php echo $image['description']; ?><br>
                        <form action="delete_image.php" method="post">
                            <input type="hidden" name="image_id" value="<?php echo $image['id']; ?>">
                            <input type="submit" value="Delete" class="btn btn-danger">
                        </form>
                    </div>
                </div>
            <?php endforeach; ?> <!-- End of loop -->
        </div>
        <p>Data uploaded. <a href="upload.php">View Images</a></p>
    </div>
</body>
</html>
