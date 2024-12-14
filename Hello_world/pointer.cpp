#include <iostream>
using namespace std;

int main() {
    int x = 10;
    int* ptr = &x;

    cout << "Value of x: " << x << endl;
    cout << "Address of x: " << &x << endl;
    cout << "Value stored in ptr: " << ptr << endl;
    cout << "Value ptr is pointing to: " << *ptr << endl;

    return 0;
}