#include <stdio.h>

int main(){
    int i;
    int* p;

    i = 14;
    p = &i;

    printf("%d %p\n", i, &i);
    printf("%p %d %d\n", p, *p, i);

    return 0;
}