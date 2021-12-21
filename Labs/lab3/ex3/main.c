#include <stdio.h>

int main()
{
	int nums[] = {1, 2, 3, 4};
	int len = sizeof(nums)/4;
	int sum = 0;

	for (int i=0; i<len; i++) {
		sum += nums[i];
	}

	float avg = (float) sum / len;
	printf("%.1f\n", avg);

	return 0;
}
