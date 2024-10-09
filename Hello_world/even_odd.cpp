#include <iostream>
using namespace std;

int even(int i) {
    int j;

    j = i % 2;

    if ( j == 0 ){
        cout << "The number is even" << endl;

    } else {
        cout << "The numbe is odd" << endl;
    }

    return 0;
}

int main(){

    int i;

    cout << "Enter a number ";
    cin >> i;

    even(i);

    return 0;
}