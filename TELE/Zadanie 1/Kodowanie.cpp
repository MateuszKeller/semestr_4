#include "Kodowanie.h"

// ----------------------------------------------- //
void testMacierzy8x16(bool TEST)
{
	const vector<int> T = { 17, 55, 107, 211, 173, 85, 153, 255, 128, 64, 32,16,8,4,2,1 };

	for (int i = 0; i < 16; i++)
	{
		if (TEST) cout << i << ". :\n";
		for (int j = i + 1; j < 16; j++)
		{
			int W = T[i] + T[j];


			if (TEST)
				std::cout << T[i] << " + " << T[j] << " = " << W << "   (" << j << ")" << std::endl;

			for (int m = i; m < 16; m++)
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

void WypiszWektor(vector<bool> &Wektor)
{
	for (int i = 0; i < Wektor.size(); i++)
	{
		if (i % 8 == 0 && i != 0)
			cout << " ";
		cout << Wektor[i];
	}
}

int Mnozenie(vector<bool>& Wektor)
{
	return 0;
}

void KodujBajt(vector<bool> &Wektor, bool TestSum)
{
	int suma = 0;
	for (int Wiersze = 0; Wiersze < 4; Wiersze++)
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
	int ilosc = (Wektor.size() / 8);
	if (CzyBlad)
	{
		for (unsigned int i = 0; i < Wektor.size(); i++)
			wyjscie.put(Wektor[i] + '0');
		wyjscie.put('\n');
	}
	else // No idea how most of this works ?!?!
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

void SzukajBledu(vector<bool>& Wektor)
{
	int suma = 0;
	vector<bool> KolumnaBledu;
	for (int Wiersze = 0; Wiersze < 4; Wiersze++)
	{
		for (int Kolumny = 0; Kolumny < 8; Kolumny++)
		{
			//int x = PlikB[Kolumny]; int y = H[Wiersze][Kolumny];
			suma += Wektor[Kolumny] * H[Wiersze][Kolumny];
		}

		KolumnaBledu.push_back(suma % 2);
		suma = 0;
	}

	for (int i = 0; i < 12; i++)
		if (H[0][i] == KolumnaBledu[0] && H[1][i] == KolumnaBledu[1] && H[2][i] == KolumnaBledu[2] && H[3][i] == KolumnaBledu[3])
			cout << "Blad wystapil w bicie " << i;


	
}
