<!DOCTYPE html>
<html>

<head>
  <title>Upload Image with Details</title>
  <style>
      body {
          font-family: Arial, sans-serif;
          background-color: #f4f4f4;
          margin: 0;
          padding: 0;
      }

      #frm {
          border: 2px solid #007BFF;
          border-radius: 10px;
          width: 400px;
          margin: 100px auto;
          padding: 20px;
          background-color: white;
      }

      h2 {
          text-align: center;
          color: #333;
      }

      input[type="text"],
      input[type="number"],
      textarea,
      input[type="file"] {
          width: 100%;
          padding: 10px;
          margin: 10px 0;
          border: 1px solid #ccc;
          border-radius: 5px;
          box-sizing: border-box;
      }

      input[type="submit"] {
          background-color: #007BFF;
          color: white;
          border: none;
          padding: 10px;
          border-radius: 5px;
          cursor: pointer;
          width: 100%;
      }

      input[type="submit"]:hover {
          background-color: #0056b3;
      }

      textarea {
          resize: vertical;
          height: 100px;
      }
  </style>
</head>

<body>
  <h2>Upload Image</h2>
  <div id="frm">
      <form action="store.php" method="post" enctype="multipart/form-data">
          Name: <input type="text" name="name"><br>
          Price: <input type="number" name="price"><br>
          Description: <textarea name="description"></textarea><br>
          Select Image: <input type="file" name="image" required><br>
          <input type="submit" value="Upload">
      </form>
  </div>

</body>

</html>