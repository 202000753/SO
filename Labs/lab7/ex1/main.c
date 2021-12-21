#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <signal.h>

void signal_handler(int signal)
{
	printf("Got signal %d..\n", signal);
}

int main()
{
	signal(SIGUSR1, signal_handler);
	int pid = fork();

	if (pid == 0) {
		printf("Hello from child!\n");
		sleep(2);
		exit(0);
	} else {
		printf("Hello from parent!\n");
		kill(pid, SIGUSR1);
		wait(NULL);
	}

	return 0;
}
