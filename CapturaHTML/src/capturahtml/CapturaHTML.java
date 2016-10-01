package capturahtml;

import dao.EstilomusicalJpaController;
import java.io.File;
import java.io.FileNotFoundException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Estilomusical;

public class CapturaHTML {

    public static void main(String[] args) throws FileNotFoundException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("CapturaHTMLPU");
        EstiloHTML estilo = new EstiloHTML();
        Estilomusical estiloentidade = new Estilomusical();
        EstilomusicalJpaController estilodao = new EstilomusicalJpaController(factory);
        
        File file = new File("htmls/Estilos.txt");
        
        estilo.ler(file, estiloentidade, estilodao);
    }
    
}
