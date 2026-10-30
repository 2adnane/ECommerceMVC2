package ma.fstt.service;

import ma.fstt.entities.LignePanier;
import java.util.List;

public interface LignePanierService extends CrudService<LignePanier> {
    List<LignePanier> trouverParPanier(Long panierId);
    void ajouterProduit(Long panierId, Long produitId, int quantite);
    void changerQuantite(Long ligneId, int quantite);
}