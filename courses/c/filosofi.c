#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<semaphore.h>
#include<assert.h>
#include<unistd.h>
#include<time.h>

#define PHILOSOPHER_AMOUNT 5

void *philosopher(void *);

sem_t spoon[PHILOSOPHER_AMOUNT];
sem_t room_size;

int main(int argc, char const *argv[]) {

  sem_init(&spoon[0], 0, 1);
  sem_init(&spoon[1], 0, 1);
  sem_init(&spoon[2], 0, 1);
  sem_init(&spoon[3], 0, 1);
  sem_init(&spoon[4], 0, 1);
  sem_init(&room_size, 0, 4);

  pthread_t threads[PHILOSOPHER_AMOUNT];
  int thread_args[PHILOSOPHER_AMOUNT];
  int result_code, i;

  for (i = 0; i < PHILOSOPHER_AMOUNT; i++) {
    thread_args[i] = i;
    printf("In main: creating thread %d\n", i);
    result_code = pthread_create(&threads[i], NULL, philosopher, (void *) &thread_args[i]);
    assert(0 == result_code);
  }

  for (i = 0; i < PHILOSOPHER_AMOUNT; i++) {
    result_code = pthread_join(threads[i], NULL);
    printf("In main: thread %d has completed.\n", i);
    assert(0 == result_code);
  }

  return 0;
}


void *philosopher(void *arg) {
  int input;

  input = *((int*) arg);

  while (1) {

     sem_wait(&room_size);
      printf("Philosopher %d enters room,\n", input+1);
      sem_wait(&spoon[input]);
      sem_wait(&spoon[(input + 1) % 5]);
      printf("Philosopher %d is eating.\n", input+1);
      sleep(1);
      sem_post(&spoon[(input + 1) % 5]);
      sem_post(&spoon[input]);
      sem_post(&room_size);
      printf("Philosopher %d is done\n", input+1);
  }

  return NULL;
}
