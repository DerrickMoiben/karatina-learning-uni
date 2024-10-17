#include <iostream>
using namespace std;

int fibrec(int n){
    if ( n == 0 ){
        return 0;
    }
    else if ( n ==  1 ){
        return 1;
    }

    return fibrec( n - 1) + fibrec( n -2 );    
}

int main(){
    int number, result;

    cout << "Enter a number please to find it respective fibinacci number in the series ";
    cin >> number;

    result = fibrec(number);

    cout << "The fibinacci of the number  " << number << " is " << result << endl;

    return 0;

}