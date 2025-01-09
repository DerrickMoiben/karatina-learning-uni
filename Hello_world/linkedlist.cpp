// #include <iostream>
// using namespace std;

// class Node {
//     public:
//         int data; //Stores data in teh node
//         Node* next; //Pointer to the next node

//         //constructor to initialize
//         Node(int value) {
//             data = value;
//             next = nullptr; //Intiliaze the pointer to the next node
//         }
// };

// class LinkedList {
//     private:
//         Node* headNode; // pointer to the first node in the likedlist

//     public:
//         //Constructor
//         LinkedList() {
//             headNode = nullptr //Initialize the list as empty list 
//         }
//         //methode to insert as the begginning of the node
//         Void insertAtBeginning(int value) {

//             Node* newNodeToInsert = new Node(value); // Step 1: create a new node
//             newNodeToInsert->next = headNode;   // Step 2:  pointt new node to teh current head
//             headNode = newNodeToInsert;  // step 3: Update head to the new node
//         }

//         // void traverse() {
//         //     Node* current = head; //Start from the head
//         //     while (current != nullptr) {
//         //         cout << current->data << "-> ";
//         //         current = current->next // move the next node
//         //     }
//         //     cout << "nullptr" << endl; //End of list
//         // }
// };
#include <iostream>
using namespace std;

class Node{
public:
    int value;
    Node* next;

    Node(int number) {
        number = value;
        next =  nullptr;
    }
};

// void printlist(Node*n){
//     while (n != NULL)
//     {
//         /* code */
//         cout << n->value << endl;
//         n = n->next;
//     }

    
// }

class Linkedlist{
    private:
        Node* head;
    public:
        Linkedlist() {
            head = nullptr;
        }

    void addvalue(int number) {
        Node*  newNode = new Node(number);
        if (head == nullptr) {
            head = newNode;
        } else {
            Node* temp = head;
            while (temp->next != nullptr) {
                temp = temp->next;
            }
            temp->next =newNode;
        }
    }
    void printlist() {
        Node* temp = head;
        while (temp != nullptr)
        {
            cout << temp->value << "->";
            temp = temp->next;
        }
        cout << "NULL" << endl;
        
    }
};
 
int main(){
//     Node* head = new Node();
//     Node* second = new Node();
//     Node* third = new Node();

//     head->value = 1;
//     head->next = second;
//     second->value = 2;
//     second->next = third;
//     third->value = 3;
//     third->next = NULL;

//     printlist(head);
// }



    //linked list object
    Linkedlist list;

    list.addvalue(1);
    list.addvalue(2);

    list.printlist();

    return 0;
}