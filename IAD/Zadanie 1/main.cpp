#include <iostream>
#include <vector>
#include <fstream>
#include <math.h>
#include <iomanip>

using namespace std;
typedef vector<double> wiersz;

void wypisz(double ** dane,int wiersze, int kolumny){
    for(int i = 0; i < wiersze; i++){
        for (int j = 0; j < kolumny; j++){
            cout<<dane[i][j]<<" ";
        }
        cout<<endl;
    }

}

int policz(int N, int n, int nr_populacji, double ** dane){
    int ilosc = 0;
    for(int i = 0; i < N; i++){
        if(dane[i][1] == 0){
            ilosc = ilosc + 1;
        }
    }
    return ilosc;
}

void przepisz(int nr_populacji, int N, int cecha, int n, double * lista, double ** dane){
    int j = 0;
    for (int i = 0 ; i < N; i++){
        if(dane[i][n] == nr_populacji){
            lista[j] = dane[i][cecha];
            j = j + 1;
        }
    }
}

void sortuj(int ilosc, double * lista){
    double temp;
    for(int i = 0; i < ilosc ; i++){
        for (int j = 1; j < ilosc; j++){
            if (lista[j] < lista[j - 1]){
                temp = lista[j];
                lista[j] = lista[j-1];
                lista[j-1]=temp;
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

double moment_centralny(int ilosc, int k, double * lista, double srednia_aryt){
    double moment_centr = 0;
    for(int j = 0; j < ilosc; j++){
        moment_centr = moment_centr + pow(lista[j]-srednia_aryt,k);
    }
    moment_centr = moment_centr/(ilosc-1);
    return moment_centr;
}


double st_swobody(int nr_populacji_1,int nr_populacji_2,double ** raport){
    double st_swobody;
    st_swobody = pow((raport[0][7]/raport[0][0] + raport[1][7]/raport[1][0]),2)/(pow(raport[0][7]/raport[0][0],2)/(raport[0][0]-1)+pow(raport[1][7]/raport[1][0],2)/(raport[1][0]-1));
    return st_swobody;
}

double roznica_srednich (int nr_populacji_1, int nr_populacji_2, double ** raport){
    double z;
    z = (raport[1][1] - raport[0][1])/sqrt(raport[0][7]/raport[0][0]+raport[1][7]/raport[1][0]);
    return z;
}

double prawdopodobienstwo(double z){
    double p;
    z = -fabs(z);
    p = 2*(1/2.0*(1.0+erf(z/sqrt(2.0))));
    return p;
}


int main()
{
    int N; //ilosc wierszy w pliku
    cout << "Hello world!" << endl;

    ifstream wejscie;
        string nazwa = "217107.txt";

        //cout << "Wprowadz nazwe pliku z danymi: ";
        //getline( cin ,nazwa );
        wejscie.open(nazwa.c_str());

        if (wejscie.good() == false) {cout << "Nie mozna odczytac danych z pliku"<<endl;}
        string linia;
        N = 0;

        while (getline(wejscie, linia))
            {
            N++;
            }
        wejscie.close();
            cout << "ilosc wierszy: " << N <<endl;

        int p = 2;
        //cout<<"wprowadz ilosc badanych populacji: "<<endl;
        //cin>>p;
        double ** dane = new double*[N];
        int n = 1;

        for(int i = 0; i <N; i++){
            dane[i] = new double[n+1];
        }
        //cout<<"wprowadz ilosc badanych cech: "<<endl;
        //cin>>n;

        wejscie.open(nazwa.c_str());

    //zapisanie danych z pliku do tablicy

        for (int i = 0; i < N; i++){
                for (int j = 0; j <= n; j++){
                    wejscie>>dane[i][j];
                }
        }
        wejscie.close();

        //wypisz(dane,N,n+1);

        //yznaczenie srednich

        double ** raport = new double *[p];
        for(int i = 0; i < p; i++){
            raport[i] = new double[13];
        }


        for(int cecha = 0; cecha < n; cecha++){


        double iloczyn;




            for(int nr_populacji = 0; nr_populacji < p; nr_populacji++){

                int ilosc = policz(N, n, nr_populacji, dane);
                double * lista = new double[ilosc];
                przepisz(nr_populacji, N, cecha, n, lista, dane);
                sortuj(ilosc,lista);

                double srednia_aryt = srednia_arytmetyczna(ilosc, lista);
                double srednia_harm = srednia_harmoniczna(ilosc, lista);
                double srednia_geom = srednia_geometryczna(ilosc, lista);

                cout<<"srednia arytmetyczna populacji "<< nr_populacji <<": "<<  srednia_aryt<<endl;
                cout<<"srednia harmoniczna populacji "<< nr_populacji <<": "<<  srednia_harm<<endl;
                cout<<"srednia geometryczna populacji "<< nr_populacji <<": "<< srednia_geom<<endl;

                raport[nr_populacji][0] = ilosc;
                raport[nr_populacji][1] = srednia_aryt;
                raport[nr_populacji][2] = srednia_geom;
                raport[nr_populacji][3] = srednia_harm;
                //for(int i = 0; i < ilosc; i++){
                 //   cout<<lista[i]<<endl;
                //}


                double domi = dominanta(ilosc,lista);

                raport[nr_populacji][4] = domi;

                double medi = mediana(ilosc, lista);
                cout<<" mediana wynosi: " <<  medi <<endl;
                raport[nr_populacji][5] = medi;








            //k-ty moment centralny

                double moment_centr;
                double odchylenie_standardowe;
                double moment_stand3;

                for(int k = 1; k <=4; k++){
                    moment_centr = moment_centralny(ilosc, k, lista, srednia_aryt);
                    raport[nr_populacji][5+k] = moment_centr;
                    cout<<"Moment centralny "<< k <<"-go rzedu wynosi: "<< setprecision(4)<<  moment_centr <<endl;
                    if(k == 2){
                        odchylenie_standardowe = sqrt(moment_centr);
                    }
                    else if(k == 3){
                        moment_stand3 = moment_centr/pow(odchylenie_standardowe,3);
                    }
                }

                raport[nr_populacji][10] = odchylenie_standardowe;
                raport[nr_populacji][11] = moment_stand3;
                cout << "Odchylenie standardowe wynosi: " << setprecision(4)<< odchylenie_standardowe << endl;
                cout << "Moment standaryzowany 3-go rzedu wynosi: " <<  setprecision(4)<< moment_stand3 << endl;

             //kurtoza

                double kurtoza;
                kurtoza = moment_centr / pow(odchylenie_standardowe,4) - 3.0;
                raport[nr_populacji][12] = kurtoza;
                cout<<"kurtoza wynosi: "<<  setprecision(4) << kurtoza << endl;

                delete [] lista;
            }

            //przygotowanie histogramow

            int k = sqrt(N); //ilosc przedzialow

            double ** histogram = new double*[k];


        for(int i = 0; i <k; i++){
            histogram[i] = new double[p];
        }

        double maxv = dane[0][cecha];
        double minv = dane[0][cecha];
        double temp;

        for (int i = 0; i < N; i++){
                if(dane[i][cecha] > maxv) maxv = dane[i][cecha];
                else if (dane[i][cecha] < minv) minv = dane[i][cecha];
        }
        cout<<"max " << maxv<<endl;
        cout<<"min " << minv << endl;

        double range = (maxv - minv)/k; //rozpietosc przedizalu


        for(int i = 0; i <k; i++){
            for(int j = 0; j < p; j++){
                    histogram[i][j] = 0;
                    for (int m = 0; m < N; m++){

                        if(dane[m][n] == j && dane[m][cecha] >= minv + i*range && dane[m][cecha] <= minv + (i+1)*range ){
                            histogram[i][j] = histogram[i][j] + 1;
                        }
                    }
            }
        }



        /*temp = 0;
        for (int i = 0; i < k; i++){
                cout<< minv + i* range << " ";
            for(int j = 0; j < p; j++){
                cout<< histogram[i][j] << " ";
            }
            cout<<endl;

        }

        //if(maxv > minv + k * range)cout<<"tak"<<endl;
        //else cout<<"nie"<<endl;

        //cout<<maxv<<endl;
        //cout<<minv + (k) * range<<endl;

        /*for (int i = 0; i < p; i++){
                temp = 0;
            for (int j = 0; j < N; i++){
                if(dane[i][0]>maxv - range){temp = temp+1;
                cout<<temp; }
            }
        }*/
       // ---------------------------------------

        char numer[10] = {'0','1','2','3','4','5','6','7','8','9'};
        char zapis[16] = {'h','i','s','t','o','g','r','a','m','0','0','.','t','x','t'};
        int x = floor(cecha/10) ;


        zapis[9]= numer[x];
        zapis[10] = numer[cecha%10];
        cout <<zapis<<endl;
    ofstream wyjscie(zapis);
    if(wyjscie.good()==false)cout<<"nie mozna zapisac do pliku"<<endl;
    for (int i = 0 ; i < k ; i++){
            wyjscie<<setprecision(2)<<(minv+i*range)<<"-"<<setprecision(2)<<(minv+(i+1)*range)<<" ";
        for(int j = 0; j < p; j++){
            wyjscie<<histogram[i][j]<<" ";
        }
        wyjscie<<endl;
    }
    wyjscie.close();

    for(int i = 0; i < p; i++){
        for (int j = 0; j < 13; j++){
            cout<<raport[i][j]<<"   ";
        }
        cout<<endl;
    }



    cout<<"z: "<<roznica_srednich(0,1,raport)<<endl;
    cout<<" p: "<<prawdopodobienstwo(roznica_srednich(0,1,raport))<<endl;
    cout<<"st_swobody: "<<st_swobody(0,1,raport)<<endl;



        }










        return 0;


}
