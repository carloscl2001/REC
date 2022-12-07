package Main;

import java.util.HashMap;

/**
 * Clase para la estructura que nos permite almacenar un índice invertido
 */
public class StructDocIdPeso{

    //Dupla DocID-peso
    public HashMap<String, Double> parejaDocIDPeso = new HashMap<String, Double>();

    //IDF
    private double IDF = 0.0;

    public StructDocIdPeso(){}
    public double obtenerIDF() {
        return IDF;
    }

    public void asignarIDF(double IDF) {
        this.IDF = IDF;
    }

}
