////////////////////////////////////////////////////////////////////////////////
// Main File:        (mem.c)
// This File:        (mem.c)
// Other Files:      (name of all other files if any)
// Semester:         CS 354 Spring 2019
//
// Author:           (di bao)
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
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <stdio.h>
#include <string.h>
#include "mem.h"

/*
 * This structure serves as the header for each allocated and free block.
 * It also serves as the footer for each free block but only containing size.
 */
typedef struct block_header 
{
        int size_status;
    /*
    * Size of the block is always a multiple of 8.
    * Size is stored in all block headers and free block footers.
    *
    * Status is stored only in headers using the two least significant bits.
    *   Bit0 => least significant bit, last bit
    *   Bit0 == 0 => free block
    *   Bit0 == 1 => allocated block
    *
    *   Bit1 => second last bit 
    *   Bit1 == 0 => previous block is free
    *   Bit1 == 1 => previous block is allocated
    * 
    * End Mark: 
    *  The end of the available memory is indicated using a size_status of 1.
    * 
    * Examples:
    * 
    * 1. Allocated block of size 24 bytes:
    *    Header:
    *      If the previous block is allocated, size_status should be 27
    *      If the previous block is free, size_status should be 25
    * 
    * 2. Free block of size 24 bytes:
    *    Header:
    *      If the previous block is allocated, size_status should be 26
    *      If the previous block is free, size_status should be 24
    *    Footer:
    *      size_status should be 24
    */
} block_header;         

/* Global variable - DO NOT CHANGE. It should always point to the first block,
 * i.e., the block at the lowest address.
 */

block_header *start_block = NULL;


/* 
 * Function for allocating 'size' bytes of heap memory.
 * Argument size: requested size for the payload
 * Returns address of allocated block on success.
 * Returns NULL on failure.
 * This function should:
 * - Check size - Return NULL if not positive or if larger than heap space.
 * - Determine block size rounding up to a multiple of 8 and possibly adding padding as a result.
 * - Use best_fit-FIT PLACEMENT POLICY to find the block closest to the required block size
 * - Use SPLITTING to divide the chosen free block into two if it is too large.
 * - Update header(s) and footer as needed.
 * Tips: Be careful with pointer arithmetic.
 */
void* Alloc_Mem(int size)
 {
    if ((size <= 0) | (size > 8))
    {
        return NULL;
    }
    // Your code goes in here.
    //make the size to be a multiply of 8
    int new_size = (size + sizeof(block_header)) / 8;
    if ((size + sizeof(block_header)) % 8 != 0) 
    {
        // if the size is bigger than half of 8 increment 
        new_size ++;
    }
    new_size *= 8;//change to the neast size 
    
    //initilize all the start block and create the best-fit block and the compare status that need to use 
    block_header *begin_block = start_block;
    block_header *best_fit = NULL;
    int best_fistatus_size = -1, status_size, has_used;
    
    //iterate to find the best_fit block to fit in
    while (begin_block->size_status != 1)
     {
        //when find the best fit block then stop
        status_size = begin_block->size_status;
        has_used = 0;

        if (status_size & 0x01)
         {
            // if the current block is used block
            has_used = 1;
            status_size = status_size - 1;
        }

        if (status_size & 0x02)
         {
            // if previous block used
            status_size = status_size - 2;
        }

        if (has_used) 
        {
            begin_block = (block_header*)((char*)begin_block + status_size);
            //continure iteration
            continue;
        }

        // status_size is size of begin_block
        //compare the size and needed size if the need size is small than split 
        if ( new_size <= status_size) 
        {
            if (best_fit == NULL) 
            {
                best_fit = begin_block;
                best_fistatus_size = status_size;
            } else
             {
                // if the block size is bigger than fit it and change foot 
                if (best_fistatus_size > status_size)
                 {
                    best_fit = begin_block;
                    best_fistatus_size = status_size;
                }
            }
        }

        begin_block = (block_header*)((char*)begin_block + status_size);
    }
    //if there is no best fit block ,return null
    if (best_fit == NULL) 
    {
        return NULL;
    }
    //if the best fit block is bigger than needed and the different is 8
    if (best_fistatus_size - new_size >= 8)
     {
        // when best_fit size is too large, split
        // the remain size is the left size in the block 
        int remain_size = best_fistatus_size - new_size;
        best_fit->size_status -= remain_size;
        best_fit->size_status ++;
        //check the nect block 
        int next_one = ((block_header*)((char*)best_fit + best_fistatus_size))->size_status;
        //if the next block is not allocated change status
        if (!(next_one & 1)) 
        {
            ((block_header*)((char*)best_fit + new_size))->size_status = remain_size +
                2 + next_one;
            ((block_header*)((char*)best_fit + best_fistatus_size - sizeof(block_header) +
                next_one))->size_status = remain_size + next_one;
            // the next block is used
            
        } else
         {
            // the next block is not used, coalescing required
            ((block_header*)((char*)best_fit + new_size))->size_status = remain_size +
                2;
            ((block_header*)((char*)best_fit + best_fistatus_size - sizeof(block_header)))->size_status = remain_size;
            
        }
        
    } else
     {
        //when the different is not great 8
        best_fit->size_status ++;
        ((block_header*)((char*)best_fit + best_fistatus_size))->size_status += 2;
    }
    return (void*)best_fit + sizeof(block_header);
}

/* 
 * Function for freeing up a previously allocated block.
 * Argument ptr: address of the block to be freed up.
 * Returns 0 on success.
 * Returns -1 on failure.
 * This function should:
 * - Return -1 if ptr is NULL.
 * - Return -1 if ptr is not a multiple of 8.
 * - Return -1 if ptr is outside of the heap space. !!!!!!
 * - Return -1 if ptr block is already freed.
 * - USE IMMEDIATE COALESCING if one or both of the adjacent neighbors are free.
 * - Update header(s) and footer as needed.
 */                    
int Free_Mem(void *ptr) 
{         
    // Your code goes in here.
    // if the pointer is not exist 
    if(ptr == NULL)
    {
    	return -1;
	}
    // if the ptr is not a multiple of 8
    if((int)ptr % 8 != 0)
    {
    	return -1;
	}
    //fine the block header 
	
	block_header *current = ptr - sizeof(block_header);

    //if the pointer is freed 
    if (((current->size_status) & 0x01) == 0)
    {
        return -1;
    }

    // change the size of status to be allocated 
    current->size_status --;

    int current_status = current->size_status;
    int check_bit_1 = current_status & 0x02;
    //use the math method to find the size in the block header
    int current_size = current_status & (~7);

    //find the next header's adress
    block_header *next_header = current + current_size / 4;

    int next_status = next_header->size_status;
    int next_bit_1 = next_status & 0x01;
    //find the next header's size 
    int next_size = next_status & (~7);

    
    // when previous is allocated and the next block is free
    if ((check_bit_1 != 0) && (next_bit_1 == 0))
    {
        //updates next  footer
        block_header *next_foot = next_header + next_size / 4 - 1;

        //go to the next block to find coalesce block
        current->size_status += next_size;
        next_foot->size_status = (current->size_status) & ~0x7;

        return 0;
    }
    // when previous and next block are both free
    if ((check_bit_1 == 0) && (next_bit_1 == 0))
    {
       
        block_header *prevfoot = current - 1;
        int pre_size = prevfoot->size_status;
        block_header *prevhead = current - pre_size / 4;

        block_header *next_foot = next_header + next_size / 4 - 1;

        prevhead->size_status += current_size + next_size;
        next_foot->size_status = (prevhead->size_status) & ~0x7;
        
        return 0;
    }
    
    // when preious block is free and the next block is allocated
    if ((check_bit_1 == 0) && (next_bit_1 != 0))
    {
        //updates previous block's header and footer, and find current blocks's
        //header, so that we can use these to coalesce the nearby blocks
        block_header *prevfoot = current - 1;
        int pre_size = prevfoot->size_status;
        block_header *prevhead = current - pre_size / 4;
        block_header *curr_ftr = current + current_size / 4 - 1;

        // update new coalesced block's header and footer
        prevhead->size_status += current_size;
        curr_ftr->size_status = (prevhead->size_status) & ~0x7;

        //update the header of next block of new coalesced block
        if (next_header->size_status != 1)
        {
            next_header->size_status -= 2;
        }

        return 0;
    }
    // when both previous and next block is allocated
    if ((check_bit_1 != 0) && (next_bit_1 != 0))
    {
        //updates current block's footer
        block_header *curr_ftr = current + current_size / 4 - 1;

        //updates new footer and header of freed block
        curr_ftr->size_status = current_size;

        if (next_header->size_status != 1)
        {
            next_header->size_status -= 2;
        }

        return 0;
    }
    
    return -1;
}

/*
 * Function used to initialize the memory allocator.
 * Intended to be called ONLY once by a program.
 * Argument sizeOfRegion: the size of the heap space to be allocated.
 * Returns 0 on success.
 * Returns -1 on failure.
 */                    
int Init_Mem(int sizeOfRegion) 
{
    int pagesize;
    int padsize;
    int fd;
    int alloc_size;
    void *space_ptr;
    block_header *end_mark;
    static int allocated_once = 0;


    if (0 != allocated_once)
    {
        fprintf(stderr,
                "Error:mem.c: Init_Mem has allocated space during a previous call\n");
        return -1;
    }
    if (sizeOfRegion <= 0)
    {
        fprintf(stderr, "Error:mem.c: Requested block size is not positive\n");
        return -1;
    }

    // Get the pagesize
    pagesize = getpagesize();

    // Calculate padsize as the padding required to round up sizeOfRegion
    // to a multiple of pagesize
    padsize = sizeOfRegion % pagesize;
    padsize = (pagesize - padsize) % pagesize;

    alloc_size = sizeOfRegion + padsize;

    // Using mmap to allocate memory
    fd = open("/dev/zero", O_RDWR);
    if (-1 == fd)
    {
        fprintf(stderr, "Error:mem.c: Cannot open /dev/zero\n");
        return -1;
    }
    space_ptr = mmap(NULL, alloc_size, PROT_READ | PROT_WRITE, MAP_PRIVATE,
                     fd, 0);
    if (MAP_FAILED == space_ptr)
    {
        fprintf(stderr, "Error:mem.c: mmap cannot allocate space\n");
        allocated_once = 0;
        return -1;
    }

    allocated_once = 1;

    // for double word alignment and end mark
    alloc_size -= 8;

    // To begin with there is only one big free block
    // initialize heap so that start block meets
    // double word alignement requirement
    start_block = (block_header *)space_ptr + 1;
    end_mark = (block_header *)((void *)start_block + alloc_size);

    // Setting up the header
    start_block->size_status = alloc_size;

    // Marking the previous block as used
    start_block->size_status += 2;

    // Setting up the end mark and marking it as used
    end_mark->size_status = 1;

    // Setting up the footer
    block_header *footer = (block_header *)((char *)start_block + alloc_size - 4);
    footer->size_status = alloc_size;

    return 0;
}        
                 
/* 
 * Function to be used for DEBUGGING to help you visualize your heap structure.
 * Prints out a list of all the blocks including this information:
 * No.      : serial number of the block 
 * Status   : free/used (allocated)
 * Prev     : status of previous block free/used (allocated)
 * t_Begin  : address of the first byte in the block (where the header starts) 
 * t_End    : address of the last byte in the block 
 * status_size   : size of the block as stored in the block header
 */                     
void Dump_Mem() {         
    int counter;
    char status[5];
    char p_status[5];
    char *t_begin = NULL;
    char *t_end = NULL;
    int t_size;

    block_header *current = start_block;
    counter = 1;

    int used_size = 0;
    int free_size = 0;
    int is_used = -1;

    fprintf(stdout, "************************************Block list***\
                    ********************************\n");
    fprintf(stdout, "No.\tStatus\tPrev\tt_Begin\t\tt_End\t\tt_Size\n");
    fprintf(stdout, "-------------------------------------------------\
                    --------------------------------\n");
  
    while (current->size_status != 1) {
        t_begin = (char*)current;
        t_size = current->size_status;
    
        if (t_size & 1) {
            // LSB = 1 => used block
            strcpy(status, "used");
            is_used = 1;
            t_size = t_size - 1;
        } else {
            strcpy(status, "Free");
            is_used = 0;
        }

        if (t_size & 2) {
            strcpy(p_status, "used");
            t_size = t_size - 2;
        } else {
            strcpy(p_status, "Free");
        }

        if (is_used) 
            used_size += t_size;
        else 
            free_size += t_size;

        t_end = t_begin + t_size - 1;
    
        fprintf(stdout, "%d\t%s\t%s\t0x%08lx\t0x%08lx\t%d\n", counter, status, 
        p_status, (unsigned long int)t_begin, (unsigned long int)t_end, t_size);
    
        current = (block_header*)((char*)current + t_size);
        counter = counter + 1;
    }

    fprintf(stdout, "---------------------------------------------------\
                    ------------------------------\n");
    fprintf(stdout, "***************************************************\
                    ******************************\n");
    fprintf(stdout, "Total used size = %d\n", used_size);
    fprintf(stdout, "Total free size = %d\n", free_size);
    fprintf(stdout, "Total size = %d\n", used_size + free_size);
    fprintf(stdout, "***************************************************\
                    ******************************\n");
    fflush(stdout);

    return;
}         
