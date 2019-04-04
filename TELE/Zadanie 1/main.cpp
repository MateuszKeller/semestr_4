#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <bitset>

#include "Kodowanie.h"

int main()
{
	testMacierzy8x16(false);

	cout << "----------------" << endl;
	fstream input("plik_testowy.txt", ios::in | ios::binary);
	fstream output("plik_testowy.bin", ios::out | ios::binary);

	std::vector <bool> PlikB;
	char T;

	while (input.get(T))
	{
		if (T != EOF)
		{
			BajtNaBit(T, PlikB);
			cout << "T: "; WypiszWektor(PlikB);

			KodujBajt(PlikB, false);
			cout << "\nHamming:"; WypiszWektor(PlikB);

			ZapiszBajt(PlikB, output, false);
		}
				
	}
	input.close();
	output.close();

	std::vector <bool> WektorBledu;
	input.open("plik_testowy.bin", ios::in | ios::binary);
	while (input.get(T))
	{
		while (T != EOF)
		{
			if (T == '0')
			{
				WektorBledu.push_back(false);
			}
			else
			{
				WektorBledu.push_back(true);
			}
		}
	}
	//SzukajBledu()
		
	/*
	TODO 
		Kodowanie:
			Mno¿enie macierzy H 4x8 z T 8x1 - macierz przez bajt  // E 12x1  tak jak T z bitami parzystkoœci
			 wynik idzie do sumy
			 suma%2 jako P[i] (i <1;4>)
			 cod Hamminga to:	 1  2  3  4  5  6  7  8  9  10 11 12
								(P1,P2,D1,P3,D2,D3,D4,P4,D5,D6,D7,D8)
								    
									D   D D D   D D D D
								0 1 0 0 1 1 0 1 1 0 0 1


	*/

	cout << endl;
	system("pause");
	return 0;
}