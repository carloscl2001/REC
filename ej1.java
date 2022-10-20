import java.util.regex.Pattern;

import javax.swing.border.MatteBorder;

import java.util.regex.Matcher;

public class ej1{

    private static final String cadena = "27";
 
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


        //Ejercicio 5
        pat = Pattern.compile("[la]");
        mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}


        //Ejercicio 6
        pat = Pattern.compile(".*2(?!6).*");
        mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        */

        //Ejercicio 7
        Pattern pat = Pattern.compile("[a-zA-Z]{5,10}");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}


    }
}