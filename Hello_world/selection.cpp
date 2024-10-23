#include <iostream>
using namespace std;

void selectionsort(int data[], int size){
    // Data[] is the array of numbers we want to sort
    // n is the number of elements in the array
    for(int i = 0; i < size -1; i++){
        // i will be the index of the element we're current ly working with

       int minIdex = i; // Assume the  current element is the smalllest
       for (int  j = i + 1; j < size; j++) {
            if (data[j] < data[minIdex]){
                minIdex = j;
            }
       }
       if (minIdex != i){
        int temp = data[i];
        data[i] = data[minIdex];
        data[minIdex] = temp;
       }
    }
    cout << "The sorted array in this case ";
    for(int i = 0; i < size; i++){
        cout << data[i] << " ";
    }
    cout << endl;
}

//This main fucntion you have to pass youseld the values to be entered
// int main(){
//     int data[] = {6, 7, 300, 5, 4, 3};
//     int size = sizeof(data) / sizeof(data[0]);

//     selectionsort(data, size);
//     return 0;
// }

int main(){
    int size;
    cout << "Enter the size or rater number of elements you want to enter ";
    cin >> size;

    int data[size];

    cout << "Ente the the numbers to be sorted ";
    for(int i = 0; i < size; i++){
        cin >> data[i];
    }

    selectionsort(data, size);
    return 0;
}