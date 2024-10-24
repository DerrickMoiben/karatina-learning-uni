#include <iostream>
using namespace std;

int binarysearch(int a[], int low, int high, int x){
    // l is the leftmost index of the array
    // r is the rightmost index of the array
    // x is the element to be searched

    if (high >= low){
        int mid = low + (high - low) / 2;
        if (a[mid] == x){
            return mid;
        }
        if (a[mid] > x){
            return binarysearch(a, low, mid - 1, x);
        }
        return binarysearch(a, mid + 1, high, x);
    }
    else {
        return -1;
    }
}
int main(){
    int a[] = { 2, 3, 4, 10, 40 };
    int n = sizeof(a) / sizeof(a[0]);
    int x = 10;
    int result = binarysearch(a, 0, n - 1, x);
    cout << "Element is present at index " << result << endl;
    return 0;
}

// The Best case in this is O(1)
// The Average case in this is O(log n)
// The Worst case in this is O(log n)
