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
            System.out.print("| Introduzca una consulta | -> ");
            Scanner out = new Scanner(System.in);
            String TextoConsulta = out.nextLine();


            //String y ArrayList de términos de la consulta
            ArrayList<String> listaTerminosConsulta = new ArrayList<>();
            listaTerminosConsulta = preprocesamiento.preprocesar(TextoConsulta);

            //Leemos los documentos
            leerFicheroLongitudPeso();
            leerFicheroIndiceInvertido();

            System.out.println("\n================================================================");
            System.out.println("\t\t\tRESULTADO DE LA BÚSQUEDA");
            System.out.println("================================================================");

            //Hacemos ranking
            ranking(listaTerminosConsulta);

            //Ordenamos el ranking
            ordenarRanking();

            //Mostramos el ranking
            if (rankingOrdenado.size() == 0) {
                System.out.println("No se han encontrado resultados");
            } else {
                for (int i = 0; i < 20; i++) {
                    System.out.println("Titulo: " + obtenerTitulo(rankingOrdenado.get(i).docID));
                    System.out.println("Id del documento: " + rankingOrdenado.get(i).docID + "\nPeso: " + rankingOrdenado.get(i).peso + "\n");
                }
            }

            //Preguntar si se quiere seguir buscando
            System.out.println("----------------------------------------------------------------");
            System.out.print("Desea realizar otra consulta? (S/N) ==> ");
            String opcion = out.nextLine();
            System.out.println("----------------------------------------------------------------");
            if(opcion.equals("N")){
                consulta_finalizada = true;
            }
        }while(!consulta_finalizada);
    }

    public static void ranking(ArrayList<String> listaTerminosConsulta) throws Exception{
        for (String sTermino : listaTerminosConsulta) {
            if(IndiceInvertido.containsKey(sTermino)) {
                for(String sDocIdpeso : IndiceInvertido.get(sTermino).parejaDocIDPeso.keySet()) {
                    //calculamos el peso
                    double dPeso = IndiceInvertido.get(sTermino).parejaDocIDPeso.get(sDocIdpeso) * IndiceInvertido.get(sTermino).obtenerIDF();
                    //System.out.println("Peso: " + indiceInvertido.get(sTermino).docId.get(sDocIdpeso));
                    //añadimos el peso al docId
                    if(docRecuperados.containsKey(sDocIdpeso)) docRecuperados.put(sDocIdpeso, docRecuperados.get(sDocIdpeso) + dPeso);
                    else docRecuperados.put(sDocIdpeso, dPeso);
                }

            }
        }
        System.out.println(docRecuperados);
        for(String sDocId : docRecuperados.keySet()) {
            System.out.println(" Peso: " + docRecuperados.get(sDocId) + " Long: " + LongitudPeso.get(sDocId));
            //System.out.println("hola");
            docRecuperados.put(sDocId, docRecuperados.get(sDocId) / LongitudPeso.get(sDocId));
            System.out.println(" PesoTRAS DIVIDIR: " + docRecuperados.get(sDocId) + " Long TRAS DIVIDIr: " + LongitudPeso.get(sDocId));
        }
    }

    public static void ordenarRanking(){
        List<String> listaClave = new ArrayList<>(docRecuperados.keySet());
        List<Double> listaValores = new ArrayList<>(docRecuperados.values());

        for (int i = 0; i < listaClave.size(); i++) {
            rankingOrdenado.add(new Ranking(listaClave.get(i), listaValores.get(i)));
        }

        Collections.sort(rankingOrdenado, Comparator.comparing(Ranking::obtenerPeso));
        Collections.reverse(rankingOrdenado);
    }

    public static String obtenerTitulo(String documento) throws Exception{
        File archivo;
        FileReader fr = null;
        BufferedReader br;

        archivo = new File ("C:\\Users\\carlo\\Desktop\\REC\\corpus\\" + documento);
        fr = new FileReader (archivo);
        br = new BufferedReader(fr);

        return br.readLine();
    }

    //Función para leer el fichero y almacenarlo en el Map
    public static void leerFicheroLongitudPeso() throws Exception{
        String filePath = "C:\\Users\\carlo\\Desktop\\REC\\longDocumentos.txt";

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(" ", 2);
            if (parts.length >= 2)
            {
                LongitudPeso.put(parts[0], Double.parseDouble(parts[1]));
            }
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
                IndiceInvertido.get(almacenJson.Termino).parejaDocIDPeso.put(structJson.docID, structJson.peso);
            }
        }
    }
}