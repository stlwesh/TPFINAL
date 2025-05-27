package Observer;

import modele.Evenement;

public interface ParticipantObservateur {
    void mettreAJour(String message, Evenement evenement);
    String getId();
}
