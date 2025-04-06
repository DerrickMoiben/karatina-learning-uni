<?php 

require_once "db_config.php";

session_start();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $image_id = $_POST['image_id'];

   
    $result = mysqli_query($conn, "SELECT * FROM images WHERE id = $image_id");
    //Convert the data query into a associative arrayx
    $item = mysqli_fetch_assoc($result);

    if (!isset($_SESSION['cart'])) {
        //Declaring a session by the name cart and intializese it as an empty array
        $_SESSION['cart'] = [];
    }

    //Addig the item to the backpack
    $_SESSION['cart'][] = $item;

    header("Location: view_cart.php");
    exit();

}

?>