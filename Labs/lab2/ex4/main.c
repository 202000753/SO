#include <stdio.h>
#include "math.h"

int main()
{
	int n = 0;

	printf("Insira um numero: ");
	scanf("%d", &n);	

	printf("O quadrado do numero é %d\n", square(n));
	printf("O cubo do numero é %d\n", cube(n));
	
	return 0;
}
