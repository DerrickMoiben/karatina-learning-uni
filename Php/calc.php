<?php
if(isset($_POST["name"]) && isset($_POST["age"]  ))
{
echo "Welcome ".$_POST['name']."<br />";
echo "You are ".$_POST['age']."<br />";
exit();
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=form, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
   <form action="" method="POST">
    Name: <input type="text" name="name"/>
    Age: <input type="text" name="age"/>
    <input type="submit"/>
   </form> 
</body>
</html>