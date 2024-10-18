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

int main(){
    int size;

    cout << "Enter a nuber of array to  be sorted ";
    cin >> size;

    int data[size];

    cout << "Enter the elements of the array ";
    for (int i = 0; i <  size; i++){
        cin >> data[i];
    }

    bubblesort(data, size);

    cout << "Sorted array: ";
    for (int i = 0; i < size; i++){
        cout << data[i] << " ";
    }

    cout << endl;
    return 0;

}