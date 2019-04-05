/*
	Semestr 4 Telekomunikacja.
		Kody korekcji b³êdów bitowych.

*/
#include <string>
#include <iostream>
#include "Kodowanie.h"

/*PLIKI
	plik_testowy.txt	plik_wynikowy.txt

	PDF.pdf		Wynik.pdf

	S.jpg		E.jpg

	I.ico		W.ico
*/

int main()
{
	bool TESTY = false;

	if (TESTY) { testMacierzy8x16(false); cout << "++++++++++++++++" << endl; }

	string FSTART = "in\\"; FSTART.append("plik_testowy.txt");
	const string FMID = "plik_posredni.txt"; 
	string FEND = "out\\"; FEND.append("plik_wynikowy.txt");
	

	fstream input(FSTART, ios::in | ios::binary);
	fstream output(FMID, ios::out | ios::binary);
	if (input.fail() || output.fail()) { cout << "BLAD!!!"; return -1; }
		
	vector <bool> WektorT; // Wektor w którym zapisywany jest bajt z pliku wejsciowego.
	char T; // Zmienna na wczytany z pliku B.

	while (input.get(T))
	{
		if (!input.eof())
		{
			BajtNaBit(T, WektorT);
			if (TESTY) { cout << T << " -> T: "; WypiszWektor(WektorT, 0); }

			IloczynHT(WektorT, false);
			if (TESTY) { cout << "Hamming:"; WypiszWektor(WektorT, 8); }

			ZapiszBajt(WektorT, output, true);
			WektorT.clear();
		}
				
	}

	input.close();
	output.close();
	system("pause");

	if (TESTY) cout << "-----------------------------------" << endl;

	vector <bool> WektorR;
	vector <bool> BP;
	vector <int> BlednyBit;

	input.open(FMID, ios::in | ios::binary);
	output.open(FEND, ios::out | ios::binary);
	
	if (input.fail() || output.fail()) { cout << "BLAD!!!"; return -1; }

	while (input.get(T))
	{
		if (!input.eof())
		{
			if ( T != '\n' )
			{
				bool WystapilBlad = false;
				CharNaBajt(T, WektorR, input);
				IloczynHE(BP, WektorR);

				if (TESTY) { cout << "BP: "; WypiszWektor(BP, 0); }
				WykrywanieBledu(BP, BlednyBit, WystapilBlad);

				if (WystapilBlad) { cout << "Odebrany bajt:     ";  WypiszWektor(WektorR, 8); }
				for (unsigned int i = 0; i < BlednyBit.size(); i++)
					if (BlednyBit[i] != 0)
					{
							
						PoprawBit(WektorR, BlednyBit[i]);
					}
					if (WystapilBlad) 
					{	
						cout << "Poprawiony wektor: ";  WypiszWektor(WektorR, 8); 
						cout << "--                                --" << endl;
					}
			}
		}

		BlednyBit.clear();
		BP.clear();

		ZapiszBajt(WektorR, output, false);
		WektorR.clear();
	}

	input.close();
	output.close();
	cout << "FIN\n";

	system("pause");
	return 0;
}