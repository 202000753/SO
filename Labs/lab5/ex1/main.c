#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <semaphore.h>
#include <fcntl.h>
#include <sys/wait.h>

void do_job(char *owner)
{
	printf("%s locks mutex..\n", owner);
	sleep(2);
	printf("%s releases mutex..\n", owner);
}
int main()
{
	sem_unlink("mymutex");
	sem_t *mutex = sem_open("mymutex", O_CREAT, 0644, 1);

	if (fork() == 0) {
		printf("Child process!\n");
		sem_wait(mutex);
		do_job("Child");
		sem_post(mutex);
		exit(0);
	}
	else {
		wait(0);
		printf("Parent process!\n");
		sem_wait(mutex);
		do_job("Parent");
		sem_post(mutex);
		wait(NULL);
	}

	sem_close(mutex);

	return (EXIT_SUCCESS);
}
