#include <stdio.h>

int main() {

    int i, j;

    for (i = 1; i <= 3; i++){
        printf("Row%d", i);
        for (j = 0; j < 6; j++) 
            printf("%3d", j);
        printf("\n");
        
    }

    return 0;
}