#pragma once

#include <iostream>
#include <vector>
#include <fstream>

#define X 8
#define Y 16

using namespace std;



const bool  HA[4][12] =
{
	1,1,1,1,0,0,0,0,	1,0,0,0,
	1,0,0,0,1,1,1,0,	0,1,0,0,
	0,1,1,0,1,1,0,1,	0,0,1,0,
	0,1,0,1,1,0,1,1,	0,0,0,1
};

const bool H[8][16] =
{
	0,1,0,0,0,1,1,1,	1,0,0,0,0,0,0,0,
	0,1,0,0,0,0,0,1,	0,1,0,0,0,0,0,0,
	1,0,0,1,0,1,0,1,	0,0,1,0,0,0,0,0,
	0,0,1,1,1,1,0,1,	0,0,0,1,0,0,0,0,
	1,1,0,0,1,1,1,1,	0,0,0,0,1,0,0,0,
	0,0,1,1,0,0,0,1,	0,0,0,0,0,1,0,0,
	1,1,0,1,1,0,0,1,	0,0,0,0,0,0,1,0,
	0,0,1,0,0,0,1,1,	0,0,0,0,0,0,0,1
};

//const bool H2[8][16] =
//{
//	0,0,0,1,1,0,1,1,	1,0,0,0,0,0,0,0,
//	0,0,1,1,0,1,0,1,	0,1,0,0,0,0,0,0,
//	0,1,1,0,1,0,0,1,	0,0,1,0,0,0,0,0,
//	0,1,0,1,0,1,1,0,	0,0,0,1,0,0,0,0,
//	1,0,1,0,1,0,1,0,	0,0,0,0,1,0,0,0,
//	1,1,0,0,1,1,0,0,	0,0,0,0,0,1,0,0,
//	1,1,1,1,0,0,0,0,	0,0,0,0,0,0,1,0,
//	1,1,1,1,1,1,1,1,	0,0,0,0,0,0,0,1
//};

void testMacierzy8x16(bool TEST); 

// Kodowanie

void BajtNaBit(char B, vector<bool> &Wektor);
//Wypisanie wektoru na ekran. \n
void WypiszWektor(vector<bool> &Wektor, int Odstep);
void KodujBajt(vector<bool> &Wektor, bool TestSum);
void ZapiszBajt(vector<bool> &Wektor, fstream &wyjscie, bool CzyBlad);
//void SzukajBledu(vector<bool> &Wektor);

// --- DEKODOWANIE ---

// Przerobienie zapisanego bajtu z pliku txt na wektor.
void CharNaBajt(char B, vector <bool> &Wektor, fstream &plik);

// Wyliczenie bitów parzystoœci poprzez mno¿enie wektora E z macierz¹ H. 
void IloczynHE(vector <bool> &bParzystosci, vector<bool> &Wektor);

// Wykrywanie b³êdu poprzez porównywanie BP z kolumnami w macierzy H.
int WykrywanieBledu(vector <bool> bParzystosci);

// Poprawia bity w które uderzy³ piorun
void PoprawBit(vector<bool> &Wektor, int MiejsceBledu);