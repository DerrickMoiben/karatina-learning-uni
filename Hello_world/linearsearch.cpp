#include <iostream>
using namespace std;

int linearsearch(int a[], int n, int x){
    for (int i = 0; i < n; i++){
        if (a[i] == x){
            return i;
        }
    }
    return -1;
}

int main(){
    int a[] = { 2, 3, 4, 10, 40 };
    int n = sizeof(a) / sizeof(a[0]);
    int x = 10;
    int result = linearsearch(a, n, x);
    cout << "Element is present at index " << result << endl;
    return 0;
}