package Main;


/**
 * Clase para almacenar el resultado de la consulta
 */
public class Ranking {
    //Atributos
    public String docID;
    public double peso;

    //Constructor
    public Ranking(String docID, double peso){
        this.docID = docID;
        this.peso = peso;
    }

    //Getters y setters
    public String getDocID() {
        return docID;
    }

    public double obtenerPeso() {
        return peso;
    }

}
