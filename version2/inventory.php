<?php
require_once "db_config.php";

// Initialize variables
$error = '';
$success = '';

try {
    // Fetch images from the database with error handling
    $result = $conn->query("SELECT * FROM images");
    
    if (!$result) {
        throw new Exception("Error fetching data: " . $conn->error);
    }
    
    $images = [];
    while ($row = $result->fetch_assoc()) {
        $images[] = $row;
    }
} catch (Exception $e) {
    $error = "Database error: " . $e->getMessage();
} finally {
    $conn->close();
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Car Inventory | Premium Vehicles</title>
    <meta name="description" content="Browse our collection of luxury and premium vehicles">
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <header class="page-header">
        <h1>Premium Car Inventory <i class="fas fa-car-side"></i></h1>
        <nav>
            <a href="/" class="nav-link">Home</a>
            <a href="view_cart.php" class="nav-link cart-link">
                <i class="fas fa-shopping-cart"></i>
            </a>
        </nav>
    </header>

    <div class="container">
        <section class="inventory-section">
            <h2>Available Vehicles <span class="emoji">🚗 💨</span></h2>
            
            <div class="filter-options">
                <select id="price-filter" class="filter-select">
                    <option value="">Filter by Price</option>
                    <option value="0-25000">Under $25,000</option>
                    <option value="25000-50000">$25,000 - $50,000</option>
                    <option value="50000-100000">$50,000 - $100,000</option>
                    <option value="100000">Over $100,000</option>
                </select>
            </div>
            
            <div class="inventory-grid">
                <?php if (empty($images)): ?>
                    <div class="no-results">
                        <p>No vehicles available at the moment. Please check back later!</p>
                    </div>
                <?php else: ?>
                    <?php foreach ($images as $image): ?>
                        <div class="card" 
                             data-price="<?php echo htmlspecialchars($image['price']); ?>"
                             data-type="<?php echo htmlspecialchars(strtolower($image['type'] ?? '')); ?>">
                            <div class="card-image-container">
                                <img src="uploads/<?php echo htmlspecialchars($image['image']); ?>" 
                                     alt="<?php echo htmlspecialchars($image['name']); ?>" 
                                     class="card-image"
                                     loading="lazy">
                                <?php if ($image['featured'] ?? false): ?>
                                    <span class="featured-badge">Featured <i class="fas fa-star"></i></span>
                                <?php endif; ?>
                            </div>
                            
                            <div class="card-details">
                                <h3><?php echo htmlspecialchars($image['name']); ?></h3>
                                
                                <div class="price-section">
                                    <span class="price">$<?php echo number_format($image['price'], 2); ?></span>
                                    <?php if ($image['original_price'] > $image['price']): ?>
                                        <span class="original-price">$<?php echo number_format($image['original_price'], 2); ?></span>
                                    <?php endif; ?>
                                </div>
                                
                                
                                <p class="description"><?php echo htmlspecialchars($image['description']); ?></p>
                                
                                <div class="card-actions">
                                    <form action="add_to_cart.php" method="post" class="add-to-cart-form">
                                        <input type="hidden" name="image_id" value="<?php echo htmlspecialchars($image['id']); ?>">
                                        <button type="submit" class="btn btn-cart">
                                            <i class="fas fa-cart-plus"></i> Add to Cart
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    <?php endforeach; ?>
                <?php endif; ?>
            </div>
        </section>
    </div>

    <footer class="page-footer">
        <p>&copy; <?php echo date('Y'); ?> Premium Car Inventory. All rights reserved.</p>
    </footer>

    <script src="script.js"></script>
    <script>
        // Simple filtering functionality
        document.addEventListener('DOMContentLoaded', function() {
            const priceFilter = document.getElementById('price-filter');
            const cards = document.querySelectorAll('.card');
            
            function filterCars() {
                const priceValue = priceFilter.value;
                
                cards.forEach(card => {
                    const cardPrice = parseFloat(card.dataset.price);
                    let priceMatch = true;
                    
                    // Price filtering
                    if (priceValue) {
                        const [min, max] = priceValue.includes('-') ? 
                            priceValue.split('-').map(Number) : 
                            [Number(priceValue), Infinity];
                        
                        priceMatch = cardPrice >= min && cardPrice <= max;
                    }
                    
                    // Show/hide based on matches
                    card.style.display = (priceMatch && typeMatch) ? 'block' : 'none';
                });
            }
            
            priceFilter.addEventListener('change', filterCars);
        });
    </script>
</body>
</html>