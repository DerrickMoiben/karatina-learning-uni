#include <iostream>
using namespace std;

class Car {
public:
    // this  are attributes in a class
    string brand;
    int year;
    // this is a method in a class
    void displayDatails(){
        cout << "Brand: " << brand  << ", Year: " << year << endl;
    }
};

int main() {
    // This is a object of the class
    Car mycar;
    mycar.brand = "Volkwagen";
    mycar.year = 2022;

    mycar.displayDatails();

    return 0;
}

class Example {
    private:
        int data;

    public:
        // this is a setter 
        void setData(int value) {data = value; }
        // this is a getter 
        int getData() {return data; }
};

int main() {
    Example obj;
    obj.setData(10);
    cout << obj.getData();

    return 0;
}