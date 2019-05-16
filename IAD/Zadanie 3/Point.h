#pragma once
#include <string>
#include <sstream>
#include <vector>

class Point
{
public:

	bool operator==(const Point &R) 
	{ return this->x == R.x && this->y == R.y; }

	double x;
	double y;

	int Grupa = -1;

	Point();
	Point(double x, double y);
	~Point();

	std::string ToString();
	double Distance(Point);

};

class Centroid : public Point
{
public:
	Centroid(const Point &);
	int PointsInClaster = 0;

	std::string ToString();
};
