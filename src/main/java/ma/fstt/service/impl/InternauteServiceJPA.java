package ma.fstt.service.impl;

import ma.fstt.entities.Internaute;
import ma.fstt.service.InternauteService;
import ma.fstt.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class InternauteServiceJPA implements InternauteService {

    @Override
    public Internaute ajouter(Internaute internaute) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(internaute);
            tx.commit();
            return internaute;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Internaute trouverParId(Long id) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            return em.find(Internaute.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Internaute> trouverTous() {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT i FROM Internaute i ORDER BY i.id",
                    Internaute.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Internaute modifier(Internaute internaute) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Internaute resultat = em.merge(internaute);
            tx.commit();
            return resultat;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void supprimer(Long id) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Internaute internaute = em.find(Internaute.class, id);
            if (internaute != null) em.remove(internaute);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Internaute trouverParEmail(String email) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            List<Internaute> resultats = em.createQuery(
                            "SELECT i FROM Internaute i WHERE i.email = :email",
                            Internaute.class
                    )
                    .setParameter("email", email)
                    .getResultList();

            return resultats.isEmpty() ? null : resultats.get(0);
        } finally {
            em.close();
        }
    }
}