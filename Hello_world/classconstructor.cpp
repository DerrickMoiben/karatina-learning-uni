#include <iostream>
using namespace std;

class Car {
    public:
        string brand;
        int year;

        Car(string b, int y) {
            brand =  b;
            year = y;
        }

        void displayDetails() {
            cout << "Brand: " << brand << ", Year" << year << endl;
        }
};

int main() {
    Car Mycar("Honda", 2024);
    Mycar.displayDetails();
    return 0;
}