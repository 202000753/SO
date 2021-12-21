#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <semaphore.h>
#include <fcntl.h>
#include <string.h>

int main()
{
	sem_unlink("mymutex");
	sem_unlink("mymutex1");
	sem_t *job_ready = sem_open("mymutex", O_CREAT, 0644, 0);
	sem_t *job_done = sem_open("mymutex1", O_CREAT, 0644, 1);
	
	// Create shared memory map
	int size = 64;
	int protection = PROT_READ | PROT_WRITE;
	int visibility = MAP_ANONYMOUS | MAP_SHARED;
	void *shmem = mmap(NULL, size, protection, visibility, 0, 0);

	// Workers
	int num_workers = 5;
	int pids[num_workers];

	// Fork worker processes
	for (int i=0; i<num_workers; i++) {
		pids[i] = fork();

		if (pids[i] == 0) {
			printf("Worker process #%d!\n", i);

		while (1) {
			// Do work
			sem_wait(job_ready);
			printf("String size %ld, by %d\n", strlen((char*) shmem), i);
			sem_post(job_done);		
		}

		exit(0);
		}
	}

	// Parent process
	printf("Parent process!\n");
	char str[10][11]={{'a'},{'a','b'},{'a','b','c'},{'a','b','c','d'},{'a','b','c','d','e'},{'a','b','c','d','e','f'},{'a','b','c','d','e','f','g'},{'a','b','c','d','e','f','g','h'},{'a','b','c','d','e','f','g','h','i'},{'a','b','c','d','e','f','g','h','i','j'}};
	for(int i=0; i<10; i++) {
		sem_wait(job_done);		
		strcpy(shmem, str[i]);
		sem_post(job_ready);
	}

	sleep(2);
	// Kill worker processes
	for (int i=0; i<num_workers; i++) {
		printf("Killing %d\n", pids[i]);
		kill(pids[i], SIGKILL);
	}

	return (EXIT_SUCCESS);
}

