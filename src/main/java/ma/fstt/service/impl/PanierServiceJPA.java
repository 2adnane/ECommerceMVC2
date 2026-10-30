package ma.fstt.service.impl;
import ma.fstt.entities.Internaute;
import ma.fstt.entities.Panier;
import ma.fstt.service.PanierService;
import ma.fstt.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PanierServiceJPA implements PanierService {



    @Override
    public Panier ajouter(Panier panier) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(panier);
            tx.commit();
            return panier;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Panier trouverParId(Long id) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            return em.find(Panier.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Panier> trouverTous() {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT DISTINCT p FROM Panier p ORDER BY p.id",
                    Panier.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Panier trouverOuCreerPour(Internaute internaute) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            List<Panier> resultats = em.createQuery(
                            "SELECT p FROM Panier p WHERE p.internaute.id = :id",
                            Panier.class
                    ).setParameter("id", internaute.getId())
                    .getResultList();

            Panier panier;

            if (resultats.isEmpty()) {
                Internaute internauteGere = em.find(Internaute.class, internaute.getId());
                panier = new Panier();
                panier.setInternaute(internauteGere);
                em.persist(panier);
            } else {
                panier = resultats.get(0);
            }

            tx.commit();
            return panier;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Panier modifier(Panier panier) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Panier resultat = em.merge(panier);
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
            Panier panier = em.find(Panier.class, id);
            if (panier != null) em.remove(panier);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Panier trouverParInternaute(Long internauteId) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            List<Panier> resultats = em.createQuery(
                            "SELECT p FROM Panier p WHERE p.internaute.id = :id",
                            Panier.class
                    )
                    .setParameter("id", internauteId)
                    .getResultList();

            return resultats.isEmpty() ? null : resultats.get(0);
        } finally {
            em.close();
        }
    }
}