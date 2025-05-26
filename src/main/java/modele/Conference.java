package modele;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalTime;
import java.util.List;
public class Conference extends Evenement {private String theme;
    private List<Intervenant> intervenants;

    public Conference(String id, String nom, LocalDateTime date, String lieu,
                      int capaciteMax, String theme) {
        super(id, nom, date, lieu, capaciteMax);
        this.theme = theme;
        this.intervenants = new ArrayList<>();
    }

    @Override
    public void afficherDetails() {
        System.out.println("=== CONFÉRENCE ===");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Date: " + date);
        System.out.println("Lieu: " + lieu);
        System.out.println("Thème: " + theme);
        System.out.println("Capacité: " + participants.size() + "/" + capaciteMax);
        System.out.println("Intervenants: ");
        intervenants.forEach(i -> System.out.println("  - " + i.getNom()));
    }

    public void ajouterIntervenant(Intervenant intervenant) {
        if (!intervenants.contains(intervenant)) {
            intervenants.add(intervenant);
        }
    }

    public void retirerIntervenant(Intervenant intervenant) {
        intervenants.remove(intervenant);
    }

    // Getters et Setters
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    public List<Intervenant> getIntervenants() { return new ArrayList<>(intervenants); }

}
