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
    public preprocesamiento() {
        anadirFiltro();
    }

    public void anadirFiltro(){
        //Eliminamos los acentos
        gfCaracteres.anadir(new filtro("\\p{Punct}", " "));
        //eliminamos los numeros
        gfCaracteres.anadir(new filtro("[^A-Za-z]", " "));
        gfCaracteres.anadir(new filtro("\\b[0-9]+\\b", " "));
        //eliminamos los "-" que no sean guiones
        gfCaracteres.anadir(new filtro("-+ | -+", " "));
        //eliminamos los espacios duplicados
        gfCaracteres.anadir(new filtro(" +", " "));
        //eliminamos los espacios al principio y al final
        gfCaracteres.anadir(new filtro("^\\s*", ""));
        gfCaracteres.anadir(new filtro("\\s*$", ""));
    }

    //Método para eliminar los términos vacíos de la lista de términos
    public void eliminarTerminosVacios(ArrayList<String> listaTerminosDocumento) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("D:\\REPOSITORIOS\\REC\\Consulta\\src\\Preprocesamiento\\empty.txt"));
        String TerminoVacio;

        while ((TerminoVacio = br.readLine()) != null) {
            listaTerminosDocumento.remove(TerminoVacio);
            while (listaTerminosDocumento.contains(TerminoVacio)) {
                listaTerminosDocumento.remove(TerminoVacio);
            }
        }

    }

    //Método para aplicar el preprocesamiento
    public ArrayList<String> preprocesar(String TextoConsulta) throws Exception {

        //Pasamos a minúsculas
        TextoConsulta = TextoConsulta.toLowerCase(Locale.ENGLISH);

        //Aplicamos los filtros
        gfCaracteres.aplicar(TextoConsulta);



        //Dividimos el texto en términos y los guardamos en una lista
        ArrayList<String> listaTerminosConsulta = new ArrayList<>(Arrays.asList(TextoConsulta.split("[ ||\n]")));

        //Eliminamos los términos vacíos
        eliminarTerminosVacios(listaTerminosConsulta);

        //Devolvemos la lista de términos
        return listaTerminosConsulta;
    }
}
