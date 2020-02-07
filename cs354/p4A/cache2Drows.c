////////////////////////////////////////////////////////////////////////////////
// Main File:        cache2Drows.c
// This File:        cache2Drows.c
// Other Files:      (name of all other files if any)
// Semester:         CS 354 SPRING 2019
//           
// Author:           Di Bao
// Email:            dbao5@wisc.edu
// CS Login:         dbao
//           
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//           
// Persons:           myself
//           
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
////////////////////////////////////////////////////////////////////////////////

#include <stdio.h> 
#include <stdlib.h> 
 
int arr2D[3000][500];
int main(int argc, char *argv[]){
        for(int row=0;row<3000;row++){
          for(int col=0;col<500;col++){
             arr2D[row][col] = row + col;
	}
	}
}
