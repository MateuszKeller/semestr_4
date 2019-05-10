// zadanie2.cpp : Ten plik zawiera funkcję „main”. W nim rozpoczyna się i kończy wykonywanie programu.
//

#include <iostream>
#include <Windows.h>
//#include "sock.h"

#define SOH 1
#define EOT 4
#define ACK 6
#define NAK 21
#define C 67 // 'C'
#define SUB 26

using namespace std;

HANDLE hComm;

void setup(const char* port_name) {
	
	BOOL bPortReady;
	DCB dcb;
	int BaudRate = 57600;

	hComm = CreateFile(port_name,			//port name
		GENERIC_READ | GENERIC_WRITE,	//Read/Write
		0,								//No Sharing
		NULL,							//No Security
		OPEN_EXISTING,					//open existing port only
		0,								//non overlapped I/O
		NULL);							//Null for Comm Devices

	if (hComm == INVALID_HANDLE_VALUE)
		cout << "Error in opening serial port" << endl;
	else
		cout << "opening serial port succesful" << endl;
	bPortReady = SetupComm(hComm, 1, 128);

	bPortReady = GetCommState(hComm, &dcb);

	dcb.BaudRate = BaudRate;
	dcb.ByteSize = 8;
	dcb.Parity = NOPARITY;
	dcb.StopBits = ONESTOPBIT;
	dcb.fAbortOnError = TRUE;

	dcb.fOutX = FALSE;
	dcb.fInX = FALSE;

	dcb.fOutxCtsFlow = FALSE;
	dcb.fRtsControl = RTS_CONTROL_HANDSHAKE;

	dcb.fOutxDsrFlow = FALSE;
	dcb.fDtrControl = DTR_CONTROL_ENABLE;
	dcb.fDtrControl = DTR_CONTROL_DISABLE;
	dcb.fDtrControl = DTR_CONTROL_HANDSHAKE;

	bPortReady = SetCommState(hComm, &dcb);
}

void Send(char* toSend, int length) {
	DWORD quantity, position =0;
	while (position < length) {
		WriteFile(hComm, toSend + position, length - position, &quantity, NULL);
		position = position + quantity;
	}
}

void Recieve(char* toRecieve, int length) {
	DWORD quantity, position = 0;
	while (position < length) {
		ReadFile(hComm, toRecieve + position, length - position, &quantity, NULL);
		position = position + quantity;
	}
}

unsigned int compute_crc(char* payload) {
	int temp = 0; 
	int value = 0x18005 << 15;
	for (int i = 0; i < 3; i++)
		temp = temp * 256 + (unsigned char) payload[i];
	temp = temp * 256;

	for (int i = 3; i < 134; i++) {
		if (i < 128)
			temp = temp + (unsigned char)payload[i];
		for (int j = 0; j < 8; j++) {
			if (temp & (1 << 31))
				temp <<= 1; 
		}
	}
	return temp >> 16;
}

void reciever(char* fileName, bool useCrc) {
	char header[3];
	char payload[128];
	if (useCrc) header[0] = C;
	else header[0] = NAK;
	Send(header, 1);

	FILE* output = fopen(fileName, "wb");

	while (true) {
		Recieve(header, 1);
		if (header[0] == EOT)
			break;

		unsigned short checksum_given, checksum_computed;
		Recieve(header + 1, 2);
		Recieve(payload, 128);

		checksum_given = checksum_computed = 0;
		if (useCrc) {
			Recieve((char*)& checksum_given, 2);
		} else {
			Recieve((char*)& checksum_given, 1);
		}

		if (useCrc) {
			checksum_computed = compute_crc(payload);
		} else {
			for (int i = 0; i < 128; i++)
				checksum_computed = checksum_computed + (unsigned char)payload[i];
			checksum_computed = checksum_computed % 256;
		}
		if (checksum_given != checksum_computed) {
			header[0] = NAK;
			Send(header, 1);
			continue;
		}

		header[0] = ACK;
		Send(header, 1);

		unsigned char last = 127;
		while (payload[last] == SUB)
			last--;
		fwrite(payload, last + 1, 1, output);
	}

	fclose(output);
	header[0] = ACK;
	Send(header, 1);

}

void sender(char* fileName, bool useCrc){
	
	char header[3]; 
	char payload[128];
	Recieve(header, 1);
	if (header[0] == NAK)
		useCrc = 0;
	else if (header[0] == C)
		useCrc = 1;
	else{
		cout << "Unexpected communicate" << endl;
		return;
	}
	int packageNumber = 1;
	FILE* input = fopen(fileName, "rb");
	fseek(input, 0, SEEK_END);
	int inputSize = ftell(input);
	fseek(input, 0, SEEK_SET);
	while (ftell(input) < inputSize) {
		unsigned char packageLength = fread(payload, 1, 128, input);
		for (int i = packageLength; i < 128; i++)
			payload[i] = SUB;
		unsigned short checksum = 0; 
		if (useCrc)
			checksum = compute_crc(payload);
		else {
			for (int i = 0; i < 128; i++)
				checksum = checksum + (unsigned char)payload[i];
			checksum = checksum % 256;
		}
		header[0] = SOH;
		header[1] = packageNumber;
		header[2] = 255 - packageNumber;

		Send(header, 3);
		Send(payload, 128);
		if (useCrc == 1)
			Send((char*)& checksum, 2);
		else
			Send((char*)& checksum, 1);

		Recieve(header, 1);
		if (header[0] == ACK)
			packageNumber++;
		else
			fseek(input, -128, SEEK_CUR);
	}
	fclose(input);
	do {
		header[0] = EOT; 
		Send(header, 1);
		Recieve(header, 1);
	} while (header[0] != ACK);
}


int main()
{
	setup("COM1");
	std::string str = "input.txt";
	char* fileName = new char[str.length()+1];
	strcpy(fileName, str.c_str());
	bool useCrc; 
	char option;
	
	cout << "if crc choose 1, else choose 0" << endl;
	cin >> useCrc;
	cout << "send [s] or recieve [r]" << endl;
	cin >> option;

	switch (option) {
	case 's':
	case 'S':
		sender(fileName, useCrc);
	case 'r':
	case 'R':
		reciever(fileName, useCrc);
	}

	CloseHandle(hComm);					//closing serial port
	return 0; 
}

// Uruchomienie programu: Ctrl + F5 lub menu Debugowanie > Uruchom bez debugowania
// Debugowanie programu: F5 lub menu Debugowanie > Rozpocznij debugowanie

// Porady dotyczące rozpoczynania pracy:
//   1. Użyj okna Eksploratora rozwiązań, aby dodać pliki i zarządzać nimi
//   2. Użyj okna programu Team Explorer, aby nawiązać połączenie z kontrolą źródła
//   3. Użyj okna Dane wyjściowe, aby sprawdzić dane wyjściowe kompilacji i inne komunikaty
//   4. Użyj okna Lista błędów, aby zobaczyć błędy
//   5. Wybierz pozycję Projekt > Dodaj nowy element, aby utworzyć nowe pliki kodu, lub wybierz pozycję Projekt > Dodaj istniejący element, aby dodać istniejące pliku kodu do projektu
//   6. Aby w przyszłości ponownie otworzyć ten projekt, przejdź do pozycji Plik > Otwórz > Projekt i wybierz plik sln
