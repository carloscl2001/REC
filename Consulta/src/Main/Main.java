package Main;

import Preprocesamiento.preprocesamiento;
import com.google.gson.Gson;
import java.util.Comparator;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {
    //Map de la longitud del peso
    static HashMap<String, Double> LongitudPeso = new HashMap<>();

    //Map del IDF
    static HashMap<String, StructDocIdPeso> IndiceInvertido = new HashMap<>();

    //Map de la consulta
    static HashMap<String, Double> docRecuperados = new HashMap<>();

    //Lista de ranking
    static List<Ranking> rankingOrdenado = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        //Creamos un preprocesamiento
        preprocesamiento preprocesamiento = new preprocesamiento();

        //Variable que defina si se quiere seguir otro búsqueda o no
        boolean consulta_finalizada = false;

        //Por cada búsqueda
        do{
            //Solicitamos uan consulta
            System.out.print("Introduzca una consulta -> ");
            Scanner out = new Scanner(System.in);
            String TextoConsulta = out.nextLine();

            //String y ArrayList de términos de la consulta
            ArrayList<String> listaTerminosConsulta = new ArrayList<>();
            listaTerminosConsulta = preprocesamiento.preprocesar(TextoConsulta);

            //Leemos los documentos
            leerFicheroLongitudPeso();
            leerFicheroIndiceInvertido();

            System.out.println("RESULTADO DE LA BÚSQUEDA");
            System.out.println("--------------------------------------------------");

            //Hacemos ranking
            ranking(listaTerminosConsulta);

            //Ordenamos el ranking
            ordenarRanking();


            if(rankingOrdenado.size() < 10) {
                if (rankingOrdenado.size() == 0) {
                    System.out.println("No results found");
                } else {
                    for (int i = 0; i < rankingOrdenado.size(); i++) {
                        System.out.println("Id Documento: " + rankingOrdenado.get(i).docID + " Peso: " + rankingOrdenado.get(i).peso + "\n");
                    }
                }
            }else{
                for (int i = 0; i < 10; i++) {
                    System.out.println("Id Documento: " + rankingOrdenado.get(i).docID + " Peso: " + rankingOrdenado.get(i).peso + "\n");
                }
            }


            System.out.println("Consulta finalizada");

            //Preguntar si se quiere seguir buscando
            System.out.println("Desea realizar otra consulta? (S/N)");
            String opcion = out.nextLine();
            if(opcion.equals("N")){
                consulta_finalizada = true;
            }
        }while(!consulta_finalizada);
    }

    public static void ranking(ArrayList<String> listaTerminosConsulta) throws Exception{
        for (String termino : listaTerminosConsulta) {
            if(IndiceInvertido.containsKey(termino)){
                for(String doc : IndiceInvertido.get(termino).parejaDocIDPeso.keySet()){
                    double peso = IndiceInvertido.get(termino).parejaDocIDPeso.get(doc) * IndiceInvertido.get(termino).obtenerIDF();
                    if(docRecuperados.containsKey(doc)){
                        docRecuperados.put(doc, docRecuperados.get(doc) + peso);
                    }else{
                        docRecuperados.put(doc, peso);
                    }
                }
            }
        }
        for(String doc : docRecuperados.keySet()) {
            //System.out.println(" Peso: " + docId.get(sDocId) + "" + longDocumento.get(sDocId));
            docRecuperados.put(doc, docRecuperados.get(doc) / LongitudPeso.get(doc));
        }
    }

    public static void ordenarRanking(){
        List<String> aKeys = new ArrayList<>(docRecuperados.keySet());
        List<Double> aValues = new ArrayList<>(docRecuperados.values());

        for (int i = 0; i < aKeys.size(); i++) {
            rankingOrdenado.add(new Ranking(aKeys.get(i), aValues.get(i)));
        }


        Collections.sort(rankingOrdenado, Comparator.comparing(Ranking::obtenerPeso));
    }

    //Función para leer el fichero y almacenarlo en el Map
    public static void leerFicheroLongitudPeso() {
        File archivo;
        FileReader fr = null;
        BufferedReader br;

        try {
            archivo = new File ("..\\longDocumentos.txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
            while((linea=br.readLine())!=null) {
                LongitudPeso.put(linea.split(" ")[0], Double.parseDouble(linea.split(" ")[1]));
            }
        }
        catch(Exception e){e.printStackTrace();}
        finally{
            try{if( null != fr ){fr.close();}
            }catch (Exception e2){e2.printStackTrace();}
        }
    }

    public static void leerFicheroIndiceInvertido() throws Exception{
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\carlo\\Desktop\\REC\\IndiceInvertido.json"));
        Json json = gson.fromJson(br, Json.class);
        for(AlmacenJson almacenJson : json.lista){
            IndiceInvertido.put(almacenJson.Termino, new StructDocIdPeso());
            IndiceInvertido.get(almacenJson.Termino).asignarIDF(almacenJson.IDF);
            for(structJson structJson : almacenJson.almacen) {
                //System.out.println(containerDocId.iPeso);
                IndiceInvertido.get(almacenJson.Termino).parejaDocIDPeso.put(structJson.docID, structJson.peso);
            }
        }
    }

}