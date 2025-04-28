#include <iostream>
using namespace std;

class student {
    public:
    string name;
    string course;
    string department;
};

class exams : public student {
    public:
    int math;
    int science;
    int english;

    int calculate_total() {
        int total;

        total = math + science + english;

        return total;
    }
    int average_marks(int math, int science, int english) {
        int total, average,  sum = 3;

        total = math + science + english;

        average = total / sum;

        return average;
    }
};

int main() {
    exams student1;

    student1.average_marks(20, 30, 40);

    cout << "The average marks are " <<  student1.average_marks(20, 30, 40) << endl;
}

