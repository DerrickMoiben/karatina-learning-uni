#include <iostream>
using namespace std;


int main(){
    int a, b,result;
    char operation;

    cout << "Enter the first number ";
    cin >> a;
    cout << "Enter the second number ";
    cin >> b;
    cout << "Enter the operation to be performed: ";
    cin >> operation;

    switch(operation){
        case '+':
            result = a + b;
            cout << "The sum of numbers is " << result << endl;
            break;
        case '-':
            result = a - b;
            cout << "The subtraction of numbers is " << result << endl;
            break;
        case '*':
            result =  a * b;
            cout << "The multiplicatio of nubers is " << result << endl;
            break;
        case '/':
            result = a / b;
            cout << "The division of the two numbers is " << result << endl;
            break;
            
        default:
            cout << "You entered the wrong operation" << endl;
    }

    return 0;
}