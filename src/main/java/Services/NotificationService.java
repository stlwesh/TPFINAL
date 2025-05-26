package Services;

import modele.Participant;

import java.util.List;

public interface NotificationService {
    void envoyerNotification(String message);
    void envoyerNotificationAParticipant(Participant participant, String message);
    void envoyerNotificationGroupe(List<Participant> participants, String message);
}
