package Main;

import Preprocesamiento.preprocesamiento;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;


public class Main {
    //Map del TF -> HasMap(Término, número de veces que aparece ese término en el documento)
    static HashMap<String, Double> FrecuenciaTermino;

    //Map del IDF -> HashMap(Termino, HashMap<IDF, HashMap(DocID,peso)> )
    static HashMap<String, StructIndiceInvertido> IndiceInvertido = new HashMap<String, StructIndiceInvertido>();

    //Map de la longitud del documento -> HashMap(Documento, Peso)
    static HashMap<String, Double> LongitudDocumento = new HashMap<String, Double>();

    public static void main(String[] args) throws Exception {
        //Solicitamos el directorio donde se encuentran los archivos
        System.out.print("Introduzca el directorio donde se encuentra los archivos -> ");
        Scanner out = new Scanner(System.in);
        String directorio = out.nextLine();
        directorio.replace("\\", "\\\\");

        //Creamos un objeto File con el directorio pasado por teclado
        File carpeta = new File(directorio);
        File[] listaDocumentos = carpeta.listFiles();

        //Creamos un objeto preprocesamiento
        String TextoDocumento;
        ArrayList<String> listaTerminos;
        preprocesamiento preprocesamiento = new preprocesamiento();

        //Recorremos la lista de archivos
        for(int i = 0; i < listaDocumentos.length; i++){
            TextoDocumento = new String(Files.readAllBytes(Paths.get(listaDocumentos[i].getAbsolutePath())));

            //Aplicamos el preprocesamiento al documento y guardamos los términos en una lista
            listaTerminos = preprocesamiento.preprocesar(TextoDocumento);

            //Almacenamos en el map(termino y la frecuencia) respecto a todos los documentos
            FrecuenciaTermino = new HashMap<>();

            //Calculamos el TF
            calcularTF_paso1(listaTerminos);//contamos las veces que aparece cada término en el documento
            calcularTF_paso2(Paths.get(listaDocumentos[i].getAbsolutePath()).getFileName().toString());//calculamos el TF y creamos el IndiceInvertido
        }
        System.out.println("Preprocesamiento finalizado");
        System.out.println("-----------------------------------------------|");

        //Calculamos el IDF
        calcularIDF(listaDocumentos.length);

        //Escribimos los ficheros
        escribirFicheroLongitudPeso();
        escribirFicheroIndiceInvertido();
        System.out.println("-----------------------------------------------|");
        System.out.println("Indexación finalizada");
    }

    /**
     * Cuenta las veces que aparece cada término en el documento
     * Lo almacena en el map FrecuenciaTermino
     * @param listaTerminos lista de términos del documento
     */
    public static void calcularTF_paso1(ArrayList<String> listaTerminos) {
        for(String Termino : listaTerminos) {
            if(FrecuenciaTermino.containsKey(Termino)) {
                double f = FrecuenciaTermino.get(Termino);
                FrecuenciaTermino.put(Termino, f + 1);
            } else {
                FrecuenciaTermino.put(Termino, 1.0);
            }
        }
    }

    /**
     * Calcula el TF y crea el IndiceInvertido
     * Lo almacena en el map IndiceInvertido
     * @param NombreFichero nombre del documento
     */
    public static void calcularTF_paso2(String NombreFichero){
        for (String Termino: FrecuenciaTermino.keySet()) {
            double tf = (double) 1 + Math.log(FrecuenciaTermino.get(Termino)) / Math.log(2);

            //Creamos una dupla DocID-peso en caso de que no exista el término en el índice
            if(!IndiceInvertido.containsKey(Termino)) {
                IndiceInvertido.put(Termino, new StructIndiceInvertido());
            }
            IndiceInvertido.get(Termino).parejaDocIDPeso.put(NombreFichero, tf);
        }
    }

    /**
     * Calcula el IDF de cada término y calcula la longitud del documento
     * Lo almacena en el map IndiceInvertido y en el map LongitudPeso
     * @param numeroDocumentos número de documentos
     */
    public static void calcularIDF (int numeroDocumentos) {
        for (String Termino : IndiceInvertido.keySet()) {
            double idf = (double) numeroDocumentos / IndiceInvertido.get(Termino).parejaDocIDPeso.size();
            IndiceInvertido.get(Termino).asignarIDF(idf);

            //Calculamos la longitud del peso
            for (String Documento : IndiceInvertido.get(Termino).parejaDocIDPeso.keySet()) {
                double peso =IndiceInvertido.get(Termino).parejaDocIDPeso.get(Documento) * IndiceInvertido.get(Termino).obtenerIDF();

                if(LongitudDocumento.containsKey(Documento)) {
                    LongitudDocumento.put(Documento, LongitudDocumento.get(Documento) +  peso);
                }
                else {
                    LongitudDocumento.put(Documento, 0.0);
                }
            }
        }
        for(String Documento : LongitudDocumento.keySet()) {
            LongitudDocumento.put(Documento, Math.sqrt(LongitudDocumento.get(Documento)));
        }
    }

    /**
     * Escribe el fichero LongitudPeso
     */
    public static void escribirFicheroLongitudPeso(){
        System.out.println("Escribiendo fichero LongitudPeso...");
        FileWriter fichero = null;
        PrintWriter pw ;
        try
        {
            fichero = new FileWriter("C:\\Users\\carlo\\Desktop\\REC\\LongDocumentos.txt");
            pw = new PrintWriter(fichero);

            for (String sDoc : LongitudDocumento.keySet()) {
                pw.println(sDoc + " " + LongitudDocumento.get(sDoc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Escribe el fichero IndiceInvertido
     */
    public static void escribirFicheroIndiceInvertido() {
        System.out.println("Escribiendo fichero IndiceInvertido...");
        String path = "C:\\Users\\carlo\\Desktop\\REC\\IndiceInvertido.json";
        Json json = new Json();
        List<structJson> sJson = new ArrayList<>();
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            for (String Termino :IndiceInvertido.keySet()) {
                sJson = new ArrayList<>();
                for (String sDoc : IndiceInvertido.get(Termino).parejaDocIDPeso.keySet()) {
                    sJson.add(new structJson(sDoc, IndiceInvertido.get(Termino).parejaDocIDPeso.get(sDoc)));
                }
                json.lista.add(new AlmacenJson(Termino, IndiceInvertido.get(Termino).obtenerIDF(), sJson));
            }
            String jsonString = gson.toJson(json);
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}