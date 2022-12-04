package Main;

import java.util.HashMap;

/**
 * Clase para la estructura que nos permite almacenar un índice invertido
 */
public class StructIndiceInvertido {

    //Dupla DocID-peso
    public HashMap<String, Double> parejaDocIDPeso = new HashMap<String, Double>();

    //IDF
    private double IDF = 0.0;

    public StructIndiceInvertido(){}

    public StructIndiceInvertido(double IDF){
        this.IDF = IDF;
    }

    public double obtenerIDF(){
        return IDF;
    }

    public void asignarIDF(double IDF) {
        this.IDF = IDF;
    }


}
