package Observer;

import modele.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConferenceObservable extends EvenementObservableImplementation {
    private String theme;
    private List<Intervenant> intervenants;

    public ConferenceObservable(String id, String nom, LocalDateTime date, String lieu,
                                int capaciteMax, String theme) {
        super(id, nom, date, lieu, capaciteMax);
        this.theme = theme;
        this.intervenants = new ArrayList<>();
    }

    @Override
    public void afficherDetails() {
        System.out.println("=== CONFÉRENCE OBSERVABLE ===");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Date: " + date);
        System.out.println("Lieu: " + lieu);
        System.out.println("Thème: " + theme);
        System.out.println("Capacité: " + participants.size() + "/" + capaciteMax);

    }

    public void ajouterIntervenant(Intervenant intervenant) {
        if (!intervenants.contains(intervenant)) {
            intervenants.add(intervenant);
            notifierObservateurs("👨‍🏫 Nouvel intervenant ajouté: " + intervenant.getNom());
        }
    }

    // Getters
    public String getTheme() { return theme; }
    public List<Intervenant> getIntervenants() { return new ArrayList<>(intervenants); }
}




