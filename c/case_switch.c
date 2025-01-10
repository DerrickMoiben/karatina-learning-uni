#include <stdio.h>

int main() {
    char days;

    printf("Enter the  days of the week ");
    scanf("%c", &days);

    switch(days){
        case 'M':
            printf("Todays is Monday/n");
        break;
        case 'T':
            printf("Today is Tueday/n");
        break;
        default:
            printf("Thats not a recognized day in the days of the week/n");

    }

    return 0;
}