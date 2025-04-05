<?php
require_once "dbconfig.php";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Get form data
    $carname = $_POST["carname"];
    $price = $_POST["price"];
    $description = $_POST["description"];

    // Initialize image variable
    $image = null;

    // Check if image was uploaded
    if (isset($_FILES["image"]) && $_FILES["image"]["error"] == 0) {
        // Debugging: Print file details
        echo "File Name: " . $_FILES["image"]["name"] . "<br>";
        echo "File Type: " . $_FILES["image"]["type"] . "<br>";
        echo "File Size: " . $_FILES["image"]["size"] . "<br>";

        // Validate MIME type
        $filetype = $_FILES["image"]["type"];
        $allowedTypes = ["image/jpeg", "image/png"];

        if (in_array($filetype, $allowedTypes)) {
            // Read binary data of the image
            $image = file_get_contents($_FILES["image"]["tmp_name"]);
            echo "File uploaded successfully.<br>";

            // Verify image data
            if (!empty($image)) {
                echo "Image data size: " . strlen($image) . " bytes.<br>";
            } else {
                echo "No image data available.<br>";
                exit; // Stop script execution if no image data
            }
        } else {
            echo "Invalid file type. Only JPEG and PNG are allowed.<br>";
            exit;
        }
    } else {
        echo "Error uploading file: " . $_FILES["image"]["error"] . "<br>";
        exit;
    }

    // Save product details to the database
    $save = $conn->prepare("INSERT INTO product (name, price, description, image) VALUES (?, ?, ?, ?)");
    $save->bind_param("sisb", $carname, $price, $description, $image);

    if ($save->execute()) {
        echo "Car details uploaded successfully.<br>";
    } else {
        echo "Error inserting data: " . $conn->error . "<br>";
    }

    // Close connections
    $save->close();
    $conn->close();
}
?>
