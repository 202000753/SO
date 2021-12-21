#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <semaphore.h>
#include <fcntl.h>
int main()
{
	sem_unlink("mymutex");
	sem_unlink("mymutex1");
	sem_unlink("mymutex2");
	sem_t *sem_ping = sem_open("mymutex", O_CREAT, 0644, 1);
	sem_t *sem_pong1 = sem_open("mymutex1", O_CREAT, 0644, 0);
	sem_t *sem_pong2 = sem_open("mymutex2", O_CREAT, 0644, 0);

	// Pong 1
	if (fork() == 0) {
		while (1) {
			sem_wait(sem_pong1);
			printf("Pong 1\n");
			sleep(1);
			sem_post(sem_pong2);
		}
	}

	// Pong 2
	if (fork() == 0) {
		while (1) {
			sem_wait(sem_pong2);
			printf("Pong 2\n");
			sleep(1);
			sem_post(sem_ping);
		}
	}

	// Ping
	while (1) {
		sem_wait(sem_ping);
		printf("Ping\n");
		sleep(1);
		sem_post(sem_pong1);
	}

	sem_close(sem_ping);
	sem_close(sem_pong1);
	sem_close(sem_pong2);

	return (EXIT_SUCCESS);
}
