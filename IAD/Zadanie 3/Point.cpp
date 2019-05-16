#include "Point.h"

Point::Point() { };
Point::Point(double x, double y): x(x), y(y) { }

Point::~Point()
{
}

std::string Point::ToString()
{
	std::stringstream Ret;
	Ret << "x: " << x << " \ty: " << y
		<< " \tGrupa: " << Grupa;
	
	return  Ret.str();
}

double Point::Distance(Point P)
{
	double divx = abs(this->x - P.x);
	double divy = abs(this->y - P.y);
	
	return sqrt(divx*divx + divy * divy);
}

//-----------------------------------

Centroid::Centroid(const Point &P)
{
	this->x = P.x;
	this->y = P.y;

}

std::string Centroid::ToString()
{
	std::stringstream Ret;
	Ret << "x: " << x << " \ty: " << y
		<< " \tGrupa: " << Grupa << " x" << PointsInClaster;

	return  Ret.str();
}
