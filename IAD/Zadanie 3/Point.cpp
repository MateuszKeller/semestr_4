#include "Point.h"

Point::Point(double x, double y): x(x), y(y) { }

Point::~Point()
{
}

std::string Point::ToString()
{
	std::stringstream Ret;
	if(x < 0) Ret << "x: " << x << " y: " << y;
	else Ret << "x:  " << x << " y: " << y;

	if (y < 0 && Grupa > -1) Ret << " Grupa: " << Grupa;
	else Ret << "  Grupa: " << Grupa;
	
		

	return  Ret.str();
}

double Point::Distance(Point P)
{
	double divx = abs(this->x - P.x);
	double divy = abs(this->y - P.y);
	
	return sqrt(divx*divx + divy * divy);;
}
