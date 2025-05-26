package modele;

import java.util.Objects;

public class Intervenant {private String id;
    private String nom;
    private String specialite;
    private String biographie;

    public Intervenant(String id, String nom, String specialite, String biographie) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
        this.biographie = biographie;
    }

    // Getters et Setters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getSpecialite() { return specialite; }
    public String getBiographie() { return biographie; }

    public void setNom(String nom) { this.nom = nom; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
    public void setBiographie(String biographie) { this.biographie = biographie; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Intervenant that = (Intervenant) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
