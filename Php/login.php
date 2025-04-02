<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <title>Login Page</title>
</head>
<body>
    <form action="process.php" method="post">
        <h1>User Login Form</h1>
        <p>
            <label for="name">Name</label>
            <input type="text" id="name" name="name" required />
        </p>
        <p>
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>
        </p>
        <p>
            <input type="submit" id="btn" value="Login">
        </p>
    </form>
</body>
</html>
