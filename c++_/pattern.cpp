#include <iostream>
using namespace std;

int main () {
    for(int r = 1; r <= 5; r++ ){
        for (int p = 1; p <= 5 - r; p++) {
            cout << " ";
        }
        for (int s = 1; s <= 2 * r - 1; s++) {
            cout << "*";
        }
        cout << endl;
    }
         
}