#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <signal.h>

int i, pid;

void signal_handler(int signal)
{
	printf("Parent: Handling process #%d with PID=%d\n", i, getpid());
}

void signal_handler2(int signal)
{
	printf("Child: Handled #%d, PID=%d\n", i, getpid());
}

int main()
{
	signal(SIGUSR1, signal_handler);
	signal(SIGUSR2, signal_handler2);
	int pids[5];

	for (i=0; i<5; i++) {
		pids[i] = fork();

		if (pids[i] == 0) {
			printf("Child #%d, PID=%d\n", i, getpid());
			kill(getppid(), SIGUSR1);
			sleep(2);
			printf("Child #%d, PID=%d is exiting\n", i, getpid());
			exit(0);
		}

		kill(pids[i], SIGUSR2);

		sleep(1);
	}
	sleep(5);

	for (int i=0; i<5; i++) {
		int pid = wait(NULL);
		printf("Parent: child with PID=%d ended\n", pid);
	}

	return 0;
}

/*
Cria 4 filhos
Cada filho escreve a sua primeira mesnagem depois escreve a segunda
Entre as duas mensagens mandam um sinal para o pai
O pai ao receber o sinal escreve a mesagem do signal_handler
No fim o pai escreve 4 mensagens
*/

