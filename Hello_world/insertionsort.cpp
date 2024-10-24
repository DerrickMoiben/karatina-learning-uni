// for i = 1 to 6
// j = i -1
// tmp = a[i]
// while (j >= 0 && a[j] > temp)

//    a[j + 1] = a[j]
//    j = j - 1
//    a[j +  1] = temp



#include <iostream>
using namespace std;

void insertionsort(int a[], int n){
    int i, j, temp;
    for (i = 1; i < n; i++){
        j = i - 1;
        temp = a[i];
        while (j >= 0 && a[j] > temp){
            a[j + 1] = a[j];
            j = j - 1;
        }
        a[j + 1] = temp;
    }
}

int main(){
    int a[] = { 12, 11, 13, 5, 6 };
    int n = sizeof(a) / sizeof(a[0]);
    insertionsort(a, n);
    for (int i = 0; i < n; i++){
        cout << a[i] << " ";
    }
    cout << endl;
    return 0;
}