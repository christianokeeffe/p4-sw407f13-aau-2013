#include<stdio.h>

void swap(int *a, int *b)
{
  int t=*a; *a=*b; *b=t;
}
void sort(int arr[], int beg, int end)
{
  if (end > beg + 1)
  {
    int piv = arr[beg], l = beg + 1, r = end;
    while (l < r)
    {
      if (arr[l] <= piv)
        l++;
      else
        swap(&arr[l], &arr[--r]);
    }
    swap(&arr[--l], &arr[beg]);
    sort(arr, beg, l);
    sort(arr, r, end);
  }
}

void nosort(int arr[], int beg, int end)
{
  if (end > beg + 1)
  {
    int piv = arr[beg], l = beg + 1, r = end;
    while (l < r)
    {
      if (arr[l] <= piv)
        l++;
      else
        swap(&arr[l], &arr[--r]);
    }
    swap(&arr[--l], &arr[beg]);
  }
}

main()
{
	int Age[] = {1,31,13,12,14,12,1,4,41,13,31,4,1,4,1,1,3,13,1,312,4,12,41,2};
	int j= 0;
	for(j=0; j<= (sizeof(Age)/4)-1; j++)
	{	
		int k=j;
		
		if(j >= (sizeof(Age)/4)-3)
		{
			k=0;
		}
		
		nosort(Age, Age[k], Age[k+1]);
	}
	
	int i=0;
	for(i=0; i<= (sizeof(Age)/4)-1; i++)
	{
		printf("%d", Age[i]);
		printf("\n");
	}
}
