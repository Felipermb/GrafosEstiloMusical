/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Estilomusical;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Artista;

/**
 *
 * @author Kleyson
 */
public class ArtistaJpaController implements Serializable {

    public ArtistaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Artista artista) {
        if (artista.getEstilomusicalList() == null) {
            artista.setEstilomusicalList(new ArrayList<Estilomusical>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estilomusical> attachedEstilomusicalList = new ArrayList<Estilomusical>();
            for (Estilomusical estilomusicalListEstilomusicalToAttach : artista.getEstilomusicalList()) {
                estilomusicalListEstilomusicalToAttach = em.getReference(estilomusicalListEstilomusicalToAttach.getClass(), estilomusicalListEstilomusicalToAttach.getIdEstiloMusical());
                attachedEstilomusicalList.add(estilomusicalListEstilomusicalToAttach);
            }
            artista.setEstilomusicalList(attachedEstilomusicalList);
            em.persist(artista);
            for (Estilomusical estilomusicalListEstilomusical : artista.getEstilomusicalList()) {
                estilomusicalListEstilomusical.getArtistaList().add(artista);
                estilomusicalListEstilomusical = em.merge(estilomusicalListEstilomusical);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Artista artista) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Artista persistentArtista = em.find(Artista.class, artista.getIdArtista());
            List<Estilomusical> estilomusicalListOld = persistentArtista.getEstilomusicalList();
            List<Estilomusical> estilomusicalListNew = artista.getEstilomusicalList();
            List<Estilomusical> attachedEstilomusicalListNew = new ArrayList<Estilomusical>();
            for (Estilomusical estilomusicalListNewEstilomusicalToAttach : estilomusicalListNew) {
                estilomusicalListNewEstilomusicalToAttach = em.getReference(estilomusicalListNewEstilomusicalToAttach.getClass(), estilomusicalListNewEstilomusicalToAttach.getIdEstiloMusical());
                attachedEstilomusicalListNew.add(estilomusicalListNewEstilomusicalToAttach);
            }
            estilomusicalListNew = attachedEstilomusicalListNew;
            artista.setEstilomusicalList(estilomusicalListNew);
            artista = em.merge(artista);
            for (Estilomusical estilomusicalListOldEstilomusical : estilomusicalListOld) {
                if (!estilomusicalListNew.contains(estilomusicalListOldEstilomusical)) {
                    estilomusicalListOldEstilomusical.getArtistaList().remove(artista);
                    estilomusicalListOldEstilomusical = em.merge(estilomusicalListOldEstilomusical);
                }
            }
            for (Estilomusical estilomusicalListNewEstilomusical : estilomusicalListNew) {
                if (!estilomusicalListOld.contains(estilomusicalListNewEstilomusical)) {
                    estilomusicalListNewEstilomusical.getArtistaList().add(artista);
                    estilomusicalListNewEstilomusical = em.merge(estilomusicalListNewEstilomusical);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = artista.getIdArtista();
                if (findArtista(id) == null) {
                    throw new NonexistentEntityException("The artista with id " + id + " no longer exists.");
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
            Artista artista;
            try {
                artista = em.getReference(Artista.class, id);
                artista.getIdArtista();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The artista with id " + id + " no longer exists.", enfe);
            }
            List<Estilomusical> estilomusicalList = artista.getEstilomusicalList();
            for (Estilomusical estilomusicalListEstilomusical : estilomusicalList) {
                estilomusicalListEstilomusical.getArtistaList().remove(artista);
                estilomusicalListEstilomusical = em.merge(estilomusicalListEstilomusical);
            }
            em.remove(artista);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Artista> findArtistaEntities() {
        return findArtistaEntities(true, -1, -1);
    }

    public List<Artista> findArtistaEntities(int maxResults, int firstResult) {
        return findArtistaEntities(false, maxResults, firstResult);
    }

    private List<Artista> findArtistaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Artista.class));
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

    public Artista findArtista(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Artista.class, id);
        } finally {
            em.close();
        }
    }

    public int getArtistaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Artista> rt = cq.from(Artista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
