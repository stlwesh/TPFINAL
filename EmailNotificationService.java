package Services;

import modele.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EmailNotificationService implements NotificationService{
    @Override
    public void envoyerNotification(String message) {
        System.out.println("📧 Notification générale: " + message);
    }

    @Override
    public void envoyerNotificationAParticipant(Participant participant, String message) {
        System.out.printf("📧 Email envoyé à %s (%s): %s%n",
                participant.getNom(), participant.getEmail(), message);
    }

    @Override
    public void envoyerNotificationGroupe(List<Participant> participants, String message) {
        participants.forEach(participant ->
                envoyerNotificationAParticipant(participant, message));
    }

    /**
     * Envoi asynchrone de notification (bonus)
     */
    public CompletableFuture<Void> envoyerNotificationAsync(Participant participant, String message) {
        return CompletableFuture.runAsync(() -> {
            try {
                // Simulation d'un délai d'envoi
                Thread.sleep(1000);
                envoyerNotificationAParticipant(participant, message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Erreur lors de l'envoi asynchrone: " + e.getMessage());
            }
        });
    }
}
