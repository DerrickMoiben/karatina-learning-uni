<?php
session_start(); // 🎒 Start the backpack
?>

<!DOCTYPE html>
<html>

<head>
    <title>My Cart</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f8f8f8;
            padding: 20px;
        }

        nav {
            background-color: bisque;
            padding: 10px 20px;
            margin: 10px;
            border-radius: 20px;
        }

        .navbar-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #333;
        }

        .nav-links {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
            justify-content: end;
        }

        .nav-links li {
            margin-right: 20px;
        }

        .nav-links a {
            text-decoration: none;
            color: #444;
            font-size: 16px;
            transition: color 0.3s ease;
        }

        .nav-links a:hover {
            color: green;
        }

        h2 {
            color: #333;
            text-align: center;
        }

        .cart-item {
            background-color: #fff;
            border: 1px solid #ddd;
            padding: 45px;
            margin-bottom: 20px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.05);
        }

        .cart-item img {
            width: 600px;
            height: auto;
            border-radius: 8px;
            margin-right: 20px;
        }

        .cart-details {
            text-align: center;
            flex: 1;
        }

        .cart-details p {
            margin: 5px 0;
        }

        .total {
            font-size: 20px;
            font-weight: bold;
            color: green;
            text-align: right;
        }

        .empty-message {
            text-align: center;
            font-size: 18px;
            color: gray;
        }

        .remove-btn {
            margin-top: 10px;
            padding: 6px 12px;
            background-color: #ff4d4d;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

        .remove-btn:hover {
            background-color: #cc0000;
        }
    </style>
</head>

<body>
    <nav>
        <div class="navber-container">
            <div class="logo">🛍️ MyStore</div>
            <ul class="nav-links">
                <li><a href="inventory.php">← Back to Shop</a></li>
            </ul>
        </div>
    </nav>

    <?php
    if (!empty($_SESSION['cart'])) {
        $total = 0;
        foreach ($_SESSION['cart'] as $item) {
            echo "<div class='cart-item'>";
            echo "<img src='uploads/" . $item['image'] . "' alt='" . $item['name'] . "'>";
            echo "<div class='cart-details'>";
            echo "<p><strong>" . $item['name'] . "</strong></p>";
            echo "<p>" . $item['description'] . "</p>";
            echo "<p>Price: Ksh " . $item['price'] . "</p>";
            echo "<form action='remove_cart.php' method='POST'>";
            echo "<input type='hidden' name='image_id' value='" . $item['image_id'] . "'>";
            echo "<button type='submit' class='remove-btn'>❌ Remove</button>";
            echo "</div>";
            echo "</div>";


            $total += $item['price'];
        }
        echo "<div class='total'>Total: Ksh $total</div>";
    } else {
        echo "<p class='empty-message'>Your cart is empty!</p>";
    }
    ?>

</body>

</html>