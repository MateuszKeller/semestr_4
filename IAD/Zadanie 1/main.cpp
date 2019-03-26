#include <iostream>
#include <vector>
#include <fstream>
#include <math.h>
#include <iomanip>

using namespace std;
typedef vector<double> wiersz;

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

        //for(int i = 0 ; i < N; i++){
          //          cout<<dane[i][0]  << " "<<dane[i][1];
            //    cout<<endl;
        //}

        //yznaczenie srednich

        double suma_aryt;
        double suma_harm;
        double iloczyn;
        int ilosc;
        double srednia_aryt;
        double srednia_harm;
        double srednia_geom;

            for(int nr_populacji = 0; nr_populacji < p; nr_populacji++){

                    ilosc = 0;

            for(int i = 0; i < N; i++){
                    if(dane[i][1] == nr_populacji){
                        ilosc = ilosc + 1;
                    }
            }

            double * lista = new double[ilosc];

            int j = 0;
            for (int i = 0 ; i < N; i++){
                if(dane[i][1] == nr_populacji){
                    lista[j] = dane[i][0];
                    j = j + 1;
                }
            }

            double temp;

            for(int i = 0; i < ilosc ; i++){
                for (j = 1; j < ilosc; j++){
                    if (lista[j] < lista[j - 1]){
                        temp = lista[j];
                        lista[j] = lista[j-1];
                        lista[j-1]=temp;
                    }
                }
            }

                suma_aryt = 0;
                suma_harm = 0;
                iloczyn = 1;
                for(int i = 0; i < N; i++){
                    suma_aryt = suma_aryt + lista[i];
                    suma_harm = suma_harm + 1.0/lista[i];
                    iloczyn = iloczyn * lista[i];

                }
                //cout<<"suma arytm "<<suma_aryt<<endl;
                //cout<<"suma harm"<<suma_harm<<endl;
                //cout<<"iloczyn "<<iloczyn<<endl;
                //cout<<"ilosc "<<ilosc<<endl;
                srednia_aryt = suma_aryt/ilosc;
                srednia_harm = ilosc/suma_harm;
                srednia_geom = pow(iloczyn,1.0/ilosc);

                cout<<"srednia arytmetyczna populacji "<< nr_populacji <<": "<< setprecision(4)<< srednia_aryt<<endl;
                cout<<"srednia harmoniczna populacji "<< nr_populacji <<": "<< setprecision(4)<< srednia_harm<<endl;
                cout<<"srednia geometryczna populacji "<< nr_populacji <<": "<< setprecision(4)<< srednia_geom<<endl;




            //wyznaczenie dominanty


            double dominanta;

           // for (int i = 0; i < ilosc; i++){
            //    cout<<lista[i]<<endl;
            //}

            int c;
            double w;
            int maxc = 0;
            double maxw;

            for (int i = 0; i < ilosc; i++){
                w = lista[i];
                c = 0;
                    for(j = 0; j < ilosc; j++){
                            if (w == lista[j]) {c++;}
                    }
                    if(c > maxc){
                        maxc = c;
                        maxw = w;
                    }
                }
                if(maxc == 1){
                    cout<<"Nie mozna wskazac dominanty - wartosci sa unikatowe"<<endl;
                }
                else cout<< "Element " << maxw << "wystepuje " << maxc << "razy" << endl;

            double mediana;
            if(ilosc%2 == 0){
                mediana = (lista[ilosc/2] + lista[ilosc/2 + 1])/2;
            }
            else mediana = lista[(ilosc + 1)/2];

            cout<<" mediana wynosi: " <<  setprecision(4)<< mediana <<endl;

            //k-ty moment centralny
            double moment_centr = 0;
            double odchylenie_standardowe;
            double moment_stand3;

            for(int k = 1; k <=4; k++){
                for(int j = 0; j < ilosc; j++){
                    moment_centr = moment_centr + pow(lista[j]-srednia_aryt,k);
                }
                moment_centr = moment_centr/ilosc;
                cout<<"Moment centralny "<< k <<"-tego rzedu wynosi: "<< setprecision(4)<<  moment_centr <<endl;
                if(k == 2){
                        odchylenie_standardowe = sqrt(moment_centr);
                }
                else if(k == 3){
                    moment_stand3 = moment_centr/pow(odchylenie_standardowe,3);
                }
            }

            cout << "Odchylenie standardowe wynosi: " << setprecision(4)<< odchylenie_standardowe << endl;
            cout << "Moment standaryzowany 3-go rzedu wynosi: " <<  setprecision(4)<< moment_stand3 << endl;
             //kurtoza

             double kurtoza;
             kurtoza = moment_centr / pow(odchylenie_standardowe,4) - 3.0;
             cout<<"kurtoza wynosi: "<<  setprecision(4) << kurtoza << endl;





             delete [] lista;
            }

            //przygotowanie histogramow

            int k = sqrt(N);

            double ** histogram = new double*[k];


        for(int i = 0; i <k; i++){
            histogram[i] = new double[p];
        }

        double maxv = dane[0][0];
        double minv = dane[0][0];
        double temp;

        for (int i = 0; i < N; i++){
                if(dane[i][0] > maxv) maxv = dane[i][0];
                else if (dane[i][0] < minv) minv = dane[i][0];
        }
        cout<<"max " << maxv<<endl;
        cout<<"min " << minv << endl;

        double range = (maxv - minv)/k;


        for(int i = 0; i <k; i++){
            for(int j = 0; j < p; j++){
                    histogram[i][j] = 0;
                    for (int m = 0; m < N; m++){

                        if(dane[m][1] == j && dane[m][0] >= minv + i*range && dane[m][0] <= minv + (i+1)*range ){
                            histogram[i][j] = histogram[i][j] + 1;
                        }
                    }
            }
        }

        temp = 0;
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










        return 0;


}
