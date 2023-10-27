#include <opencv2/opencv.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc.hpp>
#include <iostream>
#include <stdlib.h>
#include <string>
#include "Supp.h"
#include <chrono>
#include <ctime>
#include <fstream>
#include <vector>
#include <map>

using namespace cv;
using namespace std;

// K-Means Clustering - Done by Teo Teck Wan
Mat kmean(Mat input, int K) {
	Mat image(input.rows * input.cols, input.channels(), CV_32F); // Change image to float

	for (int y = 0; y < input.rows; y++) {
		for (int x = 0; x < input.cols; x++) {
			for (int z = 0; z < input.channels(); z++) {
				image.at<float>(y + x * input.rows, z) = input.at<Vec3b>(y, x)[z]; //For color image
			}
		}
	}

	Mat labels, centeres; // For cluster label and cluster centers
	int attempts = 10; // For number of times the k-means will run in different initialization
	TermCriteria criteria(TermCriteria::MAX_ITER | TermCriteria::EPS, 10, 1.0); // Criteria to stop k-means (after 10 times)
	kmeans(image, K, labels, criteria, attempts, KMEANS_PP_CENTERS, centeres);

	Mat newImage(input.size(), input.type());
	for (int y = 0; y < input.rows; y++) {
		for (int x = 0; x < input.cols; x++) {
			int clusterID = labels.at<int>(y + x * input.rows, 0); // finds label for each pixel

			for (int z = 0; z < input.channels(); z++) {
				newImage.at<Vec3b>(y, x)[z] = centeres.at<float>(clusterID, z); // Assign the color to the cluster center
			}
		}
	}
	return newImage;
}

// Function to filter out contours based on aspect ratio and area - Done by Ng Zhen Hoong
vector<vector<Point>> filterContours(const vector<vector<Point>>& contours, double minAspectRatio, double minArea) {
	vector<vector<Point>> filteredContours;

	for (const auto& contour : contours) { // For each contour
		double area = contourArea(contour); // Find area
		if (area >= minArea) {
			RotatedRect boundingBox = minAreaRect(contour); // Calculate Aspect Ratio
			double aspectRatio = static_cast<double>(boundingBox.size.width) / boundingBox.size.height;
			if (aspectRatio >= minAspectRatio && aspectRatio <= 1.0 / minAspectRatio) { // Filter Aspect Ratio
				filteredContours.push_back(contour);
			}
		}
	}

	return filteredContours;
}

// Function to compute IOU (Intersection over Union) between two bounding boxes - Done by Chen Si Yang
double computeIOU(vector<int> boxA, vector<int> boxB) {
	int x_start = max(boxA[0], boxB[0]);
	int y_start = max(boxA[1], boxB[1]);
	int x_end = min(boxA[2], boxB[2]);
	int y_end = min(boxA[3], boxB[3]);

	// Compute the intersection area
	int interArea = max(0, x_end - x_start + 1) * max(0, y_end - y_start + 1);

	// Area of box A and box B
	int areaA = (boxA[2] - boxA[0] + 1) * (boxA[3] - boxA[1] + 1);
	int areaB = (boxB[2] - boxB[0] + 1) * (boxB[3] - boxB[1] + 1);

	return static_cast<double>(interArea) / (areaA + areaB - interArea);
}



int main(int argc, char** argv) {
	string windowName;
	Mat	canvasColor, canvasGray, srcI, hsv, gray, otsu;
	char str[256];
	Point2i	center;
	int const TARGET_WIDTH = 160, TARGET_HEIGHT = 120;
	int const NUM_FIELDS = 8;
	String imgPattern("Inputs/Traffic signs/*.png");
	vector<string> imageNames;
	double diffCircle = 0.0, diffTriangle = 0.0;
	int iouSegmented = 0;


	// Read the ground truth mask - Done by Chen Si Yang
	ifstream file("Inputs/TsignRecgTrain4170Annotation.txt");
	if (!file.is_open()) {
		cout << "Failed to open file." << endl;
		return 1;
	}

	map<string, vector<int>> actual_values;

	string line;
	while (getline(file, line)) {
		istringstream iss(line);
		string token;

		string filename;
		vector<int> values;
		int field = 0;
		int originalWidth = 0;
		int originalHeight = 0;

		// format of the txt file: 000_0001.png;134;128;19;7;120;117;0;
		while (getline(iss, token, ';') && field < NUM_FIELDS) {
			if (field == 0) {
				// First field (filename) should be stored as a string
				filename = token;
			}
			else if (field == 1) {
				// Field 1 contains the original width
				originalWidth = stoi(token);
			}
			else if (field == 2) {
				// Field 2 contains the original height
				originalHeight = stoi(token);
			}
			else if (field >= 3 && field <= 6) {
				// Fields 3 to 6 are x1, y1, x2, y2
				int value = stoi(token);
				// Calculate scaled coordinates based on the original width and height
				if (field % 2 == 1) {
					// Odd fields (x1, y1) need to be scaled
					value = (int)((double)value * (160.0 / originalWidth));
				}
				else {
					// Even fields (x2, y2) need to be scaled
					value = (int)((double)value * (120.0 / originalHeight));
				}
				values.push_back(value);
			}
			field++;
		}

		if (field == NUM_FIELDS) {
			actual_values[filename] = values;
		}
	}

	// Out the iou result into a CSV
	ofstream csvFile("iou_result.csv");
	if (!csvFile.is_open()) {
		cout << "Failed to create the CSV file." << endl;
		return 1;
	}
	csvFile << "Image Name" << "," << "IoU" << "," << ">Threshold" << endl;


	// For Hu Moments Calculation Later - Done by Teo Teck Wan
	// Create a black image with 160 rows and 120 columns
	Mat circleImage(120, 160, CV_8UC1, cv::Scalar(0));

	// Draw a white circle at (60, 80) with radius 40
	circle(circleImage, cv::Point(80, 60), 50, cv::Scalar(255), 1);

	// Create a black image with 160 rows and 120 columns
	cv::Mat triangle(120, 160, CV_8UC1, cv::Scalar(0));

	// Define the vertices of the triangle
	cv::Point pt1(80, 20);
	cv::Point pt2(40, 70);
	cv::Point pt3(120, 70);

	// Draw the edges of the triangle
	cv::line(triangle, pt1, pt2, cv::Scalar(255), 1);
	cv::line(triangle, pt2, pt3, cv::Scalar(255), 1);
	cv::line(triangle, pt3, pt1, cv::Scalar(255), 1);

	// Collect all image names satisfying the image name pattern
	glob(imgPattern, imageNames, true);
	int numberOfImage = imageNames.size();

	for (size_t i = 0; i < imageNames.size(); ++i) {
		auto start = chrono::system_clock::now(); // For calculate time elapsed - Done by Goh Brian Joon Jian
		srcI = imread(imageNames[i]);

		if (srcI.empty()) {
			cout << "Cannot open image for reading" << endl;
			return -1;
		}

		// Image Preprocessing (Original > Resize > HSV > K-Means > Gray > Otsu) - Done by Goh Brian Joon Jian
		// Bilinear Interpolation for Resize image
		resize(srcI, srcI, Size(TARGET_WIDTH, TARGET_HEIGHT), 0, 0, INTER_LINEAR);

		// Open 2 large windows to diaplay the results. One gives the detail. Other give only the results
		int const noOfImagePerCol = 3, noOfImagePerRow = 3;
		Mat	detailResultWin, win[noOfImagePerRow * noOfImagePerCol], legend[noOfImagePerRow * noOfImagePerCol];
		createWindowPartition(srcI, detailResultWin, win, legend, noOfImagePerCol, noOfImagePerRow);

		putText(legend[0], "Original", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);
		putText(legend[1], "HSV", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);
		putText(legend[2], "K-Means", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);
		putText(legend[3], "Gray", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);
		putText(legend[4], "Otsu", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);
		putText(legend[5], "Contours", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);
		putText(legend[6], "Filtered Contour", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);
		putText(legend[7], "Mask", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);
		putText(legend[8], "Sign Segmented", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);

		int const noOfImagePerCol2 = 1, noOfImagePerRow2 = 2;
		Mat	resultWin, win2[noOfImagePerRow2 * noOfImagePerCol2], legend2[noOfImagePerRow2 * noOfImagePerCol2];
		createWindowPartition(srcI, resultWin, win2, legend2, noOfImagePerCol2, noOfImagePerRow2);

		putText(legend2[0], "Original", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);
		putText(legend2[1], "Sign Segmented", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);


		srcI.copyTo(win[0]);
		srcI.copyTo(win2[0]);

		// Create black images for masks later
		canvasColor.create(srcI.rows, srcI.cols, CV_8UC3);
		canvasGray.create(srcI.rows, srcI.cols, CV_8U);
		canvasColor = Scalar(0, 0, 0);

		// Original to HSV
		cvtColor(srcI, hsv, COLOR_BGR2HSV);
		hsv.copyTo(win[1]);

		// HSV to K-Means
		int cluster = 6;
		Mat clusteri = kmean(hsv, cluster);
		cvtColor(clusteri, win[2], COLOR_HSV2BGR);

		// K-means to Gray Image
		cvtColor(clusteri, gray, COLOR_BGR2GRAY);
		cvtColor(gray, win[3], COLOR_GRAY2BGR);

		// Gray Image to Otsu
		threshold(gray, otsu, 0, 255, THRESH_OTSU | THRESH_BINARY);

		// Remove noise by reducing the image size (erode)
		Mat kernel = getStructuringElement(MORPH_RECT, Size(2, 1.9));
		erode(otsu, otsu, kernel);
		cvtColor(otsu, win[4], COLOR_GRAY2BGR);

		// Find contours and filter it - Done by Teo Teck Wan
		vector<vector<Point>> contours;  // 2D vectors
		findContours(otsu, contours, RETR_TREE, CHAIN_APPROX_SIMPLE);

		// Filter contours based on aspect ratio and area
		double minAspectRatio = 1 / 1.9;
		double minArea = 100;
		contours = filterContours(contours, minAspectRatio, minArea);

		double area, maxArea = 0.0, minDifCircle = 1.1, circleArea = 0.0, minDifTriangle = 1.1, triArea = 0.0;
		int	index, areaIndex = 0, circleIndex = -1, triangleIndex = -1, minTriIndex = -1;
		for (int i = 0; i < contours.size(); i++) {
			area = contourArea(contours[i]);

			// Find max area
			if (maxArea < area) {
				maxArea = area;
				areaIndex = i;
			}

			// Calculate Hu Moments and differences between image drawn and image input (Circle)
			diffCircle = matchShapes(circleImage, contours[i], CONTOURS_MATCH_I1, 0);
			if (diffCircle < 0.583580) {
				// Find min Hu Moments numbers (Lower the number, higher the similarity)
				if (minDifCircle > diffCircle) {
					minDifCircle = diffCircle;
					circleIndex = i;
				}
			}

			// Calculate Hu Moments and differences between image drawn and image input (Triangle)
			diffTriangle = matchShapes(triangle, contours[i], CONTOURS_MATCH_I1, 0);
			// Calculate the perimeter of the contour
			double perimeter = arcLength(contours[i], true);

			// Approximate the shape of the contour
			vector<cv::Point> vertices;
			approxPolyDP(contours[i], vertices, 0.055 * perimeter, true);

			// Get the number of vertices (corners)
			int corners = vertices.size();
			if (corners == 3) { // Detect Triangle
				triangleIndex = i;

				// Find min Hu Moments numbers (Lower the number, higher the similarity)
				if (minDifTriangle > diffTriangle) {
					minDifTriangle = diffTriangle;
					minTriIndex = i;
				}
			}
			// Draw all contours
			drawContours(canvasColor, contours, i, Scalar(255, 255, 255));
		}
		canvasColor.copyTo(win[5]);
		// Find their respective area to ensure remove all false region - Done by Ng Zhen Hoong
		if (circleIndex != -1) { // Circle Area
			circleArea = contourArea(contours[circleIndex]);
		}
		if (triangleIndex != -1 && circleIndex == -1) { // Triangle Area
			if (minTriIndex != -1) {
				triArea = contourArea(contours[minTriIndex]);
			}
			else {
				triArea = contourArea(contours[triangleIndex]);
			}
		}

		Rect boundingRect;
		// Assume the area contour (indicated by index) is the correct one
		if (contours.size() != 0) {
			canvasGray = 0;
			// Draw the filtered contour by shape descritors or biggest area contour
			if (circleIndex != -1 && circleArea > 5000) { // Circle
				drawContours(canvasGray, contours, circleIndex, 255);
			}
			else if (triangleIndex != -1 && triArea > 1500) { // Triangle
				if (minTriIndex != -1) {
					drawContours(canvasGray, contours, minTriIndex, 255);
				}
				else {
					drawContours(canvasGray, contours, triangleIndex, 255);
				}
			}
			else {
				drawContours(canvasGray, contours, areaIndex, 255);
			}

			// Get the bounding box of the segmented Image
			boundingRect = cv::boundingRect(canvasGray);

			cvtColor(canvasGray, win[6], COLOR_GRAY2BGR);

			// Generate mask - Done by Goh Brian Joon Jian
			Moments M = moments(canvasGray);
			center.x = M.m10 / M.m00;
			center.y = M.m01 / M.m00;

			// Fill inside sign boundary
			floodFill(canvasGray, center, 255);

			// Remove noise by "erode"
			Mat kernel2 = getStructuringElement(MORPH_RECT, Size(2, 2));
			erode(canvasGray, canvasGray, kernel2);
			cvtColor(canvasGray, canvasGray, COLOR_GRAY2BGR);
			canvasGray.copyTo(win[7]);

			// Stack mask and result is here
			canvasColor = (canvasGray & srcI);
			canvasColor.copyTo(win[8]);
			canvasColor.copyTo(win2[1]);
		}
		else {
			boundingRect.x = 0;
			boundingRect.y = 0;
			boundingRect.width = 0;
			boundingRect.height = 0;
		}

		// Remove the imageNames prefix
		string imageNameNoPrefix = imageNames[i].substr(21);

		// Calculate IOU using computeIOU function - Done by Chen Si Yang
		vector<int> boxA = { boundingRect.x, boundingRect.y, boundingRect.x + boundingRect.width, boundingRect.y + boundingRect.height };
		vector<int> boxB;
		if (actual_values.find(imageNameNoPrefix) != actual_values.end()) {
			boxB = actual_values[imageNameNoPrefix];
		}
		else {
			cout << "No values found for " << imageNameNoPrefix << endl;
			continue;
		}

		double iou = computeIOU(boxA, boxB);
		cout << "\nIoU for " << imageNameNoPrefix << ": " << iou << endl << endl;

		double threshold = 0.53;
		bool segmented = false;
		if (iou > threshold) {
			segmented = true;
			iouSegmented++;
		}

		csvFile << imageNameNoPrefix << "," << iou << "," << segmented << endl;

		windowName = "Segmentation of " + imageNames[i] + " (detail)";
		imshow(windowName, detailResultWin);
		imshow("Traffic sign segmentation", resultWin);

		// For calculating time elapsed for each image - Done by Goh Brian Joon Jian
		auto end = std::chrono::system_clock::now();
		chrono::duration<double> elapsed_seconds = end - start;
		time_t end_time = chrono::system_clock::to_time_t(end);
		cout << "elapsed time: " << elapsed_seconds.count() << "s" << std::endl;

		waitKey(0);
		destroyAllWindows();
		// Accuracy (Mannual Look) 88.0% (66 / 70 + 5)
		// Accuracy (IoU Segment)  93.3% (70 / 70 + 5)
	}
	cout << "Total Number of Segmented Image (IoU - 0.53): " << iouSegmented << "/" << imageNames.size() << " ("
		<< (iouSegmented / static_cast<double>(numberOfImage)) * 100 << "%)" << endl;

	file.close();
	csvFile.close();
	return 0;
}