#include <iostream>
using namespace std;

int  main(){
    int n, i;
    unsigned long long factorial = 1;

    cout << "Enter a  new nuber";
    cin >> n;

    if (n < 0){
        cout << "They is no factorial for a negative number "<< n << endl;
    }
    else{
        for(i = 1; i <= n; ++i){
            factorial *= i;
        }
        cout << "Factoial of " << n << " = " << factorial << endl;
    }
    return 0;
}
