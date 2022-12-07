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
    static HashMap<String, Integer> FrecuenciaTermino;

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

        System.out.println("Preprocesando documentos...");
        //Recorremos la lista de archivos
        for(File documento : listaDocumentos){
            TextoDocumento = new String(Files.readAllBytes(Paths.get(documento.getAbsolutePath())));

            //Aplicamos el preprocesamiento al documento y guardamos los términos en una lista
            listaTerminos = preprocesamiento.preprocesar(TextoDocumento);

            //Creamos un objeto TF
            FrecuenciaTermino = new HashMap<String, Integer>();

            //Almacenamos en el map(termino y la frecuencia) respecto a todos los documentos
            calcularTF_paso1(listaTerminos);
            calcularTF_paso2(Paths.get(documento.getAbsolutePath()).getFileName().toString());
        }

        //Calculamos el IDF
        calcularIDF(listaDocumentos.length);

        //escribirFicheros
        escribirFicheroLongitudPeso();
        escribirFicheroIndiceInvertido();

        System.out.println("Preprocesamiento finalizado");
    }

    public static void calcularTF_paso1(ArrayList<String> listaTerminos) {
        for (String termino : listaTerminos) {
            if (FrecuenciaTermino.containsKey(termino)) {
                FrecuenciaTermino.put(termino, FrecuenciaTermino.get(termino) + 1);
            } else {
                FrecuenciaTermino.put(termino, 1);
            }
        }
    }

    public static void calcularTF_paso2(String nombreDocumento) {
        for (String FT : FrecuenciaTermino.keySet()) {
            double tf = (double) 1 + Math.log(FrecuenciaTermino.get(FT)) / Math.log(2);

            if(!IndiceInvertido.containsKey(FT)) IndiceInvertido.put(FT, new StructIndiceInvertido());

            IndiceInvertido.get(FT).parejaDocIDPeso.put(nombreDocumento, tf);
        }
    }

    public static void calcularIDF(int numDocumentos) {
        for (String Termino : IndiceInvertido.keySet()) {
            double idf = (double) numDocumentos / IndiceInvertido.get(Termino).parejaDocIDPeso.size();
            IndiceInvertido.get(Termino).asignarIDF(idf);

            //Calculamos la LongitudPeso
            for (String sDoc : IndiceInvertido.get(Termino).parejaDocIDPeso.keySet()) {
                double peso = IndiceInvertido.get(Termino).parejaDocIDPeso.get(sDoc) * IndiceInvertido.get(Termino).obtenerIDF();

                if(LongitudPeso.containsKey(sDoc)){
                    LongitudPeso.put(sDoc, LongitudPeso.get(sDoc) + Math.pow(peso, 2));
                }
                else{LongitudPeso.put(sDoc, 0.0);}
            }
        }
    }

    public static void escribirFicheroLongitudPeso(){
        FileWriter fichero = null;
        PrintWriter pw ;
        try
        {
            fichero = new FileWriter("C:\\Users\\carlo\\Desktop\\REC\\longDocumentos.txt");
            pw = new PrintWriter(fichero);

            for (String Documento : LongitudPeso.keySet()) {
                pw.println(Documento + " " + LongitudPeso.get(Documento));
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

    public static void escribirFicheroIndiceInvertido() {
        String path = "C:\\Users\\carlo\\Desktop\\REC\\IndiceInvertido.json";
        Json json = new Json();
        List<structJson> sJson = new ArrayList<structJson>();
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            for (String sTerm :IndiceInvertido.keySet()) {
                sJson = new ArrayList<structJson>();
                for (String sDoc : IndiceInvertido.get(sTerm).parejaDocIDPeso.keySet()) {
                    sJson.add(new structJson(sDoc, IndiceInvertido.get(sTerm).parejaDocIDPeso.get(sDoc)));
                }
                json.lista.add(new AlmacenJson(sTerm, IndiceInvertido.get(sTerm).obtenerIDF(), sJson));
            }
            String jsonString = gson.toJson(json);
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}