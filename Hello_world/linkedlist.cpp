#include <iostream>
using namespace std;

class Node {
    public:
        int data; //Stores data in teh node
        Node* next; //Pointer to the next node

        //constructor to initialize
        Node(int value) {
            data = value;
            next = nullptr; //Intiliaze the pointer to the next node
        }
};

class LinkedList {
    private:
        Node* headNode; // pointer to the first node in the likedlist

    public:
        //Constructor
        LinkedList() {
            headNode = nullptr //Initialize the list as empty list 
        }
        //methode to insert as the begginning of the node
        Void insertAtBeginning(int value) {

            Node* newNodeToInsert = new Node(value); // Step 1: create a new node
            newNodeToInsert->next = headNode;   // Step 2:  pointt new node to teh current head
            headNode = newNodeToInsert;  // step 3: Update head to the new node
        }

        // void traverse() {
        //     Node* current = head; //Start from the head
        //     while (current != nullptr) {
        //         cout << current->data << "-> ";
        //         current = current->next // move the next node
        //     }
        //     cout << "nullptr" << endl; //End of list
        // }
};


