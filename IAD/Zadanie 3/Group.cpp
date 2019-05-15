#include "Group.h"

Group::Group(Point Center) : Center(Center) { }

Group::~Group()
{
}

std::string Group::ToString()
{
	std::stringstream Ret;
	Ret << Center.ToString() << "\n";
	if (Points.size() > 0)
		for (auto X : Points)
			Ret << X.ToString() << "\n";

	return  Ret.str();
}
