#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct WebPage {
    char pageName[50];
    char pageURL[100];
    struct WebPage* next;
};

void push(struct WebPage** WebPageStack, struct WebPage* newWebPage);
void pop(struct WebPage** WebPageStack);

struct WebPage* getWebPage( ) {

    struct WebPage* webPage = malloc(sizeof(struct WebPage));
    char webPageName[50];
    char webPageURL[100];
    char flushFGets[10];

    // using this because I'm assuming fgets is picking up a \n from when I grab the meun input
    fgets(flushFGets, sizeof(flushFGets), stdin);

    printf("Enter WebPage ");
    // scanf("%[^\n]s", webPageName);
    fgets(webPageName, sizeof(webPageName), stdin);
    printf("Enter WebPageURL ");
    // scanf("%[^\n]s", webPageURL);
    fgets(webPageURL, sizeof(webPageURL), stdin);
    webPageName[strcspn(webPageName, "\n")] = '\0';
    webPageURL[strcspn(webPageURL, "\n")] = '\0';

    strcpy(webPage->pageName, webPageName);
    strcpy(webPage->pageURL, webPageURL);
    webPage->next = NULL;
    
    return webPage;

}


// this is where we push something onto the stack
void push (struct WebPage** webPageStack, struct WebPage* newWebPage) {
    // printf("Pushing onto the stack\n");

    struct WebPage* tempPointer = *webPageStack; // something to help us traverse the stack

    // case where the stack is empty
    if (*webPageStack == NULL) {
        *webPageStack = newWebPage;
        return;
    }

    // traverse the stack until we find the end
    while (tempPointer->next != NULL) {
        // printf("Trying to find the end of the stack");
        tempPointer = tempPointer->next;
    }

    // add the new page on the top of the stack
    tempPointer->next = newWebPage;

}


// this is where we pop something off of the stack
void pop (struct WebPage** webPageStack) {
    // printf("Popping off of the stack\n");

    struct WebPage* tempPointer = *webPageStack;
    struct WebPage* previousPointer = NULL;

    // find the end of the stack and remove the last element
    while (tempPointer != NULL) {

        if (tempPointer->next == NULL) {
            // we have the element to remove
            // printf("Removing element %s", tempPointer->pageName);
            
            // check for case of only one element
            if (previousPointer == NULL) {
                *webPageStack = NULL;
                free(tempPointer);
                return;
            }
            
            previousPointer->next = NULL;
            free(tempPointer);
        }
        
        previousPointer = tempPointer;
        tempPointer = tempPointer->next;
    }

}




// function to print the stack
void printStack(struct WebPage* webPageStack) {

    if (webPageStack == NULL) {
        printf("\nThe stack is empty\n");
        return;
    }

    while (webPageStack != NULL) {
        printf("\nWeb Page %s", webPageStack->pageName);
        printf("\nWeb Page URL %s\n", webPageStack->pageURL);
        webPageStack=webPageStack->next;
    }

}

// count the elements in the stack and return the number
int getStackSize(struct WebPage* webPageStack) {

    int stackSize = 0;

    while (webPageStack != NULL) {
        webPageStack = webPageStack->next;
        stackSize++;
    }

    return stackSize;
}

void freeStack(struct WebPage* webPageStack) {

    struct WebPage* tempPointer = webPageStack;
    
    while (webPageStack != NULL) {
        tempPointer = webPageStack;
        webPageStack = webPageStack->next;
        free(tempPointer);
    }

}

// main function which will hold onto the stack and bring up the menu options
int main() {

    struct WebPage* webPageStack = NULL;
    int command = -1;

    while (command != 0) {

        printf("\nCommand List:\n 1 - Push \n 2 - Pop \n 3 - Print Stack \n 4 - Print Stack Size \n 0 - Exit Program ");
        scanf("%d", &command);

        if (command == 1) {
            push(&webPageStack, getWebPage());
        } else if (command == 2) {
            pop(&webPageStack);
        } else if (command == 3) {
            printStack(webPageStack);
        } else if (command == 4) {
            printf("\nThere are %d element(s) on the stack\n", getStackSize(webPageStack));
        }  
        
        else if (command != 0 ) {
            printf("Invalid command\n");
        }

    }

    // free any memeory that is still in use before the program exits
    freeStack(webPageStack);
    
    return 1;
}