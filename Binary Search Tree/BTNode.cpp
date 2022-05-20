#include <iostream>
#include "BTNode.h"

using namespace std;

BTNode::BTNode(type newItem) {
	item = newItem;
	left = right = NULL;
}
