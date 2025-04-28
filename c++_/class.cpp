#include <iostream>
using namespace std;

class Factory {
    private:
    string name;
    int id;

    public:
    void setFactory(string x, int i) {
        name = x;
        id = i;
    }
    string getFactory() {
        return name;
    }

    int myName() {
        cout << "PLease mame sure you enter the name of the facotory please" << endl;

        return 0;
    }
};

int main() {
    Factory olmismis;

    // olmismis.name = "Olmismis";
    // olmismis.id = 34;

    // cout << "The factory naem is this " << olmismis.name << endl;
    // cout << "The factory id is this " << olmismis.id << endl;

    // olmismis.myName();

    olmismis.setFactory("olmismis", 45);
    cout << olmismis.getFactory();

    return 0;
}