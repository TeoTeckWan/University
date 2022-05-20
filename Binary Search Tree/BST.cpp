#include <iostream>
#include <fstream>
#include <algorithm>
#include "BST.h"


using namespace std;


BST::BST() {
	root = NULL;
	count = 0;
}


bool BST::empty() {
	if (count == 0) return true;
	return false;
}


int BST::size() {
	return count;
}


void BST::preOrderPrint() {
	if (root == NULL) return;// handle special case
	else preOrderPrint2(root);// do normal process
	cout << endl;
}
void BST::preOrderPrint2(BTNode *cur) {
	if (cur == NULL) return;
	cur->item.print(cout);
	preOrderPrint2(cur->left);
	preOrderPrint2(cur->right);
}

void BST::inOrderPrint() {
	if (root == NULL) return;// handle special case
	else inOrderPrint2(root);// do normal process
	cout << endl;
}
void BST::inOrderPrint2(BTNode *cur) {
	if (cur == NULL) return;
	inOrderPrint2(cur->left);
	cur->item.print(cout);
	inOrderPrint2(cur->right);
}

void BST::postOrderPrint() {
	if (root == NULL) return;// handle special case
	else postOrderPrint2(root);// do normal process
	cout << endl;
}
void BST::postOrderPrint2(BTNode *cur) {
	if (cur == NULL) return;
	postOrderPrint2(cur->left);
	postOrderPrint2(cur->right);
	cur->item.print(cout);
}


int BST::countNode() {
	int	counter = 0;
	if (root == NULL) return 0;
	countNode2(root, counter);
	return counter;
}
void BST::countNode2(BTNode *cur, int &count) {
	if (cur == NULL) return;
	countNode2(cur->left, count);
	countNode2(cur->right, count);
	count++;
}

bool BST::findGrandsons(type grandFather) {
	if (root == NULL) return false;
	return (fGS2(grandFather, root));
}
bool BST::fGS2(type grandFather, BTNode *cur) {
	if (cur == NULL) return false;
	//if (cur->item == grandFather) {
	if (cur->item.compare2(grandFather)){

		fGS3(cur, 0);// do another TT to find grandsons
		return true;
	}
	if (fGS2(grandFather, cur->left)) return true;
	return fGS2(grandFather, cur->right);
}
void BST::fGS3(BTNode *cur, int level) {
	if (cur == NULL) return;
	if (level == 2) {
		cur->item.print(cout);
		return;  // No need to search downward
	}
	fGS3(cur->left, level + 1);
	fGS3(cur->right, level + 1);
}

void BST::topDownLevelTraversal() {
	BTNode			*cur;
	Queue		    q;

	if (empty()) return; 	// special case
	q.enqueue(root);	// Step 1: enqueue the first node
	while (!q.empty()) { 	// Step 2: do 2 operations inside
		q.dequeue(cur);
		if (cur != NULL) {
			cur->item.print(cout);

			if (cur->left != NULL)
				q.enqueue(cur->left);

			if (cur->right != NULL)
				q.enqueue(cur->right);
		}
	}
}

//insert for BST
bool BST::insert(type newItem) {
	BTNode	*cur = new BTNode(newItem);
	if (!cur) return false;		// special case 1
	if (root == NULL) {
		root = cur;
		count++;
		return true; 			// special case 2
	}
	insert2(root, cur);			// normal
	count++;
	return true;
}
void BST::insert2(BTNode *cur, BTNode *newNode) {
	//if (cur->item > newNode->item) {
	if (cur->item.compare1(newNode->item)){
		if (cur->left == NULL)
			cur->left = newNode;
		else
			insert2(cur->left, newNode);
	}
	else {
		if (cur->right == NULL)
			cur->right = newNode;
		else
			insert2(cur->right, newNode);
	}
}

bool BST::remove(type item) {
	if (root == NULL) return false; 		// special case 1: tree is empty
	return remove2(root, root, item); 		// normal case
}
bool BST::remove2(BTNode *pre, BTNode *cur, type item) {

	// Turn back when the search reaches the end of an external path
	if (cur == NULL) return false;

	// normal case: manage to find the item to be removed
	//if (cur->item == item) {
	if (cur->item.compare2(item)){
		if (cur->left == NULL || cur->right == NULL)
			case2(pre, cur);	// case 2 and case 1: cur has less than 2 sons
		else
			case3(cur);		// case 3, cur has 2 sons
		count--;				// update the counter
		return true;
	}

	// Current node does NOT store the current item -> ask left sub-tree to check
	//if (cur->item > item)
	if (cur->item.compare1(item))
		return remove2(cur, cur->left, item);

	// Item is not in the left subtree, try the right sub-tree instead
	return remove2(cur, cur->right, item);
}

void BST::case2(BTNode *pre, BTNode *cur) {

	// special case: delete root node
	if (pre == cur) {
		if (cur->left != NULL)	// has left son?
			root = cur->left;
		else
			root = cur->right;

		free(cur);
		return;
	}

	if (pre->right == cur) {		// father is right son of grandfather? 
		if (cur->left == NULL)			// father has no left son?
			pre->right = cur->right;			// connect gfather/gson
		else
			pre->right = cur->left;
	}
	else {						// father is left son of grandfather?
		if (cur->left == NULL)			// father has no left son? 
			pre->left = cur->right;				// connect gfather/gson
		else
			pre->left = cur->left;
	}

	free(cur);					// remove item
}
void BST::case3(BTNode *cur) {
	BTNode		*is, *isFather;

	// get the IS and IS_parent of current node
	is = isFather = cur->right;
	while (is->left != NULL) {
		isFather = is;
		is = is->left;
	}

	// copy IS node into current node
	cur->item = is->item;

	// Point IS_Father (grandfather) to IS_Child (grandson)
	if (is == isFather)
		cur->right = is->right;		// case 1: There is no IS_Father    
	else
		isFather->left = is->right;	// case 2: There is IS_Father

	// remove IS Node
	free(is);
}



bool BST::deepestNodes() {
	int maxHeight = 0;
	int deepID[10] = { 0 }; 
	int i = 0; // For array index

	if (root == NULL) {
		return false; // Empty tree
	}
	else {
		cout << "\n DEEPEST NODE OF THE TREE" << endl;
		cout << "\nDeepest node: ";

		deepestNodes2(root, 0, maxHeight, deepID, i);

		for (int j = 0; j < i; j++) { // Print Deepest Node(s)
			cout << deepID[j] << "   ";
		}
		cout << endl;
		return true;
	}
}
void BST::deepestNodes2(BTNode *cur, int height, int& maxHeight, int deepID[], int& i) {
	if (cur == NULL) {
		return;
	}

	deepestNodes2(cur->left, ++height, maxHeight, deepID, i);

	if (height >= maxHeight) { // Allow same or greater height node to come in 
		if (height > maxHeight) { // Set max height for deepest node and reset the array index
			maxHeight = height;
			i = 0; // This means the only print the deepest node
		}
		deepID[i] = cur->item.id;
		i++; // This can print more than 1 node in the same max height
	}

	deepestNodes2(cur->right, height, maxHeight, deepID, i);
}

bool BST::display(int order, int source) {
	ofstream file1;

	if (root == NULL) { // Tree is empty
		return false;
	}
	else {
		cout << "\n DISPLAY OUTPUT\n" << endl;
		cout << "What order (according to student id) do you want to display (1 - Ascending / 2 - Descending): ";
		cin >> order;
		cout << "Where do you want to display the output (1 - Screen / 2 - File): ";
		cin >> source;

		if (order == 1) { // Ascending
			if (source == 1) { // Screen
				inOrderPrint();
			}
			else if (source == 2) { // File
				file1.open("student-info.txt");
				display2(root, file1);
				cout << "\nFile is created with student info.\n" << endl;
				file1.close();
			}
			else {
				cout << "\nInvalid input source. Please try it again.\n" << endl;
			}
		}
		else if (order == 2) { // Descending
			if (source == 1) { // Screen
				display3(root, file1, source);
				cout << endl;
			}
			else if (source == 2) { // File
				file1.open("student-info.txt");
				display3(root, file1, source);
				cout << "\nFile is created with student info.\n" << endl;
				file1.close();
			}
			else {
				cout << "\nInvalid input source. Please try it again.\n" << endl;
			}
		}
		else {
			cout << "\nInvalid input order. Please try it again.\n" << endl;
		}
		return true;
	}
}
void BST::display2(BTNode *cur, ofstream& file) { // Ascending and print it into file
	if (cur == NULL) {
		return;
	}
	
	if (file.is_open()) {
		display2(cur->left, file);
		cur->item.print(file);
		display2(cur->right, file);
	}
}
void BST::display3(BTNode *cur, ofstream& file, int &source) { // Descending and print it on screen or file

	if (cur == NULL) { 
		return; 
	}

	if (source == 1) {
		display3(cur->right, file, source);
		cur->item.print(cout);
		display3(cur->left, file, source);
	}
	else {
		if (file.is_open()) {
			display3(cur->right, file, source);
			cur->item.print(file);
			display3(cur->left, file, source);
		}
	}
}

bool BST::CloneSubtree(BST t1, type item) {
	bool found = false;

	if (t1.root == NULL) { // tree1 is empty
		return false;
	}
	cout << "\n CLONE THE SUBTREE" << endl;
	CloneSubtree2(t1.root, item); // Function for cloning whole t1 to t2

	if (root->item.id == item.id) { // t2 same with t1
		return true;
	}

	CloneSubtree3(root, item, found); // Function for find the subtree according to id

	if (found) { // To track that the item is found in the list or not
		return true;
	}
	else {
		return false;
	}
}
void BST::CloneSubtree2(BTNode *cur, type item) { // Function for cloning whole t1 to t2
	if (cur == NULL) {
		return;
	}

	insert(cur->item);
	CloneSubtree2(cur->left, item);
	CloneSubtree2(cur->right, item);
}
void BST::CloneSubtree3(BTNode *cur, type item, bool &found) { // Function for find the subtree according to id and clone to t2
	if (cur == NULL) {
		return;
	}

	if (cur->item.id == item.id) {
		root = cur;
		found = true;
		return;
	}

	CloneSubtree3(cur->left, item, found);
	CloneSubtree3(cur->right, item, found);
}

bool BST::printAncestor(type item) {
	cout << "\n PRINT ANCESTOR(S)\n" << endl;
	cout << "Ancestor(s): ";

	if (root == NULL) {
		return false; // Empty tree
	}

	if (root->item.id == item.id) { // Root is the item
		cout << "None" << endl;
		cout << "There is not ancestor for this item." << endl;
		return true;
	}
	return (printAncestor2(root, item));
}
bool BST::printAncestor2(BTNode *cur, type item) {
	if (cur == NULL) {
		return false;
	}

	if (cur->item.id == item.id) {
		return true;
	}

	// If the item is the next left or right node, print this node
	if (printAncestor2(cur->left, item) || printAncestor2(cur->right, item)) {
		cout << cur->item.id << "   ";
		return true;
	}

	return false;
}

bool BST::printSpiral() {
	int maxLevel = 0; 
	
	cout << "\n PRINT SPIRAL\n" << endl;
	if (root == NULL) {
		return false;
	}
	maxHeight(root, 0, maxLevel); // Find max height first

	bool ltr = false; // ltr means Left to Right

	for (int i = 1; i <= maxLevel; i++) {
		printSpiral2(root, i, ltr);
		ltr = !ltr; // Swift direction (LTR or RTL) for each time
	}
	cout << "\n\n";
	return true;
}
void BST::maxHeight(BTNode *cur, int height, int &maxLvl) { // Find max height of tree
	if (cur == NULL) {
		return;
	}

	maxHeight(cur->left, ++height, maxLvl);
	if (height > maxLvl) {
		maxLvl = height;
	}
	maxHeight(cur->right, ++height, maxLvl);
}
void BST::printSpiral2(BTNode *cur, int level, bool ltr) { // Print Spiral 
	if (cur == NULL) {
		return;
	}

	if (level == 1) {
		cout << cur->item.id << "   ";
	}

	if (level > 1) {
		if (ltr) { // Left to Right
			printSpiral2(cur->left, level - 1, ltr);
			printSpiral2(cur->right, level - 1, ltr);
		}
		else { // Right to Left
			printSpiral2(cur->right, level - 1, ltr);
			printSpiral2(cur->left, level - 1, ltr);
		}
	}
}
