#include <iostream>
using namespace std;

int fibnacci(int n);

int main(){
    int i, result;

    cout << "Enter a nubmer that you want to find the fib";
    cin >> i;

    result = fibnacci(i);

    cout << "The fibonacci for the number is this " << result << endl;

    return 0;

}

int  fibnacci(int n) {
    int result;

    if (n == 0){
        return 0;
    }
    else if (n == 1){
        return 1;
    }
    else {
        result = fibnacci(n - 1) + fibnacci(n -2);
    }

    return result;
}