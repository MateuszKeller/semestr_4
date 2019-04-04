#include <string>
#include <iostream>
#include "Kodowanie.h"

int main()
{
	bool TESTY = true;

	if (TESTY) { testMacierzy8x16(false); cout << "++++++++++++++++" << endl; }

	string FSTART = "plik_testowy.txt"; string FMID = "plik_posredni.txt"; string FEND = "plik_wynikowy.txt";
	fstream input(FSTART, ios::in | ios::binary);
	fstream output(FMID, ios::out | ios::binary);
		
	vector <bool> WektorT; // Wektor w którym zapisywany jest bajt z pliku wejsciowego.
	char T; // Zmienna na wczytany z pliku B.

	while (input.get(T))
	{
		if (TESTY) cout << "----" << T << endl;
		if (T != EOF)
		{
			BajtNaBit(T, WektorT);
			if (TESTY) { cout << "T: "; WypiszWektor(WektorT, 0); }

			KodujBajt(WektorT, false);
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
	
	while (input.get(T))
	{
		if (T != EOF && T != '\n')
		{
			CharNaBajt(T, WektorR, input);
			cout << "Odebrany bajt:     ";  WypiszWektor(WektorR, 8);
			
			IloczynHE(BP, WektorR);

			if (TESTY) { cout << "BP: "; WypiszWektor(BP, 0); }
			
			BlednyBit.push_back(WykrywanieBledu(BP));
			
			bool temp = false;
			for (unsigned int i = 0; i < BlednyBit.size(); i++)
				if (BlednyBit[i] != 0)
				{
					PoprawBit(WektorR, BlednyBit[i]);
					temp = true;
				}
			if (temp) { cout << "Poprawiony wektor: ";  WypiszWektor(WektorR, 8); }

			cout << endl;
		}

		BlednyBit.clear();
		BP.clear();

		ZapiszBajt(WektorR, output, false);
		WektorR.clear();
			
			

		
	}

	
	cout << "FIN?\n";


	cout << endl;
	system("pause");
	return 0;
}