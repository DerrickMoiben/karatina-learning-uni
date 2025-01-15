#include <stdio.h>

#define MAX_SIZE 25

int main() {
    int numprinted;
    int list [MAX_SIZE];

    numprinted = 0;
    for (int i = 0; i < MAX_SIZE; i++) {
        printf("%d", list[i]);
        if (numprinted < 9){
            numprinted++;
        }
        else {
            printf("\n");
            numprinted = 0;
        }
    }

    return 0;
}