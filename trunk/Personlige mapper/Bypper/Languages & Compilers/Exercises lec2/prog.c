#include<stdio.h>

typedef struct
{
	char* Name;
	int Age;
	float GPA;
	char* Grade_level;
} student;

main()
{
	student Students[100];
	
	Students[0].Name = "Rasmus";
	Students[0].Age = 20;
	Students[0].GPA = 100.00;
	Students[0].Grade_level = "Godlike";

	printf(Students[0].Name);
		printf("\n");
	printf("%d", Students[0].Age);
		printf("\n");
	printf("%f", Students[0].GPA);
		printf("\n");
	printf(Students[0].Grade_level);
		printf("\n");
}
