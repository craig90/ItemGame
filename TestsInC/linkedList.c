#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Record {
    char name[30];
    int age;
    struct Record *next;
};

// function declarations
void menu(struct Record* recordList);
struct Record* createRecord(char name[30], int age);
void addRecord(struct Record **recordList);
void removeRecord(struct Record** recordList);
void printList(struct Record *recordList);
void freeList(struct Record *recordList);

int main () {

    struct Record *list = NULL;

    // for now lets just popultate the list
    // list = createRecord("Craig", 35);
    // list->next = createRecord("Jerf", 35);
    // list->next->next = createRecord("Meg", 34);
    printList(list);
    
    menu(list);

    return 0;
}


// main menu for the program - get user input and act accordingly
void menu (struct Record* recordList) {

    int response = -1;
    // struct Record* recordList = NULL;

    while (response != 0) {
        
        printf("\nChoose an option\n");
        printf("Add record - 1\n");
        printf("Remove record - 2\n");
        printf("Print List of Records - 3\n");
        printf("Exit program - 0\n");

        scanf("%d", &response);

        if (response == 1) {
            addRecord(&recordList);
            printf("Added recored age: %d\n", recordList->age);
        } else if (response == 2) {
            removeRecord(&recordList);
        } else if (response == 3) {
            printList(recordList);
        } else if (response == 0) {
            freeList(recordList);
        } else {
            printf("Invalid input\n");
        }


    }

}


// traversthrough the list and print it out
void printList(struct Record *recordList) {

    if (recordList == NULL){
        printf("The list is empty\n");
        return;
    }

    while (recordList !=NULL) {
        printf("Name: %s \nAge: %d \n\n", recordList->name, recordList->age);
        recordList=recordList->next;
    }

}

// free everything, we don't want to be leaking memeory
void freeList(struct Record *recordList) {

    struct Record *freePointer = recordList;

    while(freePointer != NULL) {
        printf("Freeing record %s\n", recordList->name);
        freePointer = recordList->next;
        free(recordList);
        recordList = freePointer;
    }

}

// Where we create the struct and fill it with data
struct Record* createRecord(char name[30], int age) {
    
    struct Record *newRecord;
    newRecord = malloc(sizeof(struct Record));
    newRecord->age = age;
    strcpy(newRecord->name,name);
    newRecord->next = NULL;

    return newRecord;
    
}

void addRecord (struct Record **recordList) {

    char name[30];
    int age;
    
    printf("Enter the name of the person you'd like to add ");
    scanf("%s", name);
    printf("Enter the persons age ");
    scanf("%d", &age);

    struct Record* newRecord = createRecord(name, age);    
    printf("Adding New Record %s %d\n", newRecord->name, newRecord->age);

    // check if the list exists
    if (*recordList == NULL) {
        // no list, add to beginning
        // printf("Adding to the beginning of the list\n");
        *recordList = newRecord;
        // printf("Added age %d\n", (*recordList)->age);
        return;
    }
 
    // temp pointer to traverse the list - set it to the head of the list
    struct Record* tempPointer = *recordList;  

    // traverse the list and add the record to it
    while (tempPointer->next != NULL) {
        // printf("Traversing Linked List - looking to add at the end\n");
        tempPointer = tempPointer->next;
    }
    
    // add the new record on the end of the list
    tempPointer->next = newRecord;

}

void removeRecord(struct Record** recordList) {

    char nameToRemove[30];
    printf("Enter the name to remove ");
    scanf("%s", nameToRemove);

    struct Record* previousRecord = NULL;
    struct Record* recordToRemove = NULL;
    struct Record* tempPointer = *recordList; // temp pointer to walk through the list

    // scan through the list to find the record we want to remove
    while(tempPointer != NULL) {
        if (strcmp(tempPointer->name, nameToRemove) == 0){
            printf("Found the record to remove %s \n", tempPointer->name);
            recordToRemove = tempPointer;

            // check if we'er removing the first node
            if (previousRecord == NULL) {
                *recordList = (*recordList)->next;
            } else { // middle and end case 
                previousRecord->next = tempPointer->next;
            }            

            free(recordToRemove);
            return;
        }
        
        previousRecord = tempPointer;
        tempPointer = tempPointer->next;
        
    }

    printf("No match for record %s, make sure you have it spelt correctly\n", nameToRemove);

}
