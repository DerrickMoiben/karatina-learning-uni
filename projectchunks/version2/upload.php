<!DOCTYPE html>
<html>
<head>
    <title>Upload Image with Details</title>
</head>
<body>

<h2>Upload Image</h2>

<form action="store.php" method="post" enctype="multipart/form-data">
    Name: <input type="text" name="name" required><br><br>
    Price: <input type="number" name="price" step="0.01" required><br><br>
    Description: <textarea name="description"></textarea><br><br>
    Select Image: <input type="file" name="image" required><br><br>
    <input type="submit" value="Upload">
</form>

</body>
</html>
