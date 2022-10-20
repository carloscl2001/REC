import java.util.regex.Pattern;

import javax.swing.border.MatteBorder;

import java.util.regex.Matcher;

public class ej1{

    private static final String cadena = " 23-34567";
 
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


        //Ejercicio 7
        pat = Pattern.compile("[a-zA-Z]{5,10}");
        mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        

        //Ejercicio 8
        Pattern pat = Pattern.compile("www.*\\.*.es");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        

        //Ejercicio9
        pat = Pattern.compile("\\d\\d/\\d\\d/\\d\\d");
        mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        

        //Ejercicio 10
        pat = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        

        //Ejercicio 11
        pat = Pattern.compile("[+]\\d{2}\\ \\d{2}\\ \\d{7}");
        mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        */

        //Ejercicio 12
        //" 23-34567"
        Pattern pat = Pattern.compile("\\s\\d{2}\\-\\d{5}");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}

    }
}