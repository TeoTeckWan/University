#ifndef Queue_type
#define Queue_type

#include	"Node.h"

struct Queue {
		int		count;
		Node	*head;
		Node	*tail;
		Queue();
		bool empty();
		int size();
		bool enqueue(type2); 
		bool dequeue(type2 &);
		Node	*find(type2);
};


#endif