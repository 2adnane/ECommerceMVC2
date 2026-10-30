package ma.fstt.service;

import java.util.List;

public interface CrudService<T> {
    T ajouter(T objet);
    T trouverParId(Long id);
    List<T> trouverTous();
    T modifier(T objet);
    void supprimer(Long id);
}