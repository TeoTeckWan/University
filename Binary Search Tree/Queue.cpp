#include <iostream>
#include "Queue.h"

using namespace std;



Queue::Queue() {
	head = tail = NULL;
	count = 0;
}


bool Queue::empty() {
	if (count == 0) return true;
	return false;
}


int Queue::size() {
	return count;
}


bool Queue::enqueue(type2 newItem) {// Any simplification can be done on code below?
	Node	*tmp = new Node(newItem);

	if (!tmp) return false;
	if (empty()) {
		head = tail = tmp;
		count++;
		return true;
	}
	count++;
	tail->next = tmp;
	tail = tmp;
	return true;
}


bool Queue::dequeue(type2 &itemExtracted) {
	Node	*cur = head;

	if (empty()) return false;
	count--;
	itemExtracted = head->item;
	head = head->next;
	free(cur);
	if (count == 0) tail = NULL;
	return true;
}


Node *Queue::find(type2 record) {
	Node	*cur;

	cur = head;
	for (; cur != NULL;) {
		if (cur->item == record) return cur;
		cur = cur->next;
	}
	return NULL;
}
