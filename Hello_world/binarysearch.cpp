#include <iostream>
using namespace std;

int binarysearch(int a[], int l, int r, int x){
    if (r >= l){
        int mid = l + (r - l) / 2;
        if (a[mid] == x){
            return mid;
        }
        if (a[mid] > x){
            return binarysearch(a, l, mid - 1, x);
        }
        return binarysearch(a, mid + 1, r, x);
    }
    return -1;
}

int main(){
    int a[] = { 2, 3, 4, 10, 40 };
    int n = sizeof(a) / sizeof(a[0]);
    int x = 10;
    int result = binarysearch(a, 0, n - 1, x);
    if (result == -1){
        cout << "Element is not present in the array" << endl;
    }
    else{
        cout << "Element is present at index " << result << endl;
    }
    return 0;
}