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
    //Map de la longitud del documento -> HashMap(Documento, Peso)
    static HashMap<String, Double> LongitudDocumento = new HashMap<>();

    //Map del IDF -> HashMap(Termino, HashMap<IDF, HashMap(DocID,peso)> )
    static HashMap<String, StructDocIdPeso> IndiceInvertido = new HashMap<>();

    //Map de la consulta -> HashMap(DocID, Peso)
    static HashMap<String, Double> docRecuperados = new HashMap<>();


    public static void main(String[] args) throws Exception {
        //Creamos un preprocesamiento
        preprocesamiento preprocesamiento = new preprocesamiento();

        //Variable que defina si se quiere seguir otro búsqueda o no
        boolean consulta_finalizada = false;

        //Por cada búsqueda
        do{
            docRecuperados = new HashMap<>();
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
            List<Ranking> rankingOrdenado = new ArrayList<>();
            ordenarRanking(rankingOrdenado);

            //Mostramos el ranking
            if (rankingOrdenado.size() == 0) {
                System.out.println("No se han encontrado resultados");
            } else {
                for (int i = 0; i < 10; i++) {
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

    /**
     * Método que genera el ranking de los documentos recuperados y lo almacena en el HashMap docRecuperados
     * @throws Exception
     * @param listaTerminosConsulta ArrayList de términos de la consulta
     */
    public static void ranking(ArrayList<String> listaTerminosConsulta) throws Exception{
        for (String sTermino : listaTerminosConsulta) {
            if(IndiceInvertido.containsKey(sTermino)) {
                for(String DocIdpeso : IndiceInvertido.get(sTermino).parejaDocIDPeso.keySet()) {
                    //Calculamos el peso
                    double dPeso = IndiceInvertido.get(sTermino).parejaDocIDPeso.get(DocIdpeso) * IndiceInvertido.get(sTermino).obtenerIDF() * IndiceInvertido.get(sTermino).obtenerIDF();
                    //Añadimos el peso al docId
                    if(docRecuperados.containsKey(DocIdpeso)) docRecuperados.put(DocIdpeso, docRecuperados.get(DocIdpeso) + dPeso);
                    else docRecuperados.put(DocIdpeso, dPeso);
                }
            }
        }
        for(String sDocId : docRecuperados.keySet()) {
            docRecuperados.put(sDocId, docRecuperados.get(sDocId) / LongitudDocumento.get(sDocId));
        }
    }

    /**
     * Función que ordena el ranking
     * @param rankingOrdenado Lista de ranking
     */
    public static void ordenarRanking(List<Ranking>rankingOrdenado) {
        List<String> listaClave = new ArrayList<>(docRecuperados.keySet());
        List<Double> listaValores = new ArrayList<>(docRecuperados.values());

        for (int i = 0; i < listaClave.size(); i++) {
            rankingOrdenado.add(new Ranking(listaClave.get(i), listaValores.get(i)));
        }

        Collections.sort(rankingOrdenado, Comparator.comparing(Ranking::obtenerPeso));
        Collections.reverse(rankingOrdenado);
    }

    /**
     * Función que devuelve el título de un documento
     * @param documento documento a devolver el título
     * @throws Exception
     */
    public static String obtenerTitulo(String documento) throws Exception{
        File archivo;
        FileReader fr = null;
        BufferedReader br;

        archivo = new File ("C:\\Users\\carlo\\Desktop\\REC\\corpus\\" + documento);
        fr = new FileReader (archivo);
        br = new BufferedReader(fr);

        return br.readLine();
    }

    /**
     * Método que lee el fichero de LongitudDocumento y peso
     * @throws Exception
     */
    public static void leerFicheroLongitudPeso() throws Exception{
        String filePath = "C:\\Users\\carlo\\Desktop\\REC\\longDocumentos.txt";

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(" ", 2);
            if (parts.length >= 2)
            {
                LongitudDocumento.put(parts[0], Double.parseDouble(parts[1]));
            }
        }
    }

    /**
     * Función para leer el fichero de IndiceInvertido y almacenarlo en el Map
     * @throws Exception
     */
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