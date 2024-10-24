#include <iostream>
using namespace std;

void quicksort(int a[], int l, int r){
    if (l < r){
        int i = l;
        int j = r;
        int pivot = a[l];
        while (i < j){
            while (a[i] <= pivot){
                i++;
            }
            while (a[j] > pivot){
                j--;
            }
            if (i < j){
                swap(a[i], a[j]);
            }
        }
        swap(a[l], a[j]);
        quicksort(a, l, j - 1);
        quicksort(a, j + 1, r);
    }
}


int main(){
    int a[] = { 12, 11, 13, 5, 6, 7 };
    int n = sizeof(a) / sizeof(a[0]);
    quicksort(a, 0, n - 1);
    for (int i = 0; i < n; i++){
        cout << a[i] << " ";
    }
    cout << endl;
    return 0;
}