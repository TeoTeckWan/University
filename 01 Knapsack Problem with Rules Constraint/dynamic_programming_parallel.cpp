#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <tuple>
#include <sstream>
#include <algorithm> 
#include <bitset>
#include <omp.h>

using namespace std;

struct Item {
	string name = "";
	int weight = 0;
	int value = 0;
	unsigned int bitmask = 0;
};

unsigned int nameToBitmask(const string& name) {
	unsigned int bitmask = 0;
	for (char c : name) {
		bitmask |= 1U << (c - 'A'); // Assuming 'A' to 'Z'
	}
	return bitmask;
}

vector<int> inputFile(string& filePath, vector<Item>& items) {
	ifstream inFile(filePath);
	string line;
	int containerSize = 0;
	int itemSize = 0;
	int rulesSize = 0;

	if (inFile.is_open()) {
		// Read container size
		getline(inFile, line); // Skip "ContainerSize:" line
		inFile >> containerSize;
		inFile.ignore(numeric_limits<streamsize>::max(), '\n'); // Skip to the next line

		// Read items
		getline(inFile, line); // Skip "Items:" line
		Item tempItem;
		while (getline(inFile, line) && line.substr(0, 5) != "Rules") { // Read until "Rules" encountered
			if (line.empty()) continue; // Skip any empty lines
			stringstream ss(line); // This allow to extract individual values from one line
			if (ss >> tempItem.name >> tempItem.weight >> tempItem.value) {
				tempItem.bitmask = nameToBitmask(tempItem.name); // Convert into bitmask
				items.push_back(tempItem); // Store into vector
				itemSize++;
			}
		}
		// All logic sames goes to reading rules
		// Read rules
		while (getline(inFile, line)) {
			stringstream ss(line);
			if (ss >> tempItem.name >> tempItem.weight >> tempItem.value) {
				tempItem.bitmask = nameToBitmask(tempItem.name);
				items.push_back(tempItem);
				rulesSize++;
			}
		}
		inFile.close();
	}
	else {
		cout << "Invalid File Path." << endl;
	}
	return { containerSize, itemSize, rulesSize };
}

void rulesConvert(vector<Item>& items, int itemSize, int rulesSize) {
	int calculationWeight, calculationValue;
	for (int k = itemSize; k < (itemSize + rulesSize); k++) {
		calculationWeight = 0, calculationValue = 0;
		for (int l = 0; l < itemSize; l++) {
			if (items[k].name[0] == items[l].name[0]) {
				calculationWeight += items[l].weight;
				calculationValue += items[l].value;
			}
			else if (items[k].name[1] == items[l].name[0]) {
				calculationWeight += items[l].weight;
				calculationValue += items[l].value;
			}
		}
		items[k].weight = calculationWeight + items[k].weight;
		items[k].value = calculationValue + items[k].value;
	}
}

void ValueToWeightRatio(vector<Item>& items, int startIndex) {
	// Set the starting iterator position based on startIndex
	auto startIter = items.begin();
	advance(startIter, startIndex);

	// Sort from the specified start index to the end of the vector (for rules only)
	sort(startIter, items.end(), [](const Item& a, const Item& b) {
		return (static_cast<double>(a.value) / a.weight) > (static_cast<double>(b.value) / b.weight);
		});
}

tuple<vector<string>, int> solverBenchmark(vector<Item>& items, int containerSize, int itemSize) {
	// 2D vector 'T' to store max value at each n (itemSize) and W (containerSize)
	vector<vector<int>> T(itemSize + 1, vector<int>(containerSize + 1));

	// Build table T[][] in bottom-up manner for items only
	for (int i = 0; i <= itemSize; i++) { // Row
		for (int w = 0; w <= containerSize; w++) { // Col
			// Base Case
			if (i == 0 || w == 0) {
				T[i][w] = 0;
			}
			// If the weight of the current item is less than or equal to 'w'
			else if (items[i - 1].weight <= w) {
				// Include the current item in the knapsack or not, and choose the maximum value
				T[i][w] = max(items[i - 1].value + T[i - 1][w - items[i - 1].weight], T[i - 1][w]);
			}
			else {
				// If the current item cannot be included because of its weight
				T[i][w] = T[i - 1][w];
			}
		}
	}
	// Backtrack to find which items were included in the knapsack
	vector<string> selectedItems;
	int w = containerSize;
	int oriMaxValue = T[itemSize][containerSize];

	for (int i = itemSize; i > 0 && w > 0; i--) {
		if (T[i][w] != T[i - 1][w]) { // If the item is included in the optimal solution
			selectedItems.push_back(items[i - 1].name); // Include this item's name
			w -= items[i - 1].weight; // Reduce the remaining weight
		}
	}
	return make_tuple(selectedItems, oriMaxValue);
}


vector<string> splitIntoPairs(const string& concatenated) {
	vector<string> pairs;

	// Iterate over the string two characters at a time
	for (int i = 0; i < concatenated.length(); i += 2) {
		pairs.push_back(concatenated.substr(i, 2)); // Take 2 characters at a time
	}

	return pairs;
}

vector<string> oneshotAlgorithm(vector<Item>& items, vector<Item>& rules, int containerSize, int itemSize, int rulesSize, int valueBase) {
	int n = rules.size();
	long long totalCombinations = 1LL << n; // 2^n possible combinations

	// Prepare thread-local storage for results
	vector<int> maxValues(omp_get_max_threads(), valueBase);
	vector<vector<string>> selectedItemsList(omp_get_max_threads());

	#pragma omp parallel
	{
		int threadId = omp_get_thread_num();
		vector<Item> itemsRules;
		Item tempItemRule;

		#pragma omp for schedule(guided)
		for (long long i = 1; i < totalCombinations; ++i) { // Start from 1 to exclude the empty set
			string combinationName;
			int currentWeight = 0;
			unsigned int currentBitmask = 0;
			bool isValid = true;

			// Construct combination based on bitmask
			for (unsigned int j = 0; j < n; ++j) {
				if (i & (1LL << j)) {
					if (currentWeight + rules[j].weight > containerSize || (currentBitmask & rules[j].bitmask) != 0) {
						isValid = false;
						break;
					}
					currentWeight += rules[j].weight;
					currentBitmask |= rules[j].bitmask;
					combinationName += rules[j].name;
				}
			}

			if (isValid) {
				vector<string> rulesCombo = splitIntoPairs(combinationName);
				itemsRules.clear();

				// Put in the rest of items into the combination of rules
				for (int k = 0; k < itemSize + rulesSize; k++) { // Put item
					bool repeat = false;
					for (int j = 0; j < combinationName.length(); j++) {
						if (items[k].name[0] == combinationName[j]) {
							repeat = true;
							break;
						}
					}

					if (k >= itemSize) { // Put rules
						repeat = true;
						for (int p = 0; p < rulesCombo.size(); p++) {
							if (items[k].name == rulesCombo[p]) {
								repeat = false;
								break;
							}
						}
					}
					if (!repeat) {
						tempItemRule.name = items[k].name;
						tempItemRule.value = items[k].value;
						tempItemRule.weight = items[k].weight;
						tempItemRule.bitmask = items[k].bitmask;
						itemsRules.push_back(tempItemRule);
					}
				}

				// DP to find max value
				vector<vector<int>> TR(itemsRules.size() + 1, vector<int>(containerSize + 1));
				for (int idx = 0; idx <= itemsRules.size(); idx++) {
					for (int w = 0; w <= containerSize; w++) {
						if (idx == 0 || w == 0) {
							TR[idx][w] = 0;
						}
						else if (itemsRules[idx - 1].weight <= w) {
							TR[idx][w] = max(itemsRules[idx - 1].value + TR[idx - 1][w - itemsRules[idx - 1].weight], TR[idx - 1][w]);
						}
						else {
							TR[idx][w] = TR[idx - 1][w];
						}
					}
				}

				int rulesMaxValue = TR[itemsRules.size()][containerSize];
				if (rulesMaxValue > maxValues[threadId]) { // Comparing the max value from no rules DP
					maxValues[threadId] = rulesMaxValue;
					selectedItemsList[threadId] = vector<string>();
					int w = containerSize;

					// Retrieve the item back
					for (int idx = itemsRules.size(); idx > 0 && w > 0; idx--) {
						if (TR[idx][w] != TR[idx - 1][w]) {
							selectedItemsList[threadId].push_back(itemsRules[idx - 1].name);
							w -= itemsRules[idx - 1].weight;
						}
					}
				}
			}
		}
	}

	// Reduction to find the overall maximum and selected items
	int globalMaxValue = valueBase;
	vector<string> globalSelectedItems;
	for (int i = 0; i < maxValues.size(); i++) {
		if (maxValues[i] > globalMaxValue) {
			globalMaxValue = maxValues[i];
			globalSelectedItems = selectedItemsList[i];
		}
	}

	return globalSelectedItems;
}

void outputFile(vector<string>& result) {
	ofstream outFile("output.txt");

	if (outFile.is_open()) {
		for (const auto& name : result) {
			outFile << name << endl;
		}
		outFile.close();
	}
	else {
		cout << "Unable to open file";
	}
}

int main(void) {
	string filePath = "problem.txt";
	vector<Item> items;
	int totalValue = 0;

	vector<int> inputResult = inputFile(filePath, items);
	int containerSize = inputResult[0];
	int itemSize = inputResult[1];
	int rulesSize = inputResult[2];

	rulesConvert(items, itemSize, rulesSize);
	ValueToWeightRatio(items, itemSize);

	// Calculate the average rules weight for filter the rules later
	double totalItemWeight = 0;
	for (int i = itemSize; i < items.size(); i++) {
		totalItemWeight += items[i].weight; // Sum up all the weight of rules
	}

	vector<Item> rules;
	int counter = 0;
	double averageWeight = totalItemWeight / rulesSize;

	for (int r = itemSize; r < items.size(); ++r) {
		if (counter < 62) { // Max 32 rules only due to 2^62 is our max limit due to the long long variable
			// 1. Check the rules within the container
			// 2. Only include the rules when the rules value-to-weight ratio is bigger/equal to the ratio that rules occupied the capacity in container 
			if (items[r].weight <= containerSize && (static_cast<double>(items[r].value) / items[r].weight) >= (averageWeight / containerSize)) {
				rules.push_back({ items[r].name, items[r].weight, items[r].value, items[r].bitmask });
				counter++;
			}
		}
	}

	// Calculate the DP without rules, return the max result for comparison of rules max value later
	tuple<vector<string>, int> resultBenchmark = solverBenchmark(items, containerSize, itemSize);
	int valueBase = get<1>(resultBenchmark);

	// Calcualate the DP with rules, from finding the combination till DP for each combinations
	vector<string> result = oneshotAlgorithm(items, rules, containerSize, itemSize, rulesSize, valueBase);

	// If return nothing, the combination without rules bigger
	if (result.empty()) {
		result = get<0>(resultBenchmark);
	}
	outputFile(result);

	return 0;
}