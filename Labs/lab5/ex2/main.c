#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <semaphore.h>
#include <fcntl.h>
#include <sys/wait.h>

int main()
{
	sem_unlink("mymutex");
	sem_unlink("mymutex1");
	sem_t *sem_ping = sem_open("mymutex", O_CREAT, 0644, 1);
	sem_t *sem_pong = sem_open("mymutex1", O_CREAT, 0644, 0);

	if (fork() == 0) {
		while(1) {
			sem_wait(sem_pong);
			printf("Pong\n");
			sleep(1);
			sem_post(sem_ping);
		}
	}
	else {
		while(1) {
			sem_wait(sem_ping);
			printf("Ping\n");
			sleep(1);
			sem_post(sem_pong);
		}

	}

	sem_close(sem_ping);
	sem_close(sem_pong);

	return (EXIT_SUCCESS);
}
