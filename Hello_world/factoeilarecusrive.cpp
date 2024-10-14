#include <iostream>
using namespace std;

int factorial(int n){
    if (n ==1 || n == 0){
        return 1;
    }
    return n * factorial(n - 1);
}

int main(){
    int number, result;

    cout << "Enter a number please: ";
    cin >> number;

     result = factorial(number);

    cout <<  " The  factoril of " << number <<  " is " << result << endl;
    return 0;
}