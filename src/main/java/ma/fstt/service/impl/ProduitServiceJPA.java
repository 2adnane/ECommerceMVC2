package ma.fstt.service.impl;

import ma.fstt.entities.Produit;
import ma.fstt.service.ProduitService;
import ma.fstt.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ProduitServiceJPA implements ProduitService {

    @Override
    public Produit ajouter(Produit produit) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(produit);
            tx.commit();
            return produit;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Produit trouverParId(Long id) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            return em.find(Produit.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Produit> trouverTous() {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Produit p ORDER BY p.id",
                    Produit.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Produit modifier(Produit produit) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Produit resultat = em.merge(produit);
            tx.commit();
            return resultat;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
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
            Produit produit = em.find(Produit.class, id);
            if (produit != null) {
                em.remove(produit);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}