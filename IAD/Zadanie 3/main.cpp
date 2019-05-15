#include <iostream>
#include <fstream>
#include <math.h>
#include <cstdlib>
#include <algorithm>
#include <random>

#include "Group.h"

using namespace std;


/*
 k-œrednie 
	https://cezarywalenciuk.pl/blog/programing/post/algorytm-centroidow-k-srednie

Algorytm Kohonena
	http://algolytics.com/download/AdvancedMiner_documentation/userdoc/bk10pt02ch25.html

Algorytm gazu neuronowego
	

*/

void Open(fstream &);
void WczytajDane(fstream &, vector<Point> &);

void Wypisz(vector<Point> A) { for (auto X : A) cout << X.ToString() << endl; }
void Wypisz(vector<Group> A) { for (auto X : A) cout << X.ToString() << endl; }

void KSrednie();
void Kohonen();
void GazNeuronowy();

int Random(int); 
void CenterOfMass(vector<Point> &);


/*
	GNUPLOT

	set style line 1 lc rgb 'black' pt 5
	set datafile separator ","
	plot "A.txt" with points ls 1

	attract_small.txt
	attract.txt
	
*/
int main()
{
	fstream input;
	vector<Point> Points;	vector<Group> Groups;
	int K;

	Open(input);	
	WczytajDane(input, Points);

	cout << "Podaj K: "; cin >> K;
	for (int i = 0; i < K; i++)
	{
		Group temp(Points[Random(Points.size())]);
		Groups.push_back(temp);
		Groups[i].Center.Grupa = i;
	}

	Wypisz(Groups);

	for (auto P : Points)
	{
		double Dist = 0.0;
		int g = -1;

		for (auto G : Groups)
		{
			/*if (P == G.Center)
				continue;*/
			if(Dist < P.Distance(G.Center))
			{
				Dist = P.Distance(G.Center);
				g = G.Center.Grupa;
			}
		}
		P.Grupa = g;
		Groups[g].Points.push_back(P);
	}
		
	Wypisz(Groups);

	input.close();
	system("PAUSE");
	return 0;
}

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
}

int Random(int Size)
{
	random_device rd;
	mt19937 mt(rd());
	uniform_int_distribution<int> dist(1, Size);
	
	return dist(mt);
}

void CenterOfMass(vector<Group> & Groups)
{
	double x = 0, y = 0;
	for (auto X : Groups)
	{
		for (auto P : X.Points)
		{
			x += P.x;
			y += P.y;
		}

		x /= X.Points.size();
		y /= X.Points.size();

		Point temp(x, y);
		X.Center = temp;
	}
		
	

}

