#include <stdio.h>

int main() {
    char a;
    char b;

    a = -123;

    int i;
    int j;

    printf("%p\n %p\n", &a, &b);
    printf("For integers\n");
    printf("%p\n %p\n", &i, &j);
    return 0;
}