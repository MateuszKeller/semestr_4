#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <sstream>
#include <math.h>
#include <cstdlib>

using namespace std;

struct rekord {
    vector<double> atrybut;
    int populacja;
};

struct raport {
int ilosc;
double srednia_arytmetyczna;
double srednia_geometryczna;
double srednia_harmoniczna;
double dominanta;
double mediana;
double moment_centralny_1;
double moment_centralny_2;
double moment_centralny_3;
double moment_centralny_4;
double odchylenie_standardowe;
double moment_standaryzowany_3;
double kurtoza;
};

int policz(int N, int nr_populacji, rekord * dane){
    int ilosc = 0;
    for(int i = 0; i < N; i++){
        if (dane[i].populacja == nr_populacji){
            ilosc = ilosc + 1;
        }
    }
    return ilosc;
}

void przepisz(int nr_populacji, int N, int cecha, double * lista, rekord * dane){
    int j = 0;
    for (int i = 0 ; i < N; i++){
        if(dane[i].populacja == nr_populacji){
            lista[j] = dane[i].atrybut[cecha];
            j = j + 1;
        }
    }
}

void sortuj(int ilosc, double * lista){
    double temp;
    for(int i = 0; i < ilosc; i++){
        for(int j = 1; j < ilosc; j++){
            if(lista[j] < lista[j-1]){
                temp = lista[j];
                lista[j] = lista[j-1];
                lista[j-1]= temp;
            }
        }
    }
}

double srednia_arytmetyczna(int ilosc, double * lista){
    double suma = 0;
    for (int i = 0; i < ilosc; i++){
        suma = suma + lista[i];
    }
    suma = suma/ilosc;
    return suma;
}

double srednia_harmoniczna(int ilosc, double * lista){
    double suma = 0;
    for(int i = 0; i < ilosc; i++){
            suma = suma + 1.0/lista[i];
    }
    suma = ilosc/suma;
    return suma;
}

double srednia_geometryczna(int ilosc, double * lista){
    double iloczyn = 1;
    for(int i = 0; i < ilosc; i++){
        iloczyn = iloczyn * lista[i];
    }
    iloczyn = pow(iloczyn,1.0/ilosc);
    return iloczyn;
}

double dominanta(int ilosc, double * lista){
    double dominanta;
    int c;
    double w;
    int maxc = 0;
    double maxw;
    for (int i = 0; i < ilosc; i++){
        w = lista[i];
        c = 0;
        for(int j = 0; j < ilosc; j++){
            if (w == lista[j]) {c++;}
        }
        if(c > maxc){
            maxc = c;
            maxw = w;
        }
    }
    if(maxc == 1){
        cout<<"Nie mozna wskazac dominanty - wartosci sa unikatowe"<<endl;
        return 0;
    }
    else {
        cout<< "Element " << maxw << "wystepuje " << maxc << "razy" << endl;
        return maxw;
    }
}

double mediana(int ilosc, double * lista){
    double mediana;
    if(ilosc%2 == 0){
        mediana = (lista[ilosc/2] + lista[ilosc/2 + 1])/2;
    }
    else mediana = lista[(ilosc + 1)/2];
    return mediana;
}

double moment_centralny(int ilosc, int k, int nr_populacji, double * lista, raport * raporty){
    double moment_centr = 0;
    for(int j = 0; j < ilosc; j++){
        moment_centr = moment_centr + pow(lista[j]-raporty[nr_populacji].srednia_arytmetyczna,k);
    }
    moment_centr = moment_centr/(ilosc);
    return moment_centr;
}

bool plik_dla_gpl (char zapis[],char numer[], int cecha, int k, double minv, double range, double ** histogram, vector <string> populacje){
    int x = floor(cecha/10);
    zapis[9]= numer[x];
    zapis[10] = numer[cecha%10];
    ofstream wyjscie(zapis);
    if(wyjscie.good()==false){
            cout<<"nie mozna zapisac do pliku"<<endl;
            return false;
    }
        wyjscie.precision(2);
        wyjscie << fixed;
        wyjscie <<"'\'\ ";
        for (int a = 0; a < populacje.size(); a++){
            wyjscie << populacje[a] << " ";
        }
        wyjscie << endl;
        for (int i = 0 ; i < k ; i++){
            wyjscie<<(minv+i*range)<<"-"<<(minv+(i+1)*range)<<" ";
            for(int j = 0; j < populacje.size(); j++){
                wyjscie<<histogram[i][j]<<" ";
            }
        wyjscie<<endl;
        }
    wyjscie.close();

    ofstream wyjscie2("skrypt.gpl");
    if(wyjscie2.good()==false)return false;
    wyjscie2 << "reset" << endl;
    wyjscie2 << "set title 'Rozklad parametru "<< cecha <<" w populacjach obiektow badanych klas'" << endl;
    wyjscie2 << "set key fixed right top vertical Right noreverse noenhanced autotitle nobox" << endl;
    wyjscie2 << "set style data histograms" << endl;
    wyjscie2 << "set style histogram clustered gap 1" << endl;
    wyjscie2 << "set term png" << endl;
    wyjscie2 << "set output 'histogram"<< floor(cecha/10)<<cecha%10 << ".png'" << endl;
    wyjscie2 << "set xtics nomirror rotate by -45" << endl;
    wyjscie2 << "set boxwidth 0.9 absolute" << endl;
    wyjscie2 << "set xtics ()" << endl;
    wyjscie2 << "set style fill solid 1.0 border lt -1" <<endl;
    wyjscie2 << "plot '"<< zapis << "' using 2:xtic(1) title columnheader(2)";
    for(int i = 3; i < populacje.size() + 2; i++){
        wyjscie2<<",\\"<<endl;
        wyjscie2<<"'' u "<<i<<" title columnheader("<<i<<")";
    }
    wyjscie2.close();

    return true;
}

double st_swobody(int nr_populacji_1,int nr_populacji_2,raport * raporty){
    double st_swobody;
    double s1 = raporty[nr_populacji_1].moment_centralny_2;
    double s2 = raporty[nr_populacji_2].moment_centralny_2;
    double N1 = raporty[nr_populacji_1].ilosc;
    double N2 = raporty[nr_populacji_2].ilosc;

    st_swobody = pow((s1/N1+s2/N2),2.0);
    st_swobody = st_swobody/(pow(s1/N1,2)/(N1-1)+pow(s2/N2,2)/(N2-1));
    return st_swobody;
}

double roznica_srednich (int nr_populacji_1, int nr_populacji_2, raport * raporty){
    double z;
    double m1 = raporty[nr_populacji_1].srednia_arytmetyczna;
    double m2 = raporty[nr_populacji_2].srednia_arytmetyczna;
    double ro1 = raporty[nr_populacji_1].moment_centralny_2;
    double ro2 = raporty[nr_populacji_2].moment_centralny_2;
    double N1 = raporty[nr_populacji_1].ilosc;
    double N2 = raporty[nr_populacji_2].ilosc;
    z = m1 - m2;
    z = z/sqrt(ro1/N1 + ro2/N2);
    z = fabs(z);
    return z;
}

double prawdopodobienstwo(double z){
    double p;
    z = -fabs(z);
    p = 2*(1/2.0*(1.0+erf(z/sqrt(2.0))));
    return p;
}

bool drukuj_raport (int cecha, raport * raporty, vector<string> populacje)
{
    ofstream wyjscie;
    wyjscie.open("raport.txt",std::ios_base::app);
    if(wyjscie.good()==false)return false;
    else{
            wyjscie.precision(4);
            wyjscie << fixed;
            wyjscie << "STATYSTYKI DLA CECHY (KOLUMNY) " << cecha <<": " << endl;
            wyjscie << endl;
        for (int i = 0 ; i < populacje.size() ; i++){
            wyjscie << "Populacja " << populacje[i] <<endl;
            wyjscie << "ilosc N: " << raporty[i].ilosc << endl;
            wyjscie << "srednia arytmetyczna: " << raporty[i].srednia_arytmetyczna << endl;
            wyjscie << "srednia geometryczna: " << raporty[i].srednia_geometryczna << endl;
            wyjscie << "srednia harmoniczna: " << raporty[i].srednia_harmoniczna << endl;
            wyjscie << "dominanta: " << raporty[i].dominanta << endl;
            wyjscie << "mediana: " << raporty[i].mediana << endl;
            wyjscie << "moment centralny 1-go rzedu: " << raporty[i].moment_centralny_1 << endl;
            wyjscie << "moment centralny 2-go rzedu: " << raporty[i].moment_centralny_2 << endl;
            wyjscie << "moment centralny 3-go rzedu: " << raporty[i].moment_centralny_3 << endl;
            wyjscie << "moment centralny 4-go rzedu: " << raporty[i].moment_centralny_4 << endl;
            wyjscie << "odchylenie standardowe " << raporty[i].odchylenie_standardowe << endl;
            wyjscie << "moment standaryzowany 3-go rzedu: " << raporty[i].moment_standaryzowany_3 << endl;
            wyjscie << "kurtoza: " << raporty[i].kurtoza << endl;
            wyjscie << endl;
        }
        return true;
    }
}

bool drukuj_raport2(double z, double prawd, double st, int nr_populacji_1, int nr_populacji_2, vector<string> populacje){
    ofstream wyjscie;
    wyjscie.open("raport.txt",std::ios_base::app);
    if(wyjscie.good()==false)return false;
    else{
        wyjscie <<"Porownanie populacji " << populacje[nr_populacji_1] << " oraz " << populacje[nr_populacji_2] <<": "<<endl;
        wyjscie << "standaryzowana roznica srednich: " << z << endl;
        wyjscie << "prawdopodobienstwo: " << prawd << endl;
        wyjscie << "ilosc stopni swobody rozkladu t-Studenta" << st << endl;
        if( prawd < 0.01){
            wyjscie << "H0 odrzucona(H1 jest mozliwa)" << endl;
        }
        else wyjscie << "H0 jest mozliwa" << endl;
        wyjscie << "-------------------------------------------------------------" <<endl;
        wyjscie << endl;

    wyjscie.close();
    return true;
    }
}

int main()
{
    int N;                              //ilosc wierszy w pliku

    ifstream wejscie;
    string nazwa;
    cout << "Wprowadz nazwe pliku z danymi: ";
    getline( cin ,nazwa );
    wejscie.open(nazwa.c_str());

    if (wejscie.good() == false) {cout << "Nie mozna odczytac danych z pliku"<<endl;}

    string linia;
    N = 0;

    while (getline(wejscie, linia)) N++;

    wejscie.close();
    cout << "ilosc wierszy: " << N <<endl;

    rekord * dane = new rekord[N];      //deklaracja tablicy na dane

    wejscie.open(nazwa.c_str());

    //obliczenie iloœci atrybutów

    string s;
    int n = 0;          //ilosc badanych atrybutow
    getline(wejscie,s);
    size_t pos = 0;
    char delimiter ;

    cout<<"Wczytaj separator danych w pliku: (jesli spacja wybierz 0)"<<endl;
    cin>>delimiter;
    if(delimiter == '0') delimiter = ' ';
    string delimiter_s (1,delimiter);
    while ((pos = s.find(delimiter_s)) != std::string::npos) {
            s.erase(0, pos + delimiter_s.length());
            n ++;
    }

    wejscie.close();
    //cout<<n<<endl;

    vector <string> populacje;      //vector ze spisem populacji
    wejscie.open(nazwa.c_str());

    string temp;
    double temp2;

    //wczytanie danych do tablicy i zamiana etykiet na int

    for (int j = 0; j < N; j++){
        for(int i = 0; i < n; i++){
            getline(wejscie,temp,delimiter);
            stringstream przeksztalc(temp);
            przeksztalc>>temp2;
            dane[j].atrybut.push_back(temp2);
        }
        getline(wejscie,temp);
        int kontrola = 0;
        for(int k = 0; k < populacje.size(); k++){
            if(temp == populacje[k]){
                    dane[j].populacja = k;
                    kontrola ++;
                    break;
            }
        }
        if(kontrola == 0){
            populacje.push_back(temp);
            dane[j].populacja = populacje.size()-1;
        }

    }
    //cout<<"pop"<< populacje.size();
    //populacje.pop_back();

    int p = populacje.size();    //ilość populacji


   /* for (int i = 0; i < N; i++){
        for(int j = 0; j < n; j++) cout <<dane[i].atrybut[j]<<" ";
            cout<<dane[i].populacja<<endl;
            }*/
    wejscie.close();
    raport * raporty = new raport[p];



    char numer[10] = {'0','1','2','3','4','5','6','7','8','9'};
    char zapis[16] = {'h','i','s','t','o','g','r','a','m','0','0','.','t','x','t'};
    for(int cecha = 0; cecha < n; cecha++){
        for (int nr_populacji = 0; nr_populacji < p; nr_populacji ++){
            cout<<"Statystyki dla populacji " << populacje[nr_populacji]<<": "<<endl;
            int ilosc = policz(N,nr_populacji,dane);
            cout<<"ilosc: "<<ilosc<<endl;

            double * lista = new double[ilosc];
            przepisz(nr_populacji, N, cecha, lista, dane);


            sortuj(ilosc,lista);
//for(int i = 0; i < ilosc;i++){cout<<lista[i]<<endl;}
            double srednia_aryt = srednia_arytmetyczna(ilosc,lista);
            double srednia_harm = srednia_harmoniczna(ilosc,lista);
            double srednia_geom = srednia_geometryczna(ilosc, lista);

            raporty[nr_populacji].ilosc = ilosc;
            raporty[nr_populacji].srednia_arytmetyczna = srednia_aryt;
            raporty[nr_populacji].srednia_harmoniczna = srednia_harm;
            raporty[nr_populacji].srednia_geometryczna = srednia_geom;

            cout<<"srednia arytmetyczna populacji "<< nr_populacji <<": "<<  srednia_aryt<<endl;
            cout<<"srednia harmoniczna populacji "<< nr_populacji <<": "<<  srednia_harm<<endl;
            cout<<"srednia geometryczna populacji "<< nr_populacji <<": "<< srednia_geom<<endl;

            double domi = dominanta(ilosc,lista);
            raporty[nr_populacji].dominanta = domi;

            double medi = mediana(ilosc, lista);
            cout<<"Mediana: " <<  medi <<endl;
            raporty[nr_populacji].mediana = medi;

            raporty[nr_populacji].moment_centralny_1 = moment_centralny(ilosc, 1, nr_populacji, lista, raporty );
            raporty[nr_populacji].moment_centralny_2 = moment_centralny(ilosc, 2, nr_populacji, lista, raporty );
            raporty[nr_populacji].moment_centralny_3 = moment_centralny(ilosc, 3, nr_populacji, lista, raporty );
            raporty[nr_populacji].moment_centralny_4 = moment_centralny(ilosc, 4, nr_populacji, lista, raporty );

            cout<<"Moment centarlny 1-go rzedu: "<<raporty[nr_populacji].moment_centralny_1<<endl;
            cout<<"Moment centarlny 2-go rzedu: "<<raporty[nr_populacji].moment_centralny_2<<endl;
            cout<<"Moment centarlny 3-go rzedu: "<<raporty[nr_populacji].moment_centralny_3<<endl;
            cout<<"Moment centarlny 4-go rzedu: "<<raporty[nr_populacji].moment_centralny_4<<endl;

            raporty[nr_populacji].odchylenie_standardowe = sqrt(raporty[nr_populacji].moment_centralny_2);
            raporty[nr_populacji].moment_standaryzowany_3 = raporty[nr_populacji].moment_centralny_3/pow(raporty[nr_populacji].odchylenie_standardowe,3);

            cout << "Odchylenie standardowe: " << raporty[nr_populacji].odchylenie_standardowe<<endl;
            cout << "Moment standaryzowany 3-go rzedu: " << raporty[nr_populacji].moment_standaryzowany_3 <<endl;

            raporty[nr_populacji].kurtoza = raporty[nr_populacji].moment_centralny_4/pow(raporty[nr_populacji].odchylenie_standardowe,4)-3.0;
            cout << "Kurtoza: " << raporty[nr_populacji].kurtoza << endl;

            delete [] lista;
            cout << endl;
        }

        int k = sqrt(N);
        double ** histogram = new double *[k];

        for(int i = 0; i < k; i++){
            histogram[i] = new double[populacje.size()];
            }

        double maxv = dane[0].atrybut[cecha];
        double minv = dane[0].atrybut[cecha];

        for(int i = 0; i < N; i++){
            if(dane[i].atrybut[cecha] > maxv) maxv = dane[i].atrybut[cecha];
            else if (dane[i].atrybut[cecha] < minv) minv = dane[i].atrybut[cecha];
        }

        double range = (maxv - minv)/k;
        for(int i = 0; i < k; i++){
            for(int j = 0; j < populacje.size(); j++){
                histogram[i][j] = 0;
                for(int m = 0;m < N; m++){
                    if(dane[m].populacja == j && dane[m].atrybut[cecha] >= minv + i*range && dane[m].atrybut[cecha] <= minv + (i+1)*range){
                        histogram[i][j] = histogram[i][j]+1;
                    }
                }
            }
        }

        plik_dla_gpl(zapis, numer, cecha, k, minv, range, histogram, populacje);
        system("gnuplot -p -e \"load 'skrypt.gpl'\"");
        drukuj_raport(cecha, raporty, populacje);
        double z;
        double prawd;
        double st;
        for(int i = 0; i < populacje.size(); i++){
            for (int j = i+1; j < populacje.size(); j++){
                z = roznica_srednich(i,j,raporty);
                prawd = prawdopodobienstwo(-fabs(z));
                st = st_swobody(i,j,raporty);
                cout<<"z: "<<z<<endl;
                cout<<"prawd: "<<prawd<<endl;
                cout<<"st: "<<st<<endl;
                drukuj_raport2(z,prawd,st,i,j,populacje);
            }
        }
    }
    return 0;
}
