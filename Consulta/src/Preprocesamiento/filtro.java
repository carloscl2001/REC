package Preprocesamiento;

/**
 * Clase para la gestion de un filtro
 */
public class filtro {

     //Atributo que representa la expresion regular
    private final String sPatron;

    //Atributo que representa el reeemplazo
    private final String sReemplazo;

    /**
     * Constructor de la clase filtro
     * @param s1 Patron a buscar
     * @param s2 Patron a reemplazar
     */
    public filtro(String s1, String s2) {
        this.sPatron = s1;
        this.sReemplazo = s2;
    }


     //Metodo para aplicar el filtro
     public void aplicarFiltro(String sTexto){
         sTexto = sTexto.replaceAll(sPatron, sReemplazo);
    }
}
