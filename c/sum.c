#include <stdio.h>


int addition(int a, int b, int c, int d) {
    printf("\n We are going to to learn about c\n");
    int sum;

    sum =  a + b + c + d;

    printf("The sum for the four numbers%d\n", sum);   
}

int main() {
    int a, b, c, d;

    printf("Enter the first number: ");
    scanf("%d", &a);
    printf("Enter the second number: ");
    scanf("%d", &b);
    printf("Enter the third number: ");
    scanf("%d", &c);
    printf("Enter the fourth number: ");
    scanf("%d", &d);

    addition(a, b, c, d);

    return 0;

}