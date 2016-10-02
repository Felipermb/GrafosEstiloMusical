package capturahtml;

import dao.ArtistaJpaController;
import dao.EstilomusicalJpaController;
import dao.exceptions.NonexistentEntityException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Artista;
import modelo.Estilomusical;

public class CapturaHTML {

    public static void main(String[] args) throws FileNotFoundException, NonexistentEntityException, Exception {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("CapturaHTMLPU");
        /* ARMAZENAMENTO DOS ESTILOS MUSICAIS
        //Instância da Classe que fará o tratamento do html
        EstiloHTML estilo = new EstiloHTML();
        //Seta o arquivo .txt com o html
        File file = new File("htmls/Estilos.txt");
        //entidade com jpa
        Estilomusical estiloentidade = new Estilomusical();
        //entidade com ações CRUD
        EstilomusicalJpaController estilodao = new EstilomusicalJpaController(factory);
        //Chamada do método para tratamento
        //estilo.ler(file, estiloentidade, estilodao);
        */

        //ARMAZENAMENTO DOS ARTISTAS
        //Instância da Classe que fará o tratamento do html
        EstiloHTML estilo = new EstiloHTML();
        //Seta o arquivo .txt com o html
        File file = new File("htmls/eletronica.txt");
        //entidade com jpa
        Artista artistaentidade = new Artista();
        //entidade com ações CRUD
        ArtistaJpaController artistadao = new ArtistaJpaController(factory);
        //ArrayList para receber os artistas
        List<Artista> artistaList;
        //Chamada do método para o tratamento
        artistaList = estilo.ler(file, artistaentidade, artistadao);

        //entidade com ações CRUD
        EstilomusicalJpaController estilodao = new EstilomusicalJpaController(factory);
        //Recupere o índice do estilo desejado no banco e altere aqui
        Estilomusical em = estilodao.findEstilomusical(13);
        em.setArtistaList(artistaList);
        estilodao.edit(em);
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("CapturaHTMLPU");
//        EntityManager entityManager = factory.createEntityManager();
//        EstiloHTML estilo = new EstiloHTML();
//        Estilomusical estiloentidade = new Estilomusical();
//        EstilomusicalJpaController estilodao = new EstilomusicalJpaController(factory);
//        ArtistaJpaController artistadao = new ArtistaJpaController(factory);
//        //ArtistaJpaController artistadao = new ArtistaJpaController(factory);
//        Artista x = new Artista();
//        Artista k = new Artista();
//        
////        x.setNome("tereza");
////        k = artistadao.salvar(x);
//        //System.out.println(k.getIdArtista());
//        artistadao.destroy(6);
//        artistadao.destroy(15);
//        artistadao.destroy(20);
    }

}
