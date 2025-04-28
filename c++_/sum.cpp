#include <iostream> 
#include <string>
using namespace std;


int main() {
    int i, j, result;
    string name;

    cout << "Enter the first number please ";
    cin >> i;

    cout  <<  "Enter your  name ";
    cin >> name;



    result = i % 2;
    if (result == 0 ) {
        cout <<  "The number is even" << endl;
    } else {
        cout << "The number is odd" << endl;
    }

    cout << "Your name is " << name << endl;

}
