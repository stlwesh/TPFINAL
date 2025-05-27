package Factory;

import Observer.ConcertObservable;
import Observer.ConferenceObservable;
import Observer.EvenementObservable;
import modele.Concert;
import modele.Conference;
import modele.Evenement;
import Observer.*;
import java.time.LocalDateTime;

public class EvenementFactory{
public enum TypeEvenement {
    CONFERENCE, CONCERT
}

/**
 * Créer un événement selon le type spécifié
 */
public static Evenement creerEvenement(TypeEvenement type, String id, String nom,
                                       LocalDateTime date, String lieu, int capaciteMax,
                                       Object... parametresSpecifiques) {
    switch (type) {
        case CONFERENCE:
            String theme = (String) parametresSpecifiques[0];
            return new Conference(id, nom, date, lieu, capaciteMax, theme);

        case CONCERT:
            String artiste = (String) parametresSpecifiques[0];
            String genre = (String) parametresSpecifiques[1];
            return new Concert(id, nom, date, lieu, capaciteMax, artiste, genre);

        default:
            throw new IllegalArgumentException("Type d'événement non supporté: " + type);
    }
}

/**
 * Créer un événement observable selon le type spécifié
 */
public static EvenementObservable creerEvenementObservable(TypeEvenement type, String id, String nom,
                                                           LocalDateTime date, String lieu, int capaciteMax,
                                                           Object... parametresSpecifiques) {
    switch (type) {
        case CONFERENCE:
            String theme = (String) parametresSpecifiques[0];
            return new ConferenceObservable(id, nom, date, lieu, capaciteMax, theme);

        case CONCERT:
            String artiste = (String) parametresSpecifiques[0];
            String genre = (String) parametresSpecifiques[1];
            return new ConcertObservable(id, nom, date, lieu, capaciteMax, artiste, genre);

        default:
            throw new IllegalArgumentException("Type d'événement observable non supporté: " + type);
    }
}}
