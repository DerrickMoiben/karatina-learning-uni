#include <iostream>
#include <string>
using namespace std;

int  main (){
    string firstname, middlename, lastname, fullname;

    cout << "please Enter your first name";
    cin >> firstname;
    cout << "please Enter your middle name";
    cin >> middlename;
    cout << "please Enter your last  name";
    cin >> lastname;

    cout << "Your sirname is " << middlename << endl;

    fullname = firstname.append(lastname);

    cout << fullname <<endl;


 }