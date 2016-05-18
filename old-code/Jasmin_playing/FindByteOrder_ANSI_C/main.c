#include <stdio.h>

int main(int argc, char **argv)
{
    int i = 1;
    unsigned char *buf = (unsigned char *)&i;

    if(buf[0] == 1) {
        printf("little-endian\n");
    } else {
        printf("big-endian\n");
    }

    return(0);
}
