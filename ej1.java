import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ej1{

    private static final String cadena = "AbC11111";
 
    public static void main(String[] args) {
        
        
        //Pattern pat = Pattern.compile("abc.*");
        //Matcher mat = pat.matcher(cadena);

        /* 
        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        */


        /**
         * EJERCICIO 2
         */
        Pattern pat = Pattern.compile("[abc|ABC].*|");
        Matcher mat = pat.matcher(cadena);
        
        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}

        /* 
        pat = Pattern.compile("\\D");
        mat = pat.matcher(cadena);
        
        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        */


    }
}