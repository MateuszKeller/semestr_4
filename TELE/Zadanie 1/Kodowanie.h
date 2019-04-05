#pragma once

#include <iostream>
#include <vector>
#include <fstream>

#define X 8
#define Y 16

using namespace std;

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

// Test liczb w macierzy dla warunku korekcji b³êdów 2 bitowych - suma dwóch kolumn nie mo¿e równaæ sie innej kolumnie.
void testMacierzy8x16(bool TEST); 

// Kodowanie

// Zamiana Bajtu z pliku txt na wektor bitów. (T)
void BajtNaBit(char B, vector<bool> &Wektor);

//Wypisanie wektoru na ekran. \n
void WypiszWektor(vector<bool> &Wektor, int Odstep);

// Wyliczenie bitów parzystoœci poprzez mno¿enie wektora T z macierz¹ H. 
void IloczynHT(vector<bool> &Wektor, bool TestSum);

// Zapisanie bajtu w podanym pliku. 
void ZapiszBajt(vector<bool> &Wektor, fstream &wyjscie, bool CzyBlad);
//void SzukajBledu(vector<bool> &Wektor);

// --- DEKODOWANIE ---

// Przerobienie zapisanego bitów ( 1B ) z pliku txt na wektor. (E) 
void CharNaBajt(char B, vector <bool> &Wektor, fstream &plik);

// Wyliczenie bitów parzystoœci poprzez mno¿enie wektora E z macierz¹ H. 
void IloczynHE(vector <bool> &bParzystosci, vector<bool> &Wektor);

// Wykrywanie b³êdu poprzez porównywanie BP z kolumnami w macierzy H.
void  WykrywanieBledu(vector <bool> bParzystosci, vector <int> &WektorBledow, bool &CzyWystapilBlad);

// Poprawia bity w które uderzy³ piorun
void PoprawBit(vector<bool> &Wektor, int MiejsceBledu);