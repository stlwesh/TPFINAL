package Observer;

public interface EvenementObservable {
    void ajouterObservateur(ParticipantObservateur observer);
    void retirerObservateur(ParticipantObservateur observer);
    void notifierObservateurs(String message);
}

