#include <stdio.h>

int main(){
    
    int a, b, result;
    char operation;

    printf("Hello enter the first number: ");
    scanf("%d", &a);
    printf("Enter the secomd number: ");
    scanf("%d", &b);
    printf("Which operation do you want to undertake is it + - / or *: ");
    scanf(" %c", &operation);

    switch(operation){
        case '+':
            result = a + b;
            printf("The sum of %d  and %d is %d \n", a, b, result);
        break;
        case '/':
            result = a / b;
            printf("The division of the  numbers is %d and %d is %d \n", a, b, result);
        break;
        case '*':
            result = a * b;
            printf("The division of the  numbers is %d and %d is %d \n", a, b, result);
        break;
        case '-':
            result = a - b;
            printf("The division of the  numbers is %d and %d is %d \n", a, b, result);
        break;
        default:
            printf("choosed the wrong operation \n");   
    }

    

    return (0);
}