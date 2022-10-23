import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.*;




public class ej2 {
    //Ejericio 15
    public static void ej15()
    {
        String cadena = "<a>uno</a><b>dos</b><c>tres</c><d>cuatro</d><e>cinco</e>";

        System.out.println("EXPRESION DADA: <[^>]*>([^<]*)</[^>]*>");
        Pattern pat = Pattern.compile("<[^>]*>([^<]*)</[^>]*>");
        Matcher mat = pat.matcher(cadena);
        while (mat.find()){System.out.println(mat.group());}
            
        System.out.println("| Como podemos apreciar no lo hace correctamente");     
        System.out.println("-----------------------------------------------");

       
        System.out.println("EXPRESION ARREGLADA: (?<=>)[^<>]*(?=<)");
        pat = Pattern.compile("(?<=>)[^<>]*(?=<)");
        mat = pat.matcher(cadena);
        while (mat.find())
            System.out.println(mat.group());
        
        System.out.println("| Como podemos apreciar lo hace correctamente");
        System.out.println("-----------------------------------------------");

        System.out.println("EXPRESION: <.*>(.*)<\\/.*>");
        pat = Pattern.compile("<.*>(.*)<\\/.*>");
        mat = pat.matcher(cadena);
        while (mat.find())
            System.out.println(mat.group());
        System.out.println("| Como podemos apreciar no lo hace correctamente");
        System.out.println("-----------------------------------------------");


        System.out.println("EXPRESION: <.*?>(.*?)<\\/.*?>");
        pat = Pattern.compile("<.*?>(.*?)<\\/.*?>");
        mat = pat.matcher(cadena);
        while (mat.find()){System.out.println(mat.group());}
        System.out.println("| Como podemos apreciar no lo hace correctamente");
        System.out.println("-----------------------------------------------");

        System.out.println("Entre todos los propuestos el resultado es el mismo, pero no es el deseado.");
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

            pat = Pattern.compile("\\s[0-9]+\\s");
            mat = pat.matcher(res);
            res = mat.replaceAll(" ");
        
            res = res.toUpperCase();

            pat = Pattern.compile(" {2}");
            mat = pat.matcher(res);
            res = mat.replaceAll(" ");

            pat = Pattern.compile("[Á]");
            mat = pat.matcher(res);
            res = mat.replaceAll("A");

            pat = Pattern.compile("[É]");
            mat = pat.matcher(res);
            res = mat.replaceAll("E");

            pat = Pattern.compile("[Í]");
            mat = pat.matcher(res);
            res = mat.replaceAll("I");

            pat = Pattern.compile("[Ó]");
            mat = pat.matcher(res);
            res = mat.replaceAll("O");

            pat = Pattern.compile("[Ú]");
            mat = pat.matcher(res);
            res = mat.replaceAll("U");


        }catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(res);
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

            case 17: ej16("EjercicioExpresiones.txt"); 
                break;

            case 18: ej16("EjercicioExpresiones.txt");
            break; 
            
            case 19: ej16("EjercicioExpresiones.txt"); 
            break;

            case 20:  ej16("EjercicioExpresiones.txt"); 
            break;

            default:
                break;
        }        
    }
}

