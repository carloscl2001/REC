package Preprocesamiento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class preprocesamiento {

    //Creamos un gestor de filtros
    private gestorFiltro gfCaracteres = new gestorFiltro();

    //Constructor de la clase preprocesamiento
    public preprocesamiento(){
        anadirFiltro();
    }

    public void anadirFiltro(){
        //Eliminamos los acentos
        gfCaracteres.anadir(new filtro("\\p{Punct}", " "));
        //eliminamos los numeros
        gfCaracteres.anadir(new filtro("[^A-Za-z]", " "));
        //eliminamos los "-" que no sean guiones
        gfCaracteres.anadir(new filtro("-+ | -+", " "));
        //eliminamos los espacios duplicados
        gfCaracteres.anadir(new filtro(" +", " "));
    }

    //Método para eliminar los términos vacíos de la lista de términos
    public void eliminarTerminosVacios(ArrayList<String> listaTerminosDocumento) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("D:\\REPOSITORIOS\\REC\\Indexacion\\src\\Preprocesamiento\\empty.txt"));
        String TerminoVacio;

        while ((TerminoVacio = br.readLine()) != null) {
            listaTerminosDocumento.remove(TerminoVacio);
            while (listaTerminosDocumento.contains(TerminoVacio)){
                listaTerminosDocumento.remove(TerminoVacio);
            }
        }
    }

    //Método para aplicar el preprocesamiento
    public ArrayList<String> preprocesar(String TextoDocumento) throws Exception {

        //Pasamos a minúsculas
        TextoDocumento = TextoDocumento.toLowerCase(Locale.ENGLISH);

        //Aplicamos los filtros
        gfCaracteres.aplicar(TextoDocumento);

        //Dividimos el texto en términos y los guardamos en una lista
        ArrayList<String> listaTerminosDocumento = new ArrayList<>(Arrays.asList(TextoDocumento.split("[ ||\n]")));

        //Eliminamos los términos vacíos
        eliminarTerminosVacios(listaTerminosDocumento);

        //Devolvemos la lista de términos
        return listaTerminosDocumento;
    }
}
