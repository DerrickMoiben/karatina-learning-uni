#include <iostream>
using namespace std;

int bubble(int data[], int size);


int main () {
    int size, result;

    cout << "Enter the sixe of data you numbwill sort in bubble sort ";
    cin >> size;

    int data [size];

    cout << "Enter the numbers  ";
    for (int i = 0; i <=  size; i++){
        cin >> data[i];
    }

    bubble(data, size);

    cout << "Sorted array: ";
    for (int i = 0; i < size; i++){
        cout << data[i] << " ";
    }

    cout << endl;
    return 0;
}

 int bubble(int data[], int size){
    int pass, j, temp;

    for (pass = 1; pass < size; pass++){
        for (int i = 0; i < size - pass; j++){
            if (data[i] > data[i + 1]){
                temp = data[i];
                data[i] = data[i + 1];
                data[i + 1] = temp;

            }

        }
    }
    return 0;
}