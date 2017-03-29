




#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <io.h>



void test_malloc(){
	int max; 
	double * ptd;
	puts("what the max number of type double entries?");
	//scanf_s("%d", ptd); 
	scanf_s("%d", &max);
	int counti = 0;
	size_t  i; 
	if (ptd = (double *)malloc(max* sizeof(double))){
		puts("Enter the values(q to quit):");
		while (counti < max && scanf("%lf", & ptd[counti]) == 1)
			counti++;
		printf("Here is your %d entries : \n", counti);
		for ( i = 0; i < counti; i++)
		{
			printf("%7.2f ", ptd[i]);
			if ( i % 7==6)
				puts("\n");
		}
		puts("\n");
		puts("DONE \n");
		free(ptd);
		return  0;
	}
	else{
		puts("Memory allocation failed .Goodbye!");
		exit(EXIT_FAILURE);
	}

}
