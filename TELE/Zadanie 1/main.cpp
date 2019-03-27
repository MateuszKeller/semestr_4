#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

int main()
{
	fstream input("plik_testowy.txt", ios::in | ios::binary);

	std::vector <bool> T;
	char B;

	while (input.get(B))
	{
		if (B != EOF)
			//Zmiana bajtu na bity
			for (int y = (sizeof(char) * 8) - 1; y >= 0; y--) 
				T.push_back(B & (1 << y)); 
	}

	system("pause");
	return 0;
}