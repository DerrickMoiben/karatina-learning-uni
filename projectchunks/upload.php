<?php
require_once "dbconfig.php";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Get form data
    $carname = $_POST["carname"];
    $price = $_POST["price"];
    $description = $_POST["description"];

    // Check if image was uploaded
    if (isset($_FILES["image"]) && $_FILES["image"]["error"] == 0) {
        $image = file_get_contents($_FILES["image"]["tmp_name"]);

        $save = $conn->prepare("INSERT INTO product (name, price, description, image) VALUES (?, ?, ?, ?)");
        $save->bind_param("sisb", $carname, $price, $description, $image);
        
        if ($save->execute()) {
            echo "Car details uploaded successfully";
        } else {
            echo "Error: " . $save->error;
        }

        $save->close();
        $conn->close();
    } else {
        echo "Please select an image file to upload.";
    }
}
?>
