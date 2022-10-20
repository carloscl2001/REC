import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ej1{

    private static final String cadena = "1bc11111";
 
    public static void main(String[] args) {
        
        /**
         * EJERCICIO 1
         */
        Pattern pat = Pattern.compile("abc.*");
        Matcher mat = pat.matcher(cadena);

        
        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}


        /**
         * EJERCICIO 2
         */
        Pattern pat = Pattern.compile("abc.*|Abc");
        Matcher mat = pat.matcher(cadena);

        
        if(mat.matches()){
            System.out.println("Si");
        }else {System.out.println("No");}


        


    }
}