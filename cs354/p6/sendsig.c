////////////////////////////////////////////////////////////////////////////////
// Main File:        (sendsig.c)
// This File:        (sendsig.c)
// Other Files:      (division.c intdate.c )
// Semester:         CS 354 Spring 2019
//
// Author:           (DI bao)
// Email:            (dbao5@wisc.edu)
// CS Login:         (dbao)
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide ///////////////////////////////////
#include <signal.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/* struct of signal type
 */
enum sigtype 
{
    SIGINT_TYPE,  /* send SIGINT */
    SIGUSR1_TYPE  /* send SIGUSR1 */
};

/* collect the type of exception signals and deal it will specific method
 *
 * (argc) (args count)
 * (argv) (args value)
 *
 * get the input from the command line and if the signal type is sigint, then kill user1 
 * otherwise kill siguser1
 */
int main(int argc, char* argv[]) {
    int pid, type;

    /* check arguments number */
    if (argc != 3) {
        printf("Usage: <signal type> <pid>\n");
        return 0;
    }

    /* check signal type */
    if (strcmp(argv[1], "-u") == 0) 
    {
        type = SIGUSR1_TYPE;
    } else if (strcmp(argv[1], "-i") == 0) 
    {
        type = SIGINT_TYPE;
    } else 
    {
        printf("Signal type: -i(SIGINT)/-u(SIGUSER1)\n");
        return -1;
    }

    /* get pid */
    pid = atoi(argv[2]);
    if (pid == 0) 
    {
        printf("Invalid pid: %s\n", argv[2]);
        return -1;
    }

    /* send signal using kill according to signal type */
    switch (type)
    {
    case SIGINT_TYPE:
        kill(pid, SIGINT);
        break;
    case SIGUSR1_TYPE:
        kill(pid, SIGUSR1);
        break;
    }

    return 0;
}
