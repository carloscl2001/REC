import java.util.regrex.Pattern;
import java.util.regrex.Matcher;

public class ej1{
    public static void main(String[] cadena) {
        
        Pattern pat = Pattern.compile("abc");
        Matcher mat = pat.matcher(cadena);

        if (mat.matches()){
            System.out.println("SI");
           } else {
            System.out.println("NO");
        }
    }
}
