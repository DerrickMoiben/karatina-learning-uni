function ValidationForm() {
    let x = document.forms["myForm"]["name"].value;

    // check if the name is empty
    if (x == "") {
        alert("Name must be filled out");
        return false;
    }
    // check the lenght of characters
    let length = x.length;
    if (length < 3) {
        alert("Name charcters should be more than 3");
        return false;
    }

    // This varible will hold he email input
    let y = document.forms["myForm"]["email"].value;


    //check if the email is empty
    if (y == "") {
        alert("The email shouldn't be empty");
        return false;
    }

    //Use regular expression to validate the emal format
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    let checkemail = emailPattern.test(y);

    if (!checkemail) {
        alert("The email pattern is wrong ")
    }

    let i = document.forms["myForm"]["password"].value;

    if ( i == "") {
        alert("Password is required");
    
    }

    const passwordPattern = /^(?=.*[A-Z])(?=.*[a-z]).{8,}$/;

    let checkpass = passwordPattern.test(i);

    if(!checkpass) {
        alert("Password should adhere to the rules of passwords");
    }
    



    return true;

}