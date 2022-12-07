package Main;

import java.util.ArrayList;
import java.util.List;

public class AlmacenJson {
    public String Termino;
    public double IDF;

    public List<structJson> almacen = new ArrayList<structJson>();

    public AlmacenJson(String Termino, double IDF, List<structJson> almacen){
        this.Termino = Termino;
        this.IDF = IDF;
        this.almacen = almacen;
    }

}
