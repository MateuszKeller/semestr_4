//#include <iostream>
//#include <fstream>
//#include <math.h>
//#include <cstdlib>
//#include <algorithm>
//#include <random>

//#include "Point.h"
#include "Header.h"

using namespace std;


/*
 k-œrednie 
	https://cezarywalenciuk.pl/blog/programing/post/algorytm-centroidow-k-srednie

Algorytm Kohonena
	http://algolytics.com/download/AdvancedMiner_documentation/userdoc/bk10pt02ch25.html

Algorytm gazu neuronowego

*/


/*
	GNUPLOT

	set style line 1 lc rgb 'black' pt 5
	set datafile separator ","
	plot "A.txt" with points ls 1

	attract_small.txt
	attract.txt
	
*/

void KSrednie(vector <Point> &, vector<Centroid> &);
void Kohonen();
void GazNeuronowy();



int main()
{
	fstream input;
	vector<Point> Points;	vector<Centroid> Centroids;
	
	Open(input);	
	WczytajDane(input, Points);

	KSrednie(Points, Centroids);

	/*PickingCentroids(Points, Centroids, K);

	Wypisz(Centroids);
	cout << endl;
	AssignmentToGroups(Points, Centroids);
	cout << "C --------------\n";
	Wypisz(Centroids);
		
	CenterOfMass(Points, Centroids);
	cout << "C --------------\n";
	Wypisz(Centroids);

	cout << "P --------------\n";
	Wypisz(Points);*/

	system("PAUSE");
	return 0;
}

void KSrednie(vector <Point> & Points, vector<Centroid> & Centroids)
{
	int K;	cout << "Podaj K: "; cin >> K;
	PickCentroids(Points, Centroids, K);
	
	AssignmentToGroups(Points, Centroids);
	Wypisz(Centroids);	cout << "----------" << endl;

	//vector<Centroid> C = Centroids;

	vector<Centroid> Memory; int i = 0;
	do
	{
		i++;
		Memory = Centroids;
		CenterOfMass(Points, Centroids);
		AssignmentToGroups(Points, Centroids);

	} while (!DoCentroidsRepeat(Centroids, Memory) || i == 100);

	Wypisz(Centroids);	cout << "----------" << i << endl << endl << endl;

	//Centroids = C;
	//Wypisz(Centroids);	cout << "----------" << endl;

	//for (int i = 0; i < 100; i++)
	//{
	//	vector<Centroid> Memory = Centroids;
	//	CenterOfMass(Points, Centroids);
	//	AssignmentToGroups(Points, Centroids);

	//}

	//Wypisz(Centroids);	cout << "----------" << endl << endl << endl;

}
