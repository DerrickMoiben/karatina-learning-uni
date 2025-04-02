<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register the studets</title>
    <style>
        #frm {
         border: solid green 1px;
         width: 20%;
         margin: 100px auto;
         padding: 50px;
        }
        #frm input {
            padding: 10px;
            width: 100%;
        }
        #btn {
            margin-left: 10px;
            padding: 10px;
            background: blue;
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <div id="frm">
    <h1>Regstration Form</h1>
    <h3>Saving Data into the Database</h3>
    
    <form action="connect.php" method="post" >
        <label for="">firstname</label><br>
        <input type="text" placeholder="Enter your  name" name="firstname" required="true">
        <br>
        <label for="">Last name</label><br>
        <input type="text" placeholder="Enter the last name" name="lastname" required>
        <br>
        <label for="">Email</label><br>
        <input type="text" placeholder="Enter Email" name="email" required>
        <br>
        <label for="">Phone</label><br>
        <input type="text" placeholder="Enter Phone" name="phone">
        <br>
        <input type="submit" id="btn" value="login"><br>
    </form>
    </div>
</body>
</html>