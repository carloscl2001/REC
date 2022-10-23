import java.util.regex.Pattern;

import javax.swing.border.MatteBorder;

import java.util.regex.Matcher;

import java.io.*;

public class ej1{

    //private static final String cadena = "v1agra";
 
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
        

        //Ejercicio 12
        pat = Pattern.compile("(P\\s\\d{2}-\\d{5})|(P-\\d{2}-\\d{4})|(P#\\s\\d{2}\\s\\d{4})|(P#\\s\\d{2}\\s\\d{4})|(P#\\d{2}-\\d{4})|(P\\s\\d{6})");
        mat = pat.matcher(cadena);

        if(mat.matches()){
        System.out.println("Si");
        }else {System.out.println("No");}
       
        
        //Ejercicio 13
        pat = Pattern.compile("[@]");
        mat = pat.matcher(cadena);
        String res = mat.replaceAll("a");

        pat = Pattern.compile("[1!¡]");
        mat = pat.matcher(res);
        res = mat.replaceAll("i");

        if (!res.equals(cadena)){
            System.out.println("Intento de saltarse spam");
        }else{System.out.println("No Spam");}
        

        //Ejercicio 14
        Pattern pat = Pattern.compile("<img.*[\\r\\n]*.*src=\".*\"");
        int cont = 0;
        //Suponemos que la pagina de la uca es un txt
        File file = new File("uca.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        
            for (String line = br.readLine(); line != null;) {
                Matcher mat = pat.matcher(line);
                if (mat.matches()) {
                    System.out.println(mat.group());
                    ++cont;
                }
            }
            System.out.println("Numero de veces en las que aparece una imagen -> " + cont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        

        
    
    }
}