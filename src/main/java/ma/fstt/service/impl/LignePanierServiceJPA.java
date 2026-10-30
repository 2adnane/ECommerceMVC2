package ma.fstt.service.impl;

import ma.fstt.entities.LignePanier;
import ma.fstt.service.LignePanierService;
import ma.fstt.util.JPAUtil;
import ma.fstt.entities.Panier;
import ma.fstt.entities.Produit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class LignePanierServiceJPA implements LignePanierService {

    @Override
    public LignePanier ajouter(LignePanier ligne) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(ligne);
            tx.commit();
            return ligne;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public LignePanier trouverParId(Long id) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            return em.find(LignePanier.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<LignePanier> trouverTous() {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT l FROM LignePanier l ORDER BY l.id",
                    LignePanier.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public LignePanier modifier(LignePanier ligne) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            LignePanier resultat = em.merge(ligne);
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
            LignePanier ligne = em.find(LignePanier.class, id);
            if (ligne != null) em.remove(ligne);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void ajouterProduit(Long panierId, Long produitId, int quantite) {
        if (quantite < 1) {
            throw new IllegalArgumentException("La quantité doit être au moins 1.");
        }

        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Panier panier = em.find(Panier.class, panierId);
            Produit produit = em.find(Produit.class, produitId);

            if (panier == null || produit == null) {
                throw new IllegalArgumentException("Panier ou produit introuvable.");
            }
            if (produit.getStock() < quantite) {
                throw new IllegalArgumentException("Stock insuffisant.");
            }

            List<LignePanier> lignes = em.createQuery(
                            "SELECT l FROM LignePanier l " +
                                    "WHERE l.panier.id = :panierId AND l.produit.id = :produitId",
                            LignePanier.class
                    ).setParameter("panierId", panierId)
                    .setParameter("produitId", produitId)
                    .getResultList();

            if (lignes.isEmpty()) {
                LignePanier ligne =
                        new LignePanier(produit, quantite, produit.getPrix());
                ligne.setPanier(panier);
                em.persist(ligne);
            } else {
                LignePanier ligne = lignes.get(0);
                int nouvelleQuantite = ligne.getQuantite() + quantite;
                if (produit.getStock() < nouvelleQuantite) {
                    throw new IllegalArgumentException("Stock insuffisant.");
                }
                ligne.setQuantite(nouvelleQuantite);
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void changerQuantite(Long ligneId, int quantite) {
        if (quantite < 1) {
            throw new IllegalArgumentException("La quantité doit être au moins 1.");
        }

        EntityManager em = JPAUtil.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            LignePanier ligne = em.find(LignePanier.class, ligneId);
            if (ligne == null) {
                throw new IllegalArgumentException("Ligne de panier introuvable.");
            }
            if (ligne.getProduit().getStock() < quantite) {
                throw new IllegalArgumentException("Stock insuffisant.");
            }

            ligne.setQuantite(quantite);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<LignePanier> trouverParPanier(Long panierId) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            return em.createQuery(
                            "SELECT l FROM LignePanier l WHERE l.panier.id = :panierId",
                            LignePanier.class
                    )
                    .setParameter("panierId", panierId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}