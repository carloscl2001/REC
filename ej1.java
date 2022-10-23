import java.util.regex.Pattern;
import java.util.*;

import javax.swing.border.MatteBorder;

import java.util.regex.Matcher;

import java.io.*;

public class ej1{

    private static String cadena = "www.abc.com";

    public static void ej11(){
        Pattern pat = Pattern.compile("abc.*");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
    }
    
    //Ejercicio 2
    public static void ej2(){
        Pattern pat = Pattern.compile("^(ABC|abc).*");
        Matcher mat = pat.matcher(cadena);
    
        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
    }
    
    

    //Ejercio 3
    public static void ej3(){
        Pattern pat = Pattern.compile("\\D.*");
        Matcher mat = pat.matcher(cadena);
        
        if(mat.matches()){
            System.out.println("No empieza por digito");
        }else {System.out.println("Si empieza por digito");}
    }
    
    
    //Ejercicio 4
    public static void ej4(){
        Pattern pat = Pattern.compile(".*\\D");
        Matcher mat = pat.matcher(cadena);
        
    
        if(mat.matches()){
            System.out.println("No acaba por digito");
        }else {System.out.println("Si acaba por digito");}
    }

    //Ejercicio 5
    public static void ej5(){
        Pattern pat = Pattern.compile("[la]");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si los contiene");
        }else {System.out.println("No los contiene");}
    }

    //Ejercicio 6
    public static void ej6() {
        Pattern pat = Pattern.compile(".*2(?!6).*");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si lo cumple");
        }else {System.out.println("No lo cumple");}
    }

    //Ejercicio 7
    public static void ej7() {
        Pattern pat = Pattern.compile("[a-zA-Z]{5,10}");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
    }

    //Ejercicio 8
    public static void ej8() {
        Pattern pat = Pattern.compile("www.*\\.*.es");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
        
    }
    
    //Ejercicio 9
    private static void ej9() {
        Pattern pat = Pattern.compile("\\d\\d/\\d\\d/\\d\\d");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
    }

    //Ejercicio 10
    private static void ej10() {
        Pattern pat = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
    }

    //Ejercicio 11
    private static void ej111() {
        Pattern pat = Pattern.compile("[+]\\d{2}\\ \\d{2}\\ \\d{7}");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}
    }

    //Ejercicio 12
    private static void ej12() {
        Pattern pat = Pattern.compile("(P\\s\\d{2}-\\d{5})|(P-\\d{2}-\\d{4})|(P#\\s\\d{2}\\s\\d{4})|(P#\\s\\d{2}\\s\\d{4})|(P#\\d{2}-\\d{4})|(P\\s\\d{6})");
        Matcher mat = pat.matcher(cadena);

        if(mat.matches()){
        System.out.println("Si");
        }else {System.out.println("No");}
    }

    //Ejercicio 13
    private static void ej13() {
        Pattern pat = Pattern.compile("[@]");
        Matcher mat = pat.matcher(cadena);
        String res = mat.replaceAll("a");

        pat = Pattern.compile("[1!¡]");
        mat = pat.matcher(res);
        res = mat.replaceAll("i");

        if (!res.equals(cadena)){
            System.out.println("Intento de saltarse spam");
        }else{System.out.println("No Spam");}
    }

    //Ejercicio 14
    private static void ej14(){
        Pattern pat = Pattern.compile("<img.*");
        int cont = 0;
        //Suponemos que la pagina de la uca es un txt

        try{
            File pagina = new File("uca.html");
            Scanner reader = new Scanner(pagina);

            while(reader.hasNextLine()) {
                Matcher mat = pat.matcher(reader.nextLine());
                if (mat.find()) {
                    cont++;
                }
            }
            System.out.println("Numero de veces en las que aparece una imagen -> " + cont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Elige un ejericio -> ");
        int key = s.nextInt();
        s.close();
        switch (key) {
            case 1: ej11();
                break;

            case 2: ej2();
            break;

            case 3: ej3();
            break;

            case 4: ej4();
            break;

            case 5: ej5();
            break;

            case 6: ej6();
            break;

            case 7: ej7();
            break;

            case 8: ej8();
            break;

            case 9: ej9();
            break;

            case 10: ej10();
            break;

            case 11: ej111();
            break;

            case 12: ej12();
            break;

            case 13: ej13();
            break;

            case 14: ej14();
            break;
        
            default:
                break;
        }
    
    }
}