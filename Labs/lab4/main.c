#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main()
{
	int n = 10;
	int p[n];
	int sec = 2000, temp = 0;

	for(int i = 0; i < n; i++)
	{
		p[i] = fork();

		if (p[i] == 0) {
			printf("Child process (PID=%d, PPID=%d)\n", getpid(), getppid());
			printf("Child PID=%d exiting!\n", getpid());
			exit(0);
		}
	}

	for(int i = 0; i < n; i++)
	{
		int pid = 0;
		printf("Parent process (PID=%d, CPID=%d)\n", getpid(), p[i]);
		pid = wait(&temp);
		printf("A child has exited: CPID=%d\n", pid);
	}

	 return 0;
}

