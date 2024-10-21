#include <iostream>
using namespace std;

void bubblesort(int  data[], int size){
    int pass, temp;

    for(pass = 1; pass < size; pass++){
        for(int i = 0; i < size - pass; i++){
            if(data[i] > data[i + 1]){
                temp = data[i];
                data[i] = data[i + 1];
                data[i + 1] = temp;
            }
        }
    }
}
// This main function prompts the user to enter  the numbers to be sorted


// int main(){
//     int size;

//     cout << "Enter a nuber of array to  be sorted ";
//     cin >> size;

//     int data[size];

//     cout << "Enter the elements of the array ";
//     for (int i = 0; i <  size; i++){
//         cin >> data[i];
//     }

//     bubblesort(data, size);

//     cout << "Sorted array: ";
//     for (int i = 0; i < size; i++){
//         cout << data[i] << " ";
//     }

//     cout << endl;
//     return 0;

// }


int main(){
    int size;

    int data[] = {7, 5, 7, 3 , 2, 6};
    size = sizeof(data) / sizeof(data[0]);

   bubblesort(data, size);

   cout << "The sorted array is: ";
   for (int i = 0; i < size; i++){
    cout << data[i] << " ";
   }
   cout << endl;

    return 0;
}


//Time complexity

// Summary of Time Complexity:
// Best Case (already sorted): O(n)
// Worst Case (reverse sorted): O(n²)
// Average Case (random order): O(n²)
