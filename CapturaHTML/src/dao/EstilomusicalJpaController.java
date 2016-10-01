package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Artista;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Estilomusical;

public class EstilomusicalJpaController implements Serializable {

    public EstilomusicalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estilomusical estilomusical) {
        if (estilomusical.getArtistaList() == null) {
            estilomusical.setArtistaList(new ArrayList<Artista>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Artista> attachedArtistaList = new ArrayList<Artista>();
            for (Artista artistaListArtistaToAttach : estilomusical.getArtistaList()) {
                artistaListArtistaToAttach = em.getReference(artistaListArtistaToAttach.getClass(), artistaListArtistaToAttach.getIdArtista());
                attachedArtistaList.add(artistaListArtistaToAttach);
            }
            estilomusical.setArtistaList(attachedArtistaList);
            em.persist(estilomusical);
            for (Artista artistaListArtista : estilomusical.getArtistaList()) {
                artistaListArtista.getEstilomusicalList().add(estilomusical);
                artistaListArtista = em.merge(artistaListArtista);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estilomusical estilomusical) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estilomusical persistentEstilomusical = em.find(Estilomusical.class, estilomusical.getIdEstiloMusical());
            List<Artista> artistaListOld = persistentEstilomusical.getArtistaList();
            List<Artista> artistaListNew = estilomusical.getArtistaList();
            List<Artista> attachedArtistaListNew = new ArrayList<Artista>();
            for (Artista artistaListNewArtistaToAttach : artistaListNew) {
                artistaListNewArtistaToAttach = em.getReference(artistaListNewArtistaToAttach.getClass(), artistaListNewArtistaToAttach.getIdArtista());
                attachedArtistaListNew.add(artistaListNewArtistaToAttach);
            }
            artistaListNew = attachedArtistaListNew;
            estilomusical.setArtistaList(artistaListNew);
            estilomusical = em.merge(estilomusical);
            for (Artista artistaListOldArtista : artistaListOld) {
                if (!artistaListNew.contains(artistaListOldArtista)) {
                    artistaListOldArtista.getEstilomusicalList().remove(estilomusical);
                    artistaListOldArtista = em.merge(artistaListOldArtista);
                }
            }
            for (Artista artistaListNewArtista : artistaListNew) {
                if (!artistaListOld.contains(artistaListNewArtista)) {
                    artistaListNewArtista.getEstilomusicalList().add(estilomusical);
                    artistaListNewArtista = em.merge(artistaListNewArtista);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estilomusical.getIdEstiloMusical();
                if (findEstilomusical(id) == null) {
                    throw new NonexistentEntityException("The estilomusical with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estilomusical estilomusical;
            try {
                estilomusical = em.getReference(Estilomusical.class, id);
                estilomusical.getIdEstiloMusical();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estilomusical with id " + id + " no longer exists.", enfe);
            }
            List<Artista> artistaList = estilomusical.getArtistaList();
            for (Artista artistaListArtista : artistaList) {
                artistaListArtista.getEstilomusicalList().remove(estilomusical);
                artistaListArtista = em.merge(artistaListArtista);
            }
            em.remove(estilomusical);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estilomusical> findEstilomusicalEntities() {
        return findEstilomusicalEntities(true, -1, -1);
    }

    public List<Estilomusical> findEstilomusicalEntities(int maxResults, int firstResult) {
        return findEstilomusicalEntities(false, maxResults, firstResult);
    }

    private List<Estilomusical> findEstilomusicalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estilomusical.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Estilomusical findEstilomusical(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estilomusical.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstilomusicalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estilomusical> rt = cq.from(Estilomusical.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
