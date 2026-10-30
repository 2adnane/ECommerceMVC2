package ma.fstt.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "panier")
public class Panier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @OneToOne
    @JoinColumn(name = "internaute_id", nullable = false, unique = true)
    private Internaute internaute;

    @OneToMany(mappedBy = "panier",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<LignePanier> lignes = new ArrayList<>();

    public Panier() {
        this.dateCreation = LocalDateTime.now();
    }

    public void ajouterLigne(LignePanier ligne) {
        lignes.add(ligne);
        ligne.setPanier(this);
    }

    public void retirerLigne(LignePanier ligne) {
        lignes.remove(ligne);
        ligne.setPanier(null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Internaute getInternaute() { return internaute; }
    public void setInternaute(Internaute internaute) {
        this.internaute = internaute;
    }

    public List<LignePanier> getLignes() { return lignes; }
    public void setLignes(List<LignePanier> lignes) {
        this.lignes = lignes;
    }
}