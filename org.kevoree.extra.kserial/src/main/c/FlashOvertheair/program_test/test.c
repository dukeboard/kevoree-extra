#include <stdio.h>
#include <avr/io.h>

#define sbi(port, pin)   ((port) |= (uint8_t)(1 << pin))
#define cbi(port, pin)   ((port) &= (uint8_t)~(1 << pin))

#define FOSC 16000000 //8MHz internal osc
//#define FOSC 1000000 //1MHz internal osc
#define BAUD 19200
#define MYUBRR (((((FOSC * 10) / (16L * BAUD)) + 5) / 10) - 1)

#define STATUS_LED	5 //PORTB

#define CTS		2 //This is an input from the XBee. If low, XBee is busy - wait.
#define RTS		3 //This is an output to the XBee. If we're busy, pull line low to tell XBee not to transmit characters into ATmega's UART

#define TRUE	1
#define FALSE	0


void (*bootloader)(void) = 20780000;

void ioinit (void);
void delay_ms(uint16_t x); // general purpose delay
void delay_us(uint8_t x);

static int uart_putchar(char c, FILE *stream);
char getch(void);
static FILE mystdout = FDEV_SETUP_STREAM(uart_putchar, NULL, _FDEV_SETUP_WRITE);

int main (void)
{

    ioinit(); //Boot up defaults

	uint8_t my_ch, x = 0;

	//Blinky!
	for(x = 0 ; x < 10 ; x++)
	{
		sbi(PORTB, STATUS_LED);
		delay_ms(200);

		cbi(PORTB, STATUS_LED);
		delay_ms(200);
	}

	printf("\n\r  JEAN-EMILE  \n\r");

	while(1)
	{
		my_ch = getch();

		if(my_ch == 'r'){
			printf("Request auto reset \n");
			bootloader();
		}
		else
			printf("You pressed: %c\n\r", my_ch);
	}

    return(0);
}

void ioinit(void)
{
    //1 = output, 0 = input
    DDRB = 0b11111111;
    DDRC = 0b11111111;
    DDRD = 0b11111111;

	//DDRD &= ~(1<<CTS); //Set CTS as input
	//PORTD |= (1<<CTS); //Enable pull-up CTS

	//DDRD |= (1<<RTS); //Set RTS as output
	//sbi(PORTD, RTS); //Tell XBee to hold serial characters, we are busy doing other things

    //Setup USART baud rate
    UBRR0H = MYUBRR >> 8;
    UBRR0L = MYUBRR;
    UCSR0B = (1<<RXEN0)|(1<<TXEN0);

    stdout = &mystdout; //Required for printf init

    //Init Timer0 for delay_us
    //TCCR0B = (1<<CS00); //Set Prescaler to clk/1 (assume we are running at internal 1MHz). CS00=1
    TCCR0B = (1<<CS01); //Set Prescaler to clk/8 : 1click = 1us(assume we are running at internal 8MHz). CS01=1
}

//General short delays
void delay_ms(uint16_t x)
{
	for (; x > 0 ; x--)
	{
		delay_us(250);
		delay_us(250);
		delay_us(250);
		delay_us(250);
	}
}

//General short delays
void delay_us(uint8_t x)
{
	TIFR0 = 0x01; //Clear any interrupt flags on Timer0

    TCNT0 = 256 - x; //256 - 125 = 131 : Preload Timer0 for x clicks. Should be 1us per click

	while( (TIFR0 & (1<<TOV0)) == 0);
}

static int uart_putchar(char c, FILE *stream)
{
    if (c == '\n') uart_putchar('\r', stream);



    loop_until_bit_is_set(UCSR0A, UDRE0);
    UDR0 = c;

    return 0;
}

char getch(void)
{

	cbi(PORTD, RTS);

	uint32_t count = 0;
	while(!(UCSR0A & _BV(RXC0)))
	;

	return UDR0;

	sbi(PORTD, RTS);


}