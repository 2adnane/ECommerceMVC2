package ma.fstt.service;

import ma.fstt.entities.Internaute;

public interface InternauteService extends CrudService<Internaute> {
    Internaute trouverParEmail(String email);
}