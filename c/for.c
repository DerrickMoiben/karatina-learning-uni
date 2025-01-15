#include <stdio.h>

int main() {

    int limits, i;

    printf("Enter the liit that is required for pinting: ");
    scanf("%d", &limits);

    for (i = 0; i <=  limits; i++){
        printf("%d\n", i);
    }


    return 0;
}