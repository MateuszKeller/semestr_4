#pragma once
#include "Point.h"

class Group
{
public:
	Point Center;
	std::vector<Point> Points;

	Group(Point Center);
	~Group();

	std::string ToString();

};

