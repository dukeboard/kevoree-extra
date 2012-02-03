/**********************************************************/
/* Serial Bootloader for ATMEGA328P  AVR Controller       */
/*                                                        */
/* Copyright (c) 2009,  Nathan Seidle                     */
/*                                                        */
/* Hacked 2012 by JeD jedartois@gmail.com                 */
/*                                                        */
/*                                                        */
/*                                                        */
/* This program is free software; you can redistribute it */
/* and/or modify it under the terms of the GNU General    */
/* Public License as published by the Free Software       */
/* Foundation; either version 2 of the License, or        */
/* (at your option) any later version.                    */
/*                                                        */
/* This program is distributed in the hope that it will   */
/* be useful, but WITHOUT ANY WARRANTY; without even the  */
/* implied warranty of MERCHANTABILITY or FITNESS FOR A   */
/* PARTICULAR PURPOSE.  See the GNU General Public        */
/* License for more details.                              */
/*                                                        */
/* You should have received a copy of the GNU General     */
/* Public License along with this program; if not, write  */
/* to the Free Software Foundation, Inc.,                 */
/* 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA */
/*                                                        */
/* Licence can be viewed at                               */
/* http://www.fsf.org/licenses/gpl.txt                    */
/*                                                        */
/* Target =  ATMEGA328P                                   */
/**********************************************************/

#include <avr/io.h>
#include <avr/boot.h>
#include <avr/interrupt.h>
#include <avr/pgmspace.h>
#include <util/delay.h>


#define MAX_SIZE_ID 5
const char NODE_ID[MAX_SIZE_ID]=  "NODE0";

#define BAUD   19200 //Works with internal osc
#define MYUBRR (((((CPU_SPEED * 10) / (16L * BAUD)) + 5) / 10) - 1)
#define CPU_SPEED	16000000
#define MAX_CHARACTER_WAIT	15
#define MAX_WAIT_IN_CYCLES ( ((MAX_CHARACTER_WAIT * 8) * CPU_SPEED) / BAUD )

#define TRUE	0
#define FALSE	1

//Function prototypes
void putch(char);
void putchKID(void);
char getch(void);
void onboard_program_write(uint32_t page, uint8_t *buf);
void (*app_start)(void) = 0x0000;


uint8_t incoming_page_data[256];
uint8_t page_length;
uint8_t retransmit_flag = FALSE;

union page_address_union {
	uint16_t word;
	uint8_t  byte[2];
} page_address;


#define SPM_PAGESIZE 128
void onboard_program_write(uint32_t page, uint8_t *buf)
{
	uint16_t i;
	uint8_t sreg;

	// Disable interrupts.

	sreg = SREG;
	cli();

	eeprom_busy_wait ();

	boot_page_erase (page);
	boot_spm_busy_wait ();

	for (i=0; i<SPM_PAGESIZE; i+=2)
	{
		// Set up little-endian word.

		uint16_t w = *buf++;
		w += (*buf++) << 8;

		boot_page_fill (page + i, w);
	}

	boot_page_write (page);     // Store buffer in flash page.
	boot_spm_busy_wait ();       // Wait until the memory is written.
	boot_rww_enable ();
	SREG = sreg;
}

void putchKID()
{
	uint8_t i=0;
	for(i=0;i< MAX_SIZE_ID;i++)
		putch(NODE_ID[i]);
}
void putch(char ch)
{
	while (!(UCSR0A & _BV(UDRE0)));
	UDR0 = ch;
}

char getch(void)
{
	retransmit_flag = FALSE;
	uint32_t count = 0;
	while(!(UCSR0A & _BV(RXC0)))
	{
		count++;
		if (count > MAX_WAIT_IN_CYCLES) //
		{
			retransmit_flag = TRUE;
			break;
		}
	}

	return UDR0;
}


int main(void)
{
	uint8_t check_sum = 0;
	uint16_t i;
	UBRR0H = MYUBRR >> 8;
	UBRR0L = MYUBRR;
	UCSR0B = (1<<RXEN0)|(1<<TXEN0);
	putch(5); 
	uint32_t count = 0;
	while(!(UCSR0A & _BV(RXC0)))
	{
		count++;
		if (count > MAX_WAIT_IN_CYCLES)
		{
			app_start();
		}
	}
	if(UDR0 != 6) app_start();

	putchKID();

	while(1)
	{

		if (check_sum != 0)
			RESTART:
			putch(7);
		else            
			putch('T');

		while(1)
		{
			if (getch() == ':') break;
			if (retransmit_flag == TRUE) goto RESTART;
		}

		page_length = getch();
		if (retransmit_flag == TRUE) goto RESTART;

		if(page_length == 'v')
		{
			putch('v');
			putch('0x1');
			putch('.');
			putch('0x0');
		}

		if(page_length == 'k')
		{
			putchKID();
		}

		if (page_length == 'S')
		{
			boot_rww_enable ();
			app_start(); 
		}

		page_address.byte[0] = getch(); if (retransmit_flag == TRUE) goto RESTART;
		page_address.byte[1] = getch(); if (retransmit_flag == TRUE) goto RESTART;

		check_sum = getch();
		if (retransmit_flag == TRUE) goto RESTART;

		for(i = 0 ; i < page_length ; i++)
		{
			incoming_page_data[i] = getch();
			if (retransmit_flag == TRUE) goto RESTART;
		}

		for(i = 0 ; i < page_length ; i++)
			check_sum = check_sum + incoming_page_data[i];

		check_sum = check_sum + page_length;
		check_sum = check_sum + page_address.byte[0];
		check_sum = check_sum + page_address.byte[1];

		if(check_sum == 0) //If we have a good transmission, put it in ink
			onboard_program_write((uint32_t)page_address.word, incoming_page_data);
	}

}

