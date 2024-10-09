#include <iostream>
using namespace std;

int calc(int a, int b) {
    int sum;

    sum = a + b;

    cout << "The sum of of " << a << " and " << b << " is " << sum << endl;
    
    return 0;
}

int main(){
    int i, j;

    cout << "Enter the first number ";
    cin >> i;

    cout << "Enter number two ";
    cin >> j;

    calc(i, j);

    return 0;

}