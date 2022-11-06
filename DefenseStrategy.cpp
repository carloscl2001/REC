// ###### Config options ################

//#define PRINT_DEFENSE_STRATEGY 1    // generate map images

// #######################################

#define BUILDING_DEF_STRATEGY_LIB 1

#include "../simulador/Asedio.h"
#include "../simulador/Defense.h"
#include <math.h>

#ifdef PRINT_DEFENSE_STRATEGY
#include "ppm.h"
#endif

#ifdef CUSTOM_RAND_GENERATOR
RAND_TYPE SimpleRandomGenerator::a;
#endif

using namespace Asedio;
using namespace std;

/*
    +============================================================+
    ||                                                          ||
    ||                  STRUCT CELDA                            ||
    ||                                                          ||
    +============================================================+
*/

struct Celda
{
    public:
        double valor;
        int x;
        int y;
        Vector3 posicion;

        Celda(double v,int i,int j, float cellWidth,  float cellHeight):valor(v),x(i),y(j){
            //Coordenadas del Vector3
            float x_ = 0;
            float y_ = 0;
            float z_ = 0;

            x_ = (j * cellWidth) + cellWidth * 0.5f;
            y_ = (i * cellHeight) + cellHeight* 0.5f;
            z_ = 0;
            posicion = Vector3(x_,y_,z_);
        }
};

/*
    +============================================================+
    ||                                                          ||
    ||                   CELLVALUE D0                           ||
    ||                                                          ||
    +============================================================+
*/

//Funcion que asigna  valores a una celda sin haber colocado la defensa 0
void cellValue_d0(int nCellsWidth, int nCellsHeight, float cellHeight, float cellWidth, int mapwidth, int mapheight, std::list<Celda*>& celdas, list<Object*> obstacles) {

    
    float centro_x = 0;
    float centro_y = 0;

    //Posiciones de la casilla central del mapa
    centro_x = (nCellsHeight/2 * cellWidth) + (cellWidth * 0.5f);
    centro_y = (nCellsWidth/2 * cellHeight) + (cellHeight* 0.5f);


    list<Celda*>::iterator it = celdas.begin();
	while(it != celdas.end())
    {

        //Categoria de poscionamineto respecto al centro
        (*it)->valor += (mapwidth - (abs(centro_x - (*it)->posicion.x) + abs((centro_y - (*it)->posicion.y))));
        
        
        
        //Categoria de posicionamiento respecto a obstaculos 
        list<Object*>::iterator itobst = obstacles.begin();
        while (itobst != obstacles.end())
        {
            float distancia = 0;
            distancia =_distance((*it)->posicion,(*itobst)->position);
            (*it)->valor +=  mapwidth - distancia;         
            itobst++;
            
        }
        it++;
    }
}



/*
    +============================================================+
    ||                                                          ||
    ||                   CELLVALUE DX                           ||
    ||                                                          ||
    +============================================================+
*/

//Funcion que asigna  valores a una celda sin haber colocado la defensa 0
void cellValue_dX(int mapwidth, std::list<Celda*>& celdas,  Defense* defensa0) {
    
    
    list<Celda*>::iterator itDX = celdas.begin();
    //Asignamos un valor a cada celda
	while(itDX != celdas.end())
    {
        (*itDX)->valor += (mapwidth - (abs((defensa0)->position.x - (*itDX)->posicion.x) + abs((defensa0)->position.y - (*itDX)->posicion.y)));
        itDX++;            
    }
}


/*
    +============================================================+
    ||                                                          ||
    ||                  FACTIBLE                                ||
    ||                                                          ||
    +============================================================+
*/

bool factible(Celda* posiblePosicion, Defense* posibleDefensa, std::list<Object*> obstacles, std::list<Defense*> defenses, int nCellsWidth, int nCellsHeight, float mapWidth, float mapHeight){
    

    bool esfactible = true;

    
    
    
    if((posiblePosicion->posicion.x + posibleDefensa->radio > mapWidth ) || (posiblePosicion->posicion.x - posibleDefensa->radio <= 0) ||
     (posiblePosicion->posicion.y + posibleDefensa->radio > mapHeight) || (posiblePosicion->posicion.y - posibleDefensa->radio <= 0)){

        esfactible = false;
    }
    
    
    //Comprueba que no choca con ningun obstaculo, calculando la distancia entre dos objetos
    //Si choca con obstaculo -> no es factible
    //Si no choca -> pasa al siguiente obstaculo
    std::list<Object*>::iterator currentObstacle = obstacles.begin();
    while(currentObstacle != obstacles.end()){ //&& esfactible()){
        if(
            (_distance(posiblePosicion->posicion,(*currentObstacle)->position) 
            < 
            ((*currentObstacle)->radio)+posibleDefensa->radio
            )
        )
        {
            esfactible = false;
        }
        ++currentObstacle;
    }
    
    

    
    //Comprueba que no choca con otra defensa, calculando la distancia entre defensas
    //Si es la defensaposible es la 0 no entraría
    //Si choca con otra defensa -> no es factible
    //Si no choca -> pasa al siguiente obstaculo
    
    std::list<Defense*>::iterator currentDefense = defenses.begin();
    if(posibleDefensa-> id != 0){
        while(currentDefense != defenses.end()){ //&& esfactible()){
            if(
                (_distance(posiblePosicion->posicion,(*currentDefense)->position)
                < 
                ((*currentDefense)->radio)+posibleDefensa->radio)
            )
            {
                esfactible = false;
            }
            ++currentDefense;
        }
    }
    
    
    
    
    return esfactible;
    
}

/*
    +============================================================+
    ||                                                          ||
    ||                  PLACE DEFENSES                          ||
    ||                                                          ||
    +============================================================+
*/


void DEF_LIB_EXPORTED placeDefenses(bool** freeCells, int nCellsWidth, int nCellsHeight, float mapWidth, float mapHeight, std::list<Object*> obstacles, std::list<Defense*> defenses)
{
    //Varibles : altura y ancho de las celdas
    float cellWidth = mapWidth / nCellsWidth;
    float cellHeight = mapHeight / nCellsHeight; 


    //Creamos una lista de celdas, de tamaño con todas las celdas
    std::list<Celda*> celdas;

    //Rellenamos la lista celdas con objetos Celda,con valor por defecto 1
    for(int i = 0; i < nCellsHeight; i++) {
       for(int j = 0; j < nCellsWidth; j++) {
            celdas.push_back(new Celda(0,i,j,cellWidth, cellHeight)); 
        }
    }

    //Asiganos valores a las celdas para colocar la defensa 0
    cellValue_d0(nCellsWidth, nCellsHeight, cellHeight, cellHeight, mapWidth , mapHeight, celdas, obstacles);

    //Ordenamos la lista
    celdas.sort([](Celda* c1, Celda* c2) -> bool{return (c1->valor > c2->valor);});
    
    
 

    
    //Iterador para recorrer la lista de las defensas a colocar
    std::list<Defense*>::iterator currentDefense = defenses.begin();


  


    //Para la primera defensa
    bool D0_colocada = false;
    list<Celda*>::iterator posibleCeldaD0 = celdas.begin();
    while((posibleCeldaD0 != celdas.end()) && (D0_colocada == false))
    {           
        
        if(factible ((*posibleCeldaD0), (*currentDefense), obstacles, defenses, nCellsWidth, nCellsHeight, mapWidth, mapHeight) == true){

            
            (*currentDefense)->position = (*posibleCeldaD0)->posicion;
            celdas.erase(posibleCeldaD0);
            D0_colocada = true;
            
        }
        else{
            posibleCeldaD0++;
        }
    }


   

    //Asiganos valores a las celdas para colocar el resto de las celdas
    cellValue_dX(mapWidth, celdas, (*currentDefense));


    //Siguientes defensas
    currentDefense++;
    
    celdas.sort([](Celda* c1, Celda* c2) -> bool{return (c1->valor > c2->valor);});

    std::list<Celda*>::iterator it = celdas.begin();

    
    //Para el resto de las defensas
    while((currentDefense != defenses.end()) && (D0_colocada == true)){
        bool h = true;
        list<Celda*>::iterator posibleCeldaDX = celdas.begin();
        while(posibleCeldaDX != celdas.end()){
            

            if(factible ((*posibleCeldaDX), (*currentDefense), obstacles, defenses, nCellsWidth, nCellsHeight, mapWidth, mapHeight) == true){
                (*currentDefense)->position = (*posibleCeldaDX)->posicion;
                celdas.erase(posibleCeldaDX);
                posibleCeldaDX = celdas.end();
                currentDefense++;
                
            }
            else{
                posibleCeldaDX++;
            }
        }
    }
    cout<<"###########################################################################################################################################################################################"<<endl;
    cout<<"\t\t\t\t\t\t\t\t\t\t\tFIN  PROGRAMA"<<endl;
    cout<<"###########################################################################################################################################################################################"<<endl;
}


#ifdef PRINT_DEFENSE_STRATEGY

    float** cellValues = new float* [nCellsHeight]; 
    for(int i = 0; i < nCellsHeight; ++i) {
       cellValues[i] = new float[nCellsWidth];
       for(int j = 0; j < nCellsWidth; ++j) {
           cellValues[i][j] = ((int)(cellValue(i, j))) % 256;
       }
    }
    dPrintMap("strategy.ppm", nCellsHeight, nCellsWidth, cellHeight, cellWidth, freeCells
                         , cellValues, std::list<Defense*>(), true);

    for(int i = 0; i < nCellsHeight ; ++i)
        delete [] cellValues[i];
	delete [] cellValues;
	cellValues = NULL;

#endif