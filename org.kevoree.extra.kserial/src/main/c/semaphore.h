#include <sys/ipc.h>
#include <sys/sem.h>
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <netdb.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <assert.h>


#ifndef SEMAPHORE
#define SEMAPHORE


#define INIVAL 0   /* Valeur initiale du semaphore             */
#define Pval -1 /* Opérations sémaphores */
#define Vval  1


void detruireSem(char* filename,int SEMNBR);

int creerSem(char* filename,int SEMNBR);

void semaphore (int sem_id, int sem_num, int op);
int recupererSem(char* filename,int SEMNBR);

void P(int sem_id, int sem_num);


void V(int sem_id, int sem_num);



#endif
