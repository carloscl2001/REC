import java.util.regex.Pattern;

import javax.swing.border.MatteBorder;

import java.util.regex.Matcher;

public class ej1{

    private static final String cadena = "7118";
 
    public static void main(String[] args) {
        
        /*
        // EJERCICO 1
        Pattern pat = Pattern.compile("abc.*");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        


        //Ejercicio 2
        pat = Pattern.compile("^(ABC|abc).*");
        mat = pat.matcher(cadena);
        
        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}



        //Ejercio 3
        pat = Pattern.compile("\\D.*");
        mat = pat.matcher(cadena);
        
        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}

        
        //Ejercicio 4
        pat = Pattern.compile(".*\\D");
        mat = pat.matcher(cadena);
        
        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        */

        Pattern pat = Pattern.compile(cadena);
        Matcher mat = pat.matcher(cadena);
        


    }
}