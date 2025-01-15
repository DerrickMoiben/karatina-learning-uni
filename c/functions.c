#include  <stdio.h>

int multiply (int a, int b) {
    int mul;

    mul =  a * b;

    printf("The product of two numbers %d and %d is %d\n", a, b, mul);

    return 0;
}

int main (){
    int number1, number2;

    printf("Enter the two numbers ");
    scanf("%d%d", &number1, &number2);

    multiply(number1, number2);

    return 0;
}

