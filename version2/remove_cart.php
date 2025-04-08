<?php
session_start();

if($_SERVER['REQUEST_METHOD'] == 'POST') {
    $item_to_remove = $_POST['image_id'];

    foreach ($_SESSION['cart'] as $index => $item) {
        if ($item['image_id'] == $item_to_remove) {
            unset($_SESSION['cart']['$index']);
            $_SESSION['cart'] == array_values($_SESSION['cart']); 
            break;
        }
    }
}
header('Location: view_cart.php');
exit();

?>