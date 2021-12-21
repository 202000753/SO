#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <semaphore.h>
#include <fcntl.h>

int main()
{
	sem_unlink("mymutex");
	sem_t *sem = sem_open("mymutex", O_CREAT, 0644, 0);

	// Create shared memory map
	int size = 64;
	int protection = PROT_READ | PROT_WRITE;
	int visibility = MAP_ANONYMOUS | MAP_SHARED;
	void *shmem = mmap(NULL, size, protection, visibility, 0, 0);

	int cpid = fork();
	if (cpid == 0) {
		printf("Child process!\n");
		sem_wait(sem);
		printf("shmem=%s", (char*) shmem);
		exit(0);
	}
	else {
		printf("Parent process!\n");
		char *msg = "Hello from parent process..\n";
		sleep(2);
		strcpy(shmem, msg);
		sem_post(sem);
		wait(0);
	}

	return (EXIT_SUCCESS);
}

