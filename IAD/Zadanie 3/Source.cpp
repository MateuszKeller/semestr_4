#include "Header.h"

void Open(fstream &input)
{
	cout << "Wybierz plik: \n" << "1. attract.txt\n" << "2. attract_small.txt\n" << "3. inny\n";
	string name;
	int ch;		cin >> ch;

	switch (ch)
	{
	case 1:
		name = "attract.txt";
		break;

	case 2:
		name = "attract_small.txt";
		break;

	case 3:
		//cout << "Wpisz nazwe pliku: "; cin >> name;
		name = "A.txt";
		break;

	default:
		return;
		break;
	}

	input.open(name);
}

void WczytajDane(fstream &input, vector<Point> &Points)
{
	while (!input.eof())
	{
		stringstream line; string s;
		double x, y;
		getline(input, s);
		size_t comma = s.find(',');

		line.str(s.substr(0, comma - 1));	line >> x;	line.clear();
		line.str(s.substr(comma + 1));		line >> y;	line.clear();

		Point temp(x, y);
		Points.push_back(temp);
	}
	input.close();
}

void PickCentroids(vector <Point> & Points, vector<Centroid> & Centroids, int K)
{
	for (int i = 0; i < K; i++)
	{
		bool Again = true;
		while (Again)
		{
			bool R = false;
			Centroid temp(Points[Random(Points.size() - 1)]);
			for (auto C : Centroids)
				if (temp == C)
					R = true;

			if (!R)
			{
				Centroids.push_back(temp);
				Centroids[i].Grupa = i;
				Again = false;
			}
		}
	}
}

// --------------------------------------------------------------------------- //

void Wypisz(vector<Point> A)
{
	for (auto X : A)
		cout << X.ToString() << endl;
}

void Wypisz(vector<Centroid> A)
{
	for (auto X : A)
		cout << X.ToString() << endl;
}

// --------------------------------------------------------------------------- //

int Random(int Size)
{
	random_device rd;
	mt19937 mt(rd());
	uniform_int_distribution<int> dist(0, Size);

	return dist(mt);
}

// --------------------------------------------------------------------------- //

void AssignmentToGroups(vector<Point> & Points, vector<Centroid> & Centroids)
{
	for (auto &P : Points)
	{
		double Smallest = P.Distance(Centroids[0]), Dist;
		for (auto C : Centroids)
		{
			Dist = P.Distance(C);
			if (Dist <= Smallest)
			{
				Smallest = Dist;
				P.Grupa = C.Grupa;
			}

		}
	}

	// Obliczenie ile punktów jest w grupie 
	for (auto &P : Points)
		Centroids[P.Grupa].PointsInClaster++;
}

vector<Point> BuildGroup(vector<Point> & Points, int G)
{
	vector<Point> Ret;

	for (auto P : Points)
		if (P.Grupa == G)
			Ret.push_back(P);

	return Ret;
}

void CenterOfMass(vector<Point> & Points, vector<Centroid> & Centroids)
{
	for (auto &C : Centroids)
	{
		double x = 0.0, y = 0.0;
		vector<Point> Group = BuildGroup(Points, C.Grupa);
		//Wypisz(Group);	cout << "\n\n";
		if (Group.size() == 0)
			continue;

		for (auto X : Group)
		{
			x += X.x;
			y += X.y;
		}
		x /= Group.size();
		y /= Group.size();

		Point NewCenter(x, y);
		C.x = NewCenter.x;
		C.y = NewCenter.y;

		C.PointsInClaster = 0;

		//cout << "x: " << x / Group.size() << " \ty:" << y / Group.size() << endl;

	}
}

bool DoCentroidsRepeat(vector<Centroid> & Centroids, vector<Centroid> & Memory)
{
	for (int i = 0; i < Centroids.size(); i++)
		if (Centroids[i] == Memory[i])
			return true;

	return false;
}

// --------------------------------------------------------------------------- //