<?php
include "db_config.php";

$image_id = $_POST['image_id'];

$conn->query("DELETE FROM images WHERE id = '$image_id'");

header("Location: display.php");

echo "The image $image_id was deleted";
?>