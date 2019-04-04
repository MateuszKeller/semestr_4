#pragma once

#include <iostream>
#include <vector>
#include <fstream>

using namespace std;

const bool  H[4][12] =
{
	1,1,1,1,0,0,0,0,	1,0,0,0,
	1,0,0,0,1,1,1,0,	0,1,0,0,
	0,1,1,0,1,1,0,1,	0,0,1,0,
	0,1,0,1,1,0,1,1,	0,0,0,1
};

const bool H2[8][16] =
{
	0,0,0,1,1,0,1,1,	1,0,0,0,0,0,0,0,
	0,0,1,1,0,1,0,1,	0,1,0,0,0,0,0,0,
	0,1,1,0,1,0,0,1,	0,0,1,0,0,0,0,0,
	0,1,0,1,0,1,1,0,	0,0,0,1,0,0,0,0,
	1,0,1,0,1,0,1,0,	0,0,0,0,1,0,0,0,
	1,1,0,0,1,1,0,0,	0,0,0,0,0,1,0,0,
	1,1,1,1,0,0,0,0,	0,0,0,0,0,0,1,0,
	1,1,1,1,1,1,1,1,	0,0,0,0,0,0,0,1
};

void testMacierzy8x16(bool TEST); 
void BajtNaBit(char B, vector<bool> &Wektor);
void WypiszWektor(vector<bool> &Wektor);
void KodujBajt(vector<bool> &Wektor, bool TestSum);
void ZapiszBajt(vector<bool> &Wektor, fstream &wyjscie, bool CzyBlad);
void SzukajBledu(vector<bool> &Wektor);