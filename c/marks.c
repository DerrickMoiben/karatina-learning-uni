#include <stdio.h>


int main() {
    int grade;

    printf("Enter the marks you caught: ");
    scanf("%d", &grade);

    if(grade >= 90) {
        printf("You grade is A \n");
    }
    else if(grade >= 80) {
        printf("You grade is B \n");
    }
    else if(grade >= 70) {
        printf("You grade is c \n");
    }
    else if(grade >= 60) {
        printf("You Grade is D\t");
    }
    else{
        printf("You grade is F\n");
    }

    return 0;
}