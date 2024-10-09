#include <iostream>
#include <array>
using namespace std;

int add(int a, int b) {
    int sum;

    sum = a + b;

    cout << "The sum of of " << a << " and " << b << " is " << sum << endl;
    
    return sum;
}

int sub(int a, int b) {
    int sub;

    sub = a - b;

    cout << "The subtraction of " << a <<  " and " << b << " is " << sub << endl;

    return sub;
}

int mul(int a, int b) {
    int mul;

    mul = a * b;

    cout << "The multipliation of " << a << " and " << b << "is " << mul << endl;

    return mul;
}

int division(int a, int b) {
    int divide;

    divide = a / b;

    cout << "The divison of " << a << " and " << b << "is " << divide << endl;
    return divide;
}
int main(){
    int i, j;

    cout << "Enter the first number ";
    cin >> i;

    cout << "Enter number two ";
    cin >> j;

    
    add(i, j);
    sub(i, j);
    mul(i, j);
    division(i, j);


    return 0;

}