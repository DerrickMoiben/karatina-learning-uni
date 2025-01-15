#include <iostream>
using namespace std;

int mul(int n, int m);

int main() {
    int i, j;

    cout << "Enter the first number: ";
    cin >> i;

    cout << "Enter the second number: ";
    cin >> j;

    mul(i,j);

    return 0;
}

int mul(int n, int m) {
    int result;
    if (m  == 1){
        cout << "The number of the base case is this: " << n << endl;
        return n;
    }
    else {
        result = n + mul(n, m-1);
        cout << "The result is: " << result << endl;
    }

    cout << "The actuall number is this one over here: " << result << endl;

    return result;

} 