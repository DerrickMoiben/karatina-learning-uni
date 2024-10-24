#include <iostream>
using namespace std;

// Recursive function to multiply two numbers
int multiply(int a, int b) {
    // Base cases
    if (b == 0) return 0;
    if (b == 1) return a; 

    // Recursive case
    return a + multiply(a, b - 1);
}

int main() {
    int num1, num2;

    // Input from user
    cout << "Enter two positive integers: ";
    cin >> num1 >> num2;

    // Validate input
    if (num1 < 0 || num2 < 0) {
        cout << "Please enter positive integers only." << endl;
        return 1;
    }

    // Calculate product
    int product = multiply(num1, num2);

    // Display result
    cout << "The product of " << num1 << " and " << num2 << " is: " << product << endl;

    return 0;
}