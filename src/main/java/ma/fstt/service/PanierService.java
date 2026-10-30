package ma.fstt.service;

import ma.fstt.entities.Internaute;
import ma.fstt.entities.Panier;

public interface PanierService extends CrudService<Panier> {

    Panier trouverParInternaute(Long internauteId);

    Panier trouverOuCreerPour(Internaute internaute);
}