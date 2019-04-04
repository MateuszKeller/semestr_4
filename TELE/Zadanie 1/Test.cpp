#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using std::fstream;
using std::ios;



void test(const char * nazwa, bool binarnie)
{
	std::ofstream plik(nazwa, binarnie ? std::ios::binary : std::ios::out);

	for (char c = 0; c < 32; ++c)
		plik.put(c);

}

void testy()
{
	test("1.bin", false);
	//test("2.bin", true);

	//std::ifstream plik("2.bin", std::ios::binary);// : std::ios::out);

	std::ofstream plik("test.bin", std::ios::binary);
	int x = 156721;
	plik.write((const char *)& x, sizeof x);
	plik.close();

	std::ifstream plik2("a.txt", std::ios::binary);
	int y = 0;
	plik2.read((char *)& y, sizeof y);
	std::cout << y << std::endl;

	plik2.close();

	std::string tekst = "Alamakota";
	std::ofstream plik3("b.bin", std::ios::binary);
	plik3.write(tekst.c_str(), tekst.size() + 1);

	plik3.close();

	//std::ifstream plik4("b.bin", std::ios::binary);
	std::ifstream plik4("a.txt", std::ios::binary);
	std::string str;
	std::getline(plik4, str, '\0');
	std::cout << str << std::endl;

	plik4.close();

	std::cout << "------------------------------" << std::endl;

	plik4.open("a.txt", std::ios::binary);
	/*char T[8];
	for (int i = 0; i < 8; i++)
		T[i]=' ';*/

	int T;
	plik4.read((char *)&T, 1);

	//for (int i = 0; i < 8; i++)
	//	std::cout << T[i];

	std::cout << T;
}

int main2()
{
	testy();

	system("pause");
	return 0;
}