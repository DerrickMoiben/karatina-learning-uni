#include <iostream>
#include <string>
using namespace std;

int main() {
    cout << "Wozaa " << endl;
    int i, j;
    int sum;
    string name;

    cout << "Please enter your name " ;
    cin >> name;

    cout << "Please enter number one ";
    cin >> i;
    
    cout << "Please enter number two ";
    cin >> j;

    sum = i + j;

    cout << "The sum of the two numbers is  " << sum << " you name is " << name <<  endl;

    return 0;    
}
