package Observer;

import modele.Evenement;
import modele.Participant;

public class ParticipantObservateurImplementation extends Participant implements ParticipantObservateur {

    public ParticipantObservateurImplementation(String id, String nom, String email) {
        super(id, nom, email);
    }

    @Override
    public void mettreAJour (String message, Evenement evenement){
        System.out.printf(" Notification pour %s: %s%n", this.nom, message);
        // Ici on pourrait ajouter d'autres actions comme envoyer un email, sauvegarder en base, etc.
    }
}
