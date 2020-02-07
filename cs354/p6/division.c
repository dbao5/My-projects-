////////////////////////////////////////////////////////////////////////////////
// Main File:        (division.c)
// This File:        (division.c)
// Other Files:      (intdate.c sendsig.c )
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
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

/* buffer length */
#define BUFFER_LEN 32

/* successful Success_Counter */
int Success_Count = 0;

/* signal comes from the ctrl - c an the handler will receive the exception
 *
 * (sig) (exception signal from key board )
 */
void sigint_handler(int sig)
 {
    printf("\nTotal number of operations completed successfully: %d\n", Success_Count);
    printf("The program will be terminated.\n");

    exit(0);
 }
/* (handle the exception from the segment fault  )
 *
 * (sig) (the signal from the segment fault need to be deal)
 *  receive the signal the terminate the program
 */
void sigfpe_handler(int sig) 
{
    printf("Error: a division by 0 operation was attempted.\n");
    printf("Total number of operations completed successfully: %d\n", Success_Count);
    printf("The program will be terminated.\n");

    exit(0);
}

/* the function to do the divide and the input is from the command line
 *
 * each time the divide success, global count increment, and print the statement that contains the result
 */
int main() 
{
    long dividend, divisor;
    char input[BUFFER_LEN];
    char* end;
    /* 
     * signal actions for SIGINT SIGFPE
     */
    struct sigaction sigint_action, sigfpe_action;

    memset(&sigint_action, 0, sizeof(sigint_action));
    sigint_action.sa_handler = sigint_handler;
    sigaction(SIGINT, &sigint_action, NULL);

    memset(&sigfpe_action, 0, sizeof(sigfpe_action));
    sigfpe_action.sa_handler = sigfpe_handler;
    sigaction(SIGFPE, &sigfpe_action, NULL);

    /* the loop to do the division*/
    while (1) 
    {
        /* parse dividend */
        printf("Enter first integer: ");
        memset(input, 0, sizeof(input));
        fgets(input, sizeof(input), stdin);
        dividend = strtol(input, &end, 10);

        /* parse divisor */
        printf("Enter second integer: ");
        memset(input, 0, sizeof(input));
        fgets(input, sizeof(input), stdin);
        divisor = strtol(input, &end, 10);

        printf("%ld / %ld is %ld with a remainder of %ld\n",
               dividend, divisor, dividend/divisor, dividend%divisor);

        /* Success_Count successful operation*/
        ++Success_Count;
    }

    return 0;
}
