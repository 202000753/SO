#include <stdio.h>

long factorial(int num)
{
	float result = 1;
	while (num) {
		result *= num;
		num--;
	}
	return result;
}

int main()
{
	int num = 5;
	long result = factorial(num);
	printf("factorial(%d)=%ld\n", num, result);
	return 0;
}

