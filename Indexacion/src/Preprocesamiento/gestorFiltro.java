package Preprocesamiento;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para la gestion de los filtros
 */
public class gestorFiltro {

    //Atributo que representa la lista de filtros
    List<filtro> listaFiltros = new ArrayList<filtro>();

    //Cosntructor de la clase gestorFiltro
    public gestorFiltro(){}

    //Metodo para añadir un filtro al gestor
    public void anadir(filtro filtro){
        listaFiltros.add(filtro);
    }

    //Metodo para eliminar un filtro del gestor
    public void eliminar(filtro filtro){
        listaFiltros.remove(filtro);
    }

    //Metodo para aplicar un filtro del gestor
    public void aplicar(String sTexto){
        for(int i = 0; i < listaFiltros.size(); i++){
            listaFiltros.get(i).aplicarFiltro(sTexto);
        }
    }




}
