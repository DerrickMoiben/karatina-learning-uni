#include <iostream>
using namespace std;

int fib(int n){

    int a, b, fib;
    if ( n == 0){
        return 0;
    } else  if ( n == 1) {
        return 1;
    }

    a = 0;
    b = 1;
    fib = 0;

    for(int i = 2; i <= n; i++;){
        fib = a + b;
        a = b;
        b = fib;
    }
    return fib;
}
int main(){
    int number, result;

    cout << "Enter a number to calculate the Fibinacci ";
    cin >> number;

    result = fib(number);

    cout << "The fibinacci series of " << number << " is " << result << endl;

    return 0;
}