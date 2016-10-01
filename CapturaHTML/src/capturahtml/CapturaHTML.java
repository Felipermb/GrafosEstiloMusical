package capturahtml;

import java.io.File;
import java.io.FileNotFoundException;

public class CapturaHTML {

    public static void main(String[] args) throws FileNotFoundException {
        EstiloHTML estilo = new EstiloHTML();
        File file = new File("reggae.txt");
        estilo.ler(file);
    }
    
}
