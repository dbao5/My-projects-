////////////////////////////////////////////////////////////////////////////////
// Main File:        (intdate.c)
// This File:        (intdate.c)
// Other Files:      (division.c sendsig.c )
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
#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <time.h>
#include <string.h>
#include <stdlib.h>

/* alarm period (seconds) */
#define ALRM_PERIOD 3

/* SIGUSER1 counter */
int siguser1_count = 0;

/* alarm signal is received and the handelr will print it out 
 *
 * (sig) (signal of alarm)
 *  infinite loop
 */
void alrm_handler(int sig) 
{
    /* get current time */
    time_t now = time(NULL);

    printf("PID: %d | Current Time: %s", getpid(), ctime(&now));
    /* resend SIGALRM */
    alarm(ALRM_PERIOD);
}

/* exception from the user1 
 *
 * (sig) (user1 signals)
 * (when the user1 signal is received, the global count variable will increment)
 */
void usr1_handler(int sig) 
{
    /* count */
    ++siguser1_count;

    printf("SIGUSR1 caught!\n");
}

/* receive the signal from keyboard
 *
 * (sig) (exception signal)
 * terminate and exist the process
 */
void int_handler(int sig) 
{
    printf("\nSIGINT received.\n");
    printf("SIGUSR1 was received %d times. Exiting now.\n", siguser1_count);

    /* terminate program*/
    exit(0);
}
/* set the alarm period and connect to the local device to show the time 
 *
 * the process will send out alarm every 3 second 
 */
int main() 
{
    /* signal actions for SIGALRM SIGUSR1 SIGINT */
    struct sigaction sigalrm_action, sigusr1_action, sigint_action;

    /* register signal handlers */
    memset(&sigalrm_action, 0, sizeof(sigalrm_action));
    sigalrm_action.sa_handler = alrm_handler;
    sigaction(SIGALRM, &sigalrm_action, NULL);

    memset(&sigusr1_action, 0, sizeof(sigusr1_action));
    sigusr1_action.sa_handler = usr1_handler;
    sigaction(SIGUSR1, &sigusr1_action, NULL);

    memset(&sigint_action, 0, sizeof(sigint_action));
    sigint_action.sa_handler = int_handler;
    sigaction(SIGINT, &sigint_action, NULL);

    printf("Pid and time will be printed every 3 seconds.\n");
    printf("Enter ^C to end the program.\n");

    /* send SIGALRM */
    alarm(ALRM_PERIOD);
    /* infinite loop to show the alarm info*/
    while (1) 
    {
    }

    return 0;
}

