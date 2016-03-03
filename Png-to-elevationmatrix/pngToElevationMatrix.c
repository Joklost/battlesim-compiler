#include <stdio.h>
#include <stdlib.h>

#define MAX_MIDI_SIZE 1000000000000

void readPNGFile(FILE *fp);

int main(void){
  FILE *fp;
  fp = fopen("Heightmap.png", "r");
  if(fp == NULL)
	  printf("Could not open file");
  readPNGFile(fp);
}

void readPNGFile(FILE *fp){
	int i = 0;
	while(!feof(fp)){
		printf("%4X ", fgetc(fp));
	}
	fclose(fp);
}