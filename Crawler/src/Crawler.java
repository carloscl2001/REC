import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

public class Crawler {

    //Cola concurrente de páginas a visitar
    public static ConcurrentLinkedQueue<String> colaURL = new ConcurrentLinkedQueue<String>();

    //Lista concurrente de paginas ya visitadas
    public static List<String> listaURL = Collections.synchronizedList(new ArrayList<String>());

    //Dominio que vamos a utilizar
    public static final String URL_dominio = "https://es.wikipedia.org";

    //Expresion regular a utilizar
    public  static final String URL_regex = "href=\"(/wiki/[^:()]*?)\"";

    //Numero dee intentos maximos
    public static int n_intentos_max = 20000;

    public static class worker implements Runnable {
        public void run() {
            while(listaURL.size() < n_intentos_max) {
                String URL_base = colaURL.poll();
                if(URL_base != null) {
                    try {
                        procesar_URL(URL_base);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }


    public static void procesar_URL(String URL_base){
        String URL_actual = URL_dominio + URL_base;
        String informacion_URL_actual = "";
        try{
            informacion_URL_actual = new String(new URL(URL_actual).openStream().readAllBytes());
            listaURL.add(URL_actual);

            if(informacion_URL_actual.equals("") ){
                System.out.println("No se pudo acceder a esa página");
            }

            PrintWriter writer = new PrintWriter("archivos/" + URL_actual.substring(5).replaceAll("[<>:\"/\\|?*]", "") + ".html");
            writer.write(informacion_URL_actual);
            writer.close();

            System.out.println("Procesada la URL -> " + URL_actual);

        }catch(Exception e) { System.out.println("Fallo al encontrar la URL ->" + URL_actual); }

        Pattern pat = Pattern.compile(URL_regex);
        Matcher mat = pat.matcher(informacion_URL_actual);
        while (mat.find()){
            String URL_siguiente = mat.group(1);

            if(!listaURL.contains(URL_siguiente) && !(colaURL.contains(URL_siguiente))){
                colaURL.add(URL_siguiente);
            }
        }
    }

    public static void main(String[] args) throws Exception{

        System.out.println("Iniciando crawler con semilla -> Porsche || con " + n_intentos_max + " intentos maximos");

        //Añadimos la semilla a la cola
        String URL_semilla = "/wiki/Porsche_911";
        colaURL.add(URL_semilla);

        //Fijamos el número de hilos a realizar
        int n_hilos = Runtime.getRuntime().availableProcessors();

        //Creamos el pool de Threads y lo ejecutamos
        ThreadPoolExecutor executor = new ThreadPoolExecutor(n_hilos, n_hilos, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < n_hilos; n_hilos++) {
            executor.execute(new worker());
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}

    }
}