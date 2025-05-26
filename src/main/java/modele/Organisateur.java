package modele;
import java.util.ArrayList;
import java.util.List;
public class Organisateur extends Participant{
    private List<Evenement> evenementsOrganises;

    public Organisateur(String id, String nom, String email) {
        super(id, nom, email);
        this.evenementsOrganises = new ArrayList<>();
    }

    public void ajouterEvenement(Evenement evenement) {
        if (!evenementsOrganises.contains(evenement)) {
            evenementsOrganises.add(evenement);
        }
    }

    public void retirerEvenement(Evenement evenement) {
        evenementsOrganises.remove(evenement);
    }

    public List<Evenement> getEvenementsOrganises() {
        return new ArrayList<>(evenementsOrganises);
    }

    public int getNombreEvenementsOrganises() {
        return evenementsOrganises.size();
    }

    @Override
    public String toString() {
        return "Organisateur{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", nbEvenements=" + evenementsOrganises.size() +
                '}';
    }

}
