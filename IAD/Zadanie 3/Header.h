#pragma once
#include <iostream>
#include <fstream>
#include <random>
#include <math.h>

#include "Point.h"

using namespace std;

void Open(fstream &);
void WczytajDane(fstream &, vector<Point> &);
void PickCentroids(vector <Point> &, vector<Centroid> &, int);

void Wypisz(vector<Point> A);
void Wypisz(vector<Centroid> A);

int Random(int);

void AssignmentToGroups(vector<Point> &, vector<Centroid> &);
vector<Point> BuildGroup(vector<Point> &, int);
void CenterOfMass(vector<Point> &, vector<Centroid> &);
bool DoCentroidsRepeat(vector<Centroid> &, vector<Centroid> &);