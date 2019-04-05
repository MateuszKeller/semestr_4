#include "Kodowanie.h"

// ----------------------------------------------- //
void testMacierzy8x16(bool TEST)
{
	const vector<int> T = { 42, 202, 21, 54, 26, 184, 137, 255, 128, 64, 32,16,8,4,2,1 }; 
	 //{ 17, 55, 107, 211, 173, 85, 153, 225, 128, 64, 32,16,8,4,2,1 };
	for (int i = 0; i < 16; i++)
	{
		if (TEST) cout << i << ". :\n";
		for (int j = 0; j < 16; j++)
		{
			int W = T[i] + T[j];


			if (TEST)
				std::cout << T[i] << " + " << T[j] << " = " << W << "   (" << j << ")" << std::endl;

			for (int m = i; m < 15; m++)
			{
				if (W == T[m])
					std::cout << T[i] << " + " << T[j] << " = " << W << "   (" << m << ")" << std::endl;
			}
		}
	}
}
// ----------------------------------------------- //
void BajtNaBit(char B, vector<bool> &Wektor)
{
	for (int y = (sizeof(char) * 8) - 1; y >= 0; y--)
		Wektor.push_back(B & (1 << y));
}

void WypiszWektor(vector<bool> &Wektor, int Odstep)
{
	for (int i = 0; i < Wektor.size(); i++)
	{
		if (Odstep != 0 && i % Odstep == 0 && i != 0)
			cout << " ";
		cout << Wektor[i];
	}
	cout << endl;
}

int Mnozenie(vector<bool>& Wektor)
{
	return 0;
}

void IloczynHT(vector<bool> &Wektor, bool TestSum)
{
	int suma = 0;
	for (int Wiersze = 0; Wiersze < X; Wiersze++)
	{
		for (int Kolumny = 0; Kolumny < 8; Kolumny++)
		{
			//int x = PlikB[Kolumny]; int y = H[Wiersze][Kolumny];
			suma += Wektor[Kolumny] * H[Wiersze][Kolumny];
		}

		if(TestSum)	cout << "Suma-" << suma << endl;

		Wektor.push_back(suma % 2);
		suma = 0;
	}
}

void ZapiszBajt(vector<bool>& Wektor, fstream & wyjscie, bool CzyBlad)
{
	char B = 0;
	int ilosc = (Wektor.size() / 16);
	if (CzyBlad)
	{
		for (unsigned int i = 0; i < Wektor.size(); i++)
			wyjscie.put(Wektor[i] + '0');
		wyjscie.put('\n');
	}
	else // Ke
	{
		int Pozycja = 7;
		for (int i = 1; i <= ilosc; i++) {
			for (int j = (i - 1) * 8; j < i * 8; j++) {
				if (Wektor[j]) {
					B |= (1 << Pozycja);
				}
				else {
					B &= (~(1 << Pozycja));
				}
				Pozycja--;
			}
			wyjscie.put(B);
			B = 0;
			Pozycja = 7;
		}	
	}
}

void CharNaBajt(char B, vector<bool>& Wektor, fstream &plik)
{

	while (B != '\n')
	{
		Wektor.push_back(B == '1');
		plik.get(B);
	}
}

void IloczynHE(vector <bool> &bParzystosci, vector<bool> &Wektor) 
{
	for (int Wiersze = 0; Wiersze < X; Wiersze++)
	{
		int suma = 0;
		for (int Kolumny = 0; Kolumny < Y; Kolumny++)
			suma += Wektor[Kolumny] * H[Wiersze][Kolumny];

		bParzystosci.push_back(suma % 2);
	}
}

void WykrywanieBledu(vector<bool> bParzystosci, vector <int> &WektorBledow, bool &CzyWystapilBlad)
{
	// 1 b³¹d bitowy
	int ERR = 0;
	for (int Kolumny = 0; Kolumny < Y; Kolumny++)
	{
		for (int Wiersze = 0; Wiersze < X; Wiersze++)
			if (bParzystosci[Wiersze] == H[Wiersze][Kolumny])
				ERR++;
		if (ERR == X)
		{
			cout << "Blad w kolumnie " << Kolumny+1 << endl;
			WektorBledow.push_back(Kolumny + 1);
			CzyWystapilBlad = true;
			return;
		}
		ERR = 0;
	}

	// 2 b³êdy bitowe
	ERR = 0;
	for (int Kolumny = 0; Kolumny < Y; Kolumny++)
		for (int Kolumny2 = 0; Kolumny2 < Y; Kolumny2++)
			if (Kolumny2 > Kolumny)
			{
				for (int Wiersze = 0; Wiersze < X; Wiersze++)
					if ((H[Wiersze][Kolumny] + H[Wiersze][Kolumny2]) %2 == bParzystosci[Wiersze])
						ERR++;
				if (ERR == X)
				{
					cout << "Blad w kolumnach " << Kolumny + 1 << " i " << Kolumny2+1 << endl;
					
					WektorBledow.push_back(Kolumny+1);
					WektorBledow.push_back(Kolumny2+1);
					CzyWystapilBlad = true;
					return;
				}
				ERR = 0;
			}
}

void PoprawBit(vector<bool>& Wektor, int MiejsceBledu)
{
	Wektor[MiejsceBledu-1] = !Wektor[MiejsceBledu-1];
}
