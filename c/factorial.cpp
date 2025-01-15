#include <iostream>
using namespace std;

int factorial(int n);

int main(){
    int i;

    cout << "Enter the number to find the factorial: ";
    cin >> i;

    factorial(i);

    return 0;
}

int factorial(int n){
    int result;

    if (n == 0){
        cout << "This is the base case end" << endl;
        return 1;

    }
    else {
        result = n * factorial(n - 1);
    }

    cout << "The factorial for the number is " << result << endl;

    return result;
}