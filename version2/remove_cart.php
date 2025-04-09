<?php
session_start();

if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['image_id'])) {
    $item_to_remove = $_POST['image_id'];

    // Check if cart exists and is not empty
    if (isset($_SESSION['cart']) && !empty($_SESSION['cart'])) {
        foreach ($_SESSION['cart'] as $index => $item) {
            if ($item['image_id'] == $item_to_remove) {
                unset($_SESSION['cart'][$index]); // Remove the item
                $_SESSION['cart'] = array_values($_SESSION['cart']); // Re-index array
                break;
            }
        }
    }
}

header('Location: view_cart.php');
exit();
?>