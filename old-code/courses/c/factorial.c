#include<stdlib.h>

int fact(int n) {
	if (n == 0)
		return 1;
	else return n*fact(n-1);
}

int main(int argc, char const *argv[])
{
	fact(10);
	return 0;
}