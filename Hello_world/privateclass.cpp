#include <iostream>
using namespace std;


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
    cout << obj.getData() << endl;

    return 0;
}