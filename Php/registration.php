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
    <br>
    <h3>fetching data from the databse</h3>
    <table align="center" style="width: 300px;">
        <tr>
            <th>id</th>
            <th>firstname</th>
            <th>Lastname</th>
            <th>Email</th>
            <th>Phone</th>
        </tr>
        <?php
        $conn = new mysqli("localhost", "phpuser", "P@ssword", "school");

        if($conn->connect_error) {
            die("Error in the Db connection: ".$conn->connect_errno.":".$conn->connect_error);
        }
        $select = "SELECT * FROM students ORDER BY id";

        $result = $conn->query($select);

        while ($row = mysqli_fetch_array($result)) {
            ?>
            <tr>
                <td><?php echo $row["id"]; ?></td>
                <td><?php echo $row["firstname"]; ?></td>
                <td><?php echo $row["lastname"]; ?></td>
                <td><?php echo $row["email"]; ?></td>
                <td><?php echo $row["phone"]; ?></td>
            </tr>
        <?php
        }
        ?>
    </table>
</body>
</html>