#include <iostream>
using namespace std;


// THs  fuction works like this 
// fibrec(5) = fibrec(4) + fibrec(3)
// fibrec(4) = fibrec(3) + fibrec(2)
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