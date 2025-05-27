package modele;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import Exception.CapaciteMaxAtteinteException;

public abstract class Evenement {

    protected String id;
    protected String nom;
    protected LocalDateTime date;
    protected String lieu;
    protected int capaciteMax;
    protected List<Participant> participants;

    //constructeurs
    public Evenement(String id, String nom, LocalDateTime date,String lieu,int capaciteMax){
        this.id=id;
        this.date=date;
        this.lieu=lieu;
        this.capaciteMax=capaciteMax;
        this.nom=nom;
        this.participants=new ArrayList<>() ;

    }
    public abstract void afficherDetails();

    // Méthodes concrètes
    public boolean ajouterParticipant(Participant participant) {
        if (participants.size() >= capaciteMax) {
            throw new CapaciteMaxAtteinteException("Capacité maximale atteinte pour l'événement: " + nom);
        }

        if (!participants.contains(participant)) {
            participants.add(participant);
            return true;
        }
        return false;
    }

    public boolean retirerParticipant(Participant participant) {
        return participants.remove(participant);
    }

    public void annuler() {
        System.out.println("Événement " + nom + " annulé !");
        // Notifier tous les participants (pattern Observer sera implémenté plus tard)
    }

    // Getters et Setters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public LocalDateTime getDate() { return date; }
    public String getLieu() { return lieu; }
    public int getCapaciteMax() { return capaciteMax; }
    public List<Participant> getParticipants() { return new ArrayList<>(participants); }
    public int getNombreParticipants() { return participants.size(); }

    public void setNom(String nom) { this.nom = nom; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Evenement evenement = (Evenement) obj;
        return Objects.equals(id, evenement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

