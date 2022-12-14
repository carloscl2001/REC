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
    //Map del TF
    static HashMap<String, Double> FrecuenciaTermino;

    //Map del IDF
    static HashMap<String, StructIndiceInvertido> IndiceInvertido = new HashMap<String, StructIndiceInvertido>();

    //Map de la longitud del peso
    static HashMap<String, Double> LongitudPeso = new HashMap<String, Double>();

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

            //Creamos un objeto TF
            FrecuenciaTermino = new HashMap<String, Double>();

            //Almacenamos en el map(termino y la frecuencia) respecto a todos los documentos
            //Calculamos IDF
            calcularTF_paso1(listaTerminos);
            calcularTF_paso2(Paths.get(listaDocumentos[i].getAbsolutePath()).getFileName().toString());
        }
        System.out.println("Preprocesamiento finalizado");
        System.out.println("-----------------------------------------------|");

        //Calculamos el IDF
        calcularIDF(listaDocumentos.length);

        //escribirFicheros
        escribirFicheroLongitudPeso();
        escribirFicheroIndiceInvertido();
        System.out.println("-----------------------------------------------|");
        System.out.println("Indezación finalizada");
    }

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

    public static void calcularTF_paso2(String NombreFichero) throws Exception{
        for (String Termino: FrecuenciaTermino.keySet()) {
            double tf = (double) 1 + Math.log(FrecuenciaTermino.get(Termino)) / Math.log(2);

            if(!IndiceInvertido.containsKey(Termino)) {
                IndiceInvertido.put(Termino, new StructIndiceInvertido());
            }
            IndiceInvertido.get(Termino).parejaDocIDPeso.put(NombreFichero, tf);

        }
    }

    public static void calcularIDF (int numeroDocumentos) {
        for (String Termino : IndiceInvertido.keySet()) {
            double idf = (double) numeroDocumentos / IndiceInvertido.get(Termino).parejaDocIDPeso.size();
            IndiceInvertido.get(Termino).asignarIDF(idf);

            for (String Documento : IndiceInvertido.get(Termino).parejaDocIDPeso.keySet()) {
                double peso =IndiceInvertido.get(Termino).parejaDocIDPeso.get(Documento) * IndiceInvertido.get(Termino).obtenerIDF();

                if(LongitudPeso.containsKey(Documento)) {
                    LongitudPeso.put(Documento, LongitudPeso.get(Documento) +  peso);
                }
                else {
                    LongitudPeso.put(Documento, 0.0);
                }
            }
        }
        for(String Documento : LongitudPeso.keySet()) {
            LongitudPeso.put(Documento, Math.sqrt(LongitudPeso.get(Documento)));
        }
    }

    public static void escribirFicheroLongitudPeso(){
        System.out.println("Escribiendo fichero LongitudPeso...");
        FileWriter fichero = null;
        PrintWriter pw ;
        try
        {
            fichero = new FileWriter("C:\\Users\\Usuario\\Desktop\\REC\\LongDocumentos.txt");
            pw = new PrintWriter(fichero);

            for (String sDoc : LongitudPeso.keySet()) {
                //imprmir en fichero peso.txt
                pw.println(sDoc + " " + LongitudPeso.get(sDoc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    public static void escribirFicheroIndiceInvertido() {
        System.out.println("Escribiendo fichero IndiceInvertido...");
        String path = "C:\\Users\\Usuario\\Desktop\\REC\\IndiceInvertido.json";
        Json json = new Json();
        List<structJson> sJson = new ArrayList<structJson>();
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            for (String Termino :IndiceInvertido.keySet()) {
                sJson = new ArrayList<structJson>();
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