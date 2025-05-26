package Services;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import modele.*;
import Exception.EvenementDejaExistantException;

public class GestionEvenement {
    private static GestionEvenement instance;
    private Map<String, Evenement> evenements;
    private NotificationService notificationService;

    // Constructeur privé pour le singleton
    private GestionEvenement() {
        this.evenements = new HashMap<>();
        this.notificationService = new EmailNotificationService();
    }

    // Méthode thread-safe pour obtenir l'instance unique
    public static synchronized GestionEvenement getInstance() {
        if (instance == null) {
            instance = new GestionEvenement();
        }
        return instance;
    }

    /**
     * Ajouter un événement
     */
    public void ajouterEvenement(Evenement evenement) {
        if (evenements.containsKey(evenement.getId())) {
            throw new EvenementDejaExistantException(
                    "Un événement avec l'ID " + evenement.getId() + " existe déjà");
        }
        evenements.put(evenement.getId(), evenement);
        System.out.println(" Événement ajouté: " + evenement.getNom());
    }

    /**
     * Supprimer un événement
     */
    public boolean supprimerEvenement(String id) {
        Evenement evenement = evenements.remove(id);
        if (evenement != null) {
            // Notifier les participants de l'annulation
            notificationService.envoyerNotificationGroupe(
                    evenement.getParticipants(),
                    "L'événement '" + evenement.getNom() + "' a été annulé."
            );
            System.out.println("🗑 Événement supprimé: " + evenement.getNom());
            return true;
        }
        return false;
    }

    /**
     * Rechercher un événement par ID
     */
    public Evenement rechercherEvenement(String id) {
        return evenements.get(id);
    }

    /**
     * Rechercher des événements par nom (avec Streams)
     */
    public List<Evenement> rechercherParNom(String nom) {
        return evenements.values().stream()
                .filter(e -> e.getNom().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Rechercher des événements par lieu
     */
    public List<Evenement> rechercherParLieu(String lieu) {
        return evenements.values().stream()
                .filter(e -> e.getLieu().toLowerCase().contains(lieu.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Obtenir tous les événements
     */
    public List<Evenement> obtenirTousLesEvenements() {
        return new ArrayList<>(evenements.values());
    }

    /**
     * Obtenir les événements par type
     */
    public List<Concert> obtenirConcerts() {
        return evenements.values().stream()
                .filter(e -> e instanceof Concert)
                .map(e -> (Concert) e)
                .collect(Collectors.toList());
    }

    public List<Conference> obtenirConferences() {
        return evenements.values().stream()
                .filter(e -> e instanceof Conference)
                .map(e -> (Conference) e)
                .collect(Collectors.toList());
    }

    /**
     * Statistiques des événements
     */
    public void afficherStatistiques() {
        System.out.println("=== STATISTIQUES ===");
        System.out.println("Nombre total d'événements: " + evenements.size());
        System.out.println("Concerts: " + obtenirConcerts().size());
        System.out.println("Conférences: " + obtenirConferences().size());

        int totalParticipants = evenements.values().stream()
                .mapToInt(Evenement::getNombreParticipants)
                .sum();
        System.out.println("Total participants: " + totalParticipants);
    }

    // Setter pour le service de notification (injection de dépendance)
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

}
