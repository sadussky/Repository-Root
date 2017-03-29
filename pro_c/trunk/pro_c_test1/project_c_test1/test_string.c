





/*
1. gets() 交互式程序非常方便，读取换行符之前(不包括换行符)所有字符，
并在这些字符后面添加'\0',然后把这个字符串交给调用的程序。它将读取
换行符并丢弃。


2. 

Error	
1	error C4996: 'gets': This function or variable may be unsafe.
Consider using gets_s instead. To disable deprecation, 
use _CRT_SECURE_NO_WARNINGS. See online help for details.	
d:\workspace\sync_github\repository\trunk\code\c\ws_visualstudio2013\
project_c_test1\project_c_test1\test_string.c	20	1	project_c_test1



*/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAX_INPUT 100 

void test_string_input(){
	char name[90]; 
	char pass[90];
	char test_inputs1[MAX_INPUT];


	//gets()函数 |START|-----------------------

	do{
		printf("tips: please input you name : \n", name);
	} while (gets(name) == NULL);
	 
	do{
		printf("tips: please input you password : \n", name);
	} while (gets(pass) == NULL);

	//gets()如果读取正常的话就返回传入的指针参数，如果读取出错就返回NULL指针。 


	/*while (gets(name) == NULL)
		printf("tips: please input you name : \n", name);
	while (gets(pass) == NULL)
		printf("tips: please input you password : \n", pass);*/

	printf("your name is %s : \n", name);
	printf("your password  is %s : \n", pass);

	//fgets() 函数 |START|-----------------------
	fgets(test_inputs1, MAX_INPUT, stdin);
	printf("your test_inputs1  is %s : \n", test_inputs1);




}


void test_string_output(){
	//C 有三个用于字符串输出的函数 puts(), fputs(),和printf(); 


}



#define MAXLINE 100

void test_string_fuc(){


	// strlen(), strcat(),strncat(),strcmp(),strncmp(),strcpy(),strncpy()
	char target[20];
	//target = "";
	for (size_t i = 0; i < 20 ; i++)
	{
		target [i]= 'c';
	}

 
	char subStr[MAXLINE] = "source";
	char *subStr2 = "source";
	char *source_target = "for_find_source_";

	char * found1 = NULL;
	char * found2 = NULL;

	if ( (found1=strstr(source_target, subStr)) != NULL){
		printf("haha,i have found the str:: \"source\",\n", subStr);
		printf("with the found:: %s\n", found1);
	}
	if ((found2 = strstr(source_target, subStr2) )!= NULL){
		printf("haha,i have found the str:: \"source\",\n", subStr2);
		printf("with the found:: %s\n", found2);
	}


}


#define SORT_LEN 20
#define SORT_ARRAY_LEN 80 
#define SORT_INPUT_HALT '\0'

void test_str_sort(){
	void str_sort(char *strsort[], int length);
	char str_for_sort[SORT_LEN][SORT_ARRAY_LEN];
	char *strsort[SORT_LEN];
	int count = 0; 
	while (count < SORT_LEN && gets(str_for_sort[count]) != NULL && str_for_sort[count][0] != SORT_INPUT_HALT)
	{
		strsort[count] = str_for_sort[count];

		count++;
	}
	str_sort(strsort, count);
	printf("after sort --------------------\n");
	for (size_t i = 0; i < count; i++)
	{
		printf("strsort[%d]:: %s\n", i, strsort[i] );
	}
}

void str_sort(char *strsort[], int length){
	char *  tmp;
	for (size_t  i= 0; i < length; i++)
	{
		for (size_t j = i +1 ; j < length; j++)
		{
			if (strcmp(strsort[i], strsort[j]) > 0){
				tmp = strsort[j];
				strsort[j] = strsort[i];
				strsort[i] = tmp;
			}	
		}
	}
}

