import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.*;




public class ej2 {
    //Ejericio 15
    public static void ej15(){
        String cadena = "<a>uno</a><b>dos</b><c>tres</c><d>cuatro</d><e>cinco</e>";

        System.out.println("Patron: <[^>]*>([^<]*)</[^>]*>");
        
        Pattern pat = Pattern.compile("<[^>]*>([^<]*)</[^>]*>");
        Matcher mat = pat.matcher(cadena);
        while (mat.find()){System.out.println(mat.group());}
            
        System.out.println("Como podemos apreciar no lo hace correctamente");
        
     
        System.out.println("-----------------------------------------------");

        System.out.println("Patron corregido: (?<=>)[^<>]*(?=<)");
        pat = Pattern.compile("(?<=>)[^<>]*(?=<)");
        mat = pat.matcher(cadena);
        while (mat.find())
            System.out.println(mat.group());

        System.out.println("-----------------------------------------------");

        System.out.println("Patron: <.*>(.*)<\\/.*>");
        pat = Pattern.compile("<.*>(.*)<\\/.*>");
        mat = pat.matcher(cadena);
        while (mat.find())
            System.out.println(mat.group());

        System.out.println("-----------------------------------------------");
        System.out.println("Patron: <.*?>(.*?)<\\/.*?>");
        pat = Pattern.compile("<.*?>(.*?)<\\/.*?>");
        mat = pat.matcher(cadena);
        while (mat.find()){System.out.println(mat.group());}
    }
    //Ejercicio 16
    public static void ej16(String file){
        
        String res = "";

        try {
            Path path = Paths.get(file);
            String texto = Files.readString(path);

            Pattern pat = Pattern.compile("[\\(:,\\.;\\?¿¡!\"'\\<>\\)]");
            Matcher mat = pat.matcher(texto);
            res = mat.replaceAll("");
        }catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(res);

    }

    //Ejercicio17
    public static String ej17(String file){


        String res = "";

        try {
            Path path = Paths.get(file);
            String texto = Files.readString(path);

            Pattern pat = Pattern.compile("\u00E1");
            Matcher mat = pat.matcher(texto);
            res = mat.replaceAll("a");

            pat = Pattern.compile("é");
            mat = pat.matcher(texto);
            res = mat.replaceAll("e");

            pat = Pattern.compile("í");
            mat = pat.matcher(texto);
            res = mat.replaceAll("i");


            pat = Pattern.compile("ó");
            mat = pat.matcher(texto);
            res = mat.replaceAll("o");

            pat = Pattern.compile("ú");
            mat = pat.matcher(texto);
            res = mat.replaceAll("u");


        }catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(res);
        return res;
    }
    
    
    //Ejercicio 18
    public static String ej18(String EjercicioExpresiones) {
        Pattern pat = Pattern.compile("\\b\\d+\\b");
        Matcher mat = pat.matcher(EjercicioExpresiones);
        String res = mat.replaceAll(" ");

        System.out.print(res);
        return res;
    }

    //Ejercicio 19
    public static String ej19(String EjercicioExpresiones) {
        String res = EjercicioExpresiones.toUpperCase();

        System.out.print(res);
        return res;
    }

    //Ejercicio 20
    public static String ej20(String EjercicioExpresiones) {
        Pattern pat = Pattern.compile(" {2}");
        Matcher mat = pat.matcher(EjercicioExpresiones);
        String res = mat.replaceAll(" ");

        System.out.print(res);
        return res;
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Elige un ejericio -> ");
        int ej = s.nextInt();
        s.close();
        switch (ej) {
            case 15:  ej15();  
                break;

            case 16: ej16("EjercicioExpresiones.txt"); 
                break;

            case 17: ej17("EjercicioExpresiones.txt"); 
                break;

            case 18: ej18("EjercicioExpresiones.txt");
            break; 
            
            case 19: ej19("EjercicioExpresiones.txt"); 
            break;

            case 20:  ej20("EjercicioExpresiones.txt"); 
            break;


        
            default:
                break;
        }

        
        
    }
    
}

