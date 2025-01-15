// Implementatin of multiplication of two numbers by looping where you have a result as zero then loop ove the number of times of the second
// number the we can have it this then we are going to hve addition 2 recursive way of doing this




#include <iostream>
using namespace std;

int mul(int n, int m);

int main(){
    int i, j;

    cout << "Enter the first number please:";
    cin >> i;

    cout << "Enter the secod number please: ";
    cin >> j;

    mul(i,j);

    return 0;
}


int mul(int n, int m) {
    int result, i;

    result = 0;

    for(i = 1; i <= m; i++){
        result += n;
    }

    cout << "The multplication of the two numbers by additon is" << result << endl;

    return 0;
}