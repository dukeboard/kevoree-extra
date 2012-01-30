#include "semaphore.h"
#include "utils.h"
/**
 * Crée un sémaphore IPC.
 * @param filename Le fichier qui va servir à créer un sémaphore
 * @param SEMNBR  Nombre de cases du tableau de semaphores
 */
int creerSem(char* filename,int SEMNBR) {
        key_t sem_cle;
        int   sem_id;
        FILE* f;
        int i=0;

        /* On essaye de lire le fichier filename. S'il n'existe pas on le crée */
        f = fopen(filename,"w");
        fclose(f);

        sem_cle = ftok(filename,100);
        if (sem_cle==-1) { perror("ftok"); return -1; }
        /* destruction si le semaphore existe */
        detruireSem(filename,SEMNBR);

        sem_id  = semget(sem_cle, SEMNBR, IPC_CREAT | IPC_EXCL | 0666);
        if (sem_id==-1)  { perror("semget"); return -1; }


        for(i=0;i< SEMNBR-1;i++)
        {
                semctl(sem_id, i, SETVAL, INIVAL); /* Init des semaphores  */
        }

        return OK;
}

/**
 * Récupérer l'ID des sémaphores à partir d'un fichier
 * @param filename Le fichier sémaphre (voir constantes)
 */
int recupererSem(char* filename,int SEMNBR) {
        key_t sem_cle;
        int   sem_id;

        sem_cle = ftok(filename,100);
        if (sem_cle==-1) { perror("ftok"); return -1; }

        sem_id  = semget(sem_cle, SEMNBR, IPC_CREAT | 0666);
        if (sem_id==-1)  { perror("semget"); return -1; }

        return sem_id;

}

/**
 * Détruit un sémaphore IPC
 */
void detruireSem(char* filename,int SEMNBR)  {

        int   sem_id =recupererSem(filename,SEMNBR);

        semctl(sem_id, SEMNBR, IPC_RMID, NULL);
}

/**
 * Opération sur un sémaphore
 */
void semaphore (int sem_id, int sem_num, int op)
{
        struct sembuf Ops[1];
        int ok;

        Ops[0].sem_num = sem_num;
        Ops[0].sem_op  = op;
        Ops[0].sem_flg = 0;

        ok = semop(sem_id, Ops, 1);
        if (ok==-1) { perror("semop");  }
}

/**
 * Prend le jeton
 */
void P(int sem_id, int sem_num) {
         /* printf("INFO \t: P(%d) \n",sem_num); */
        semaphore (sem_id, sem_num, Pval);
}

/**
 * Libère le jeton
 */
void V(int sem_id, int sem_num)
{
        /* printf("INFO \t: V(%d) \n",sem_num); */
        semaphore (sem_id, sem_num, Vval);
}

