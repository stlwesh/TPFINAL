package Observer;

import java.time.LocalDateTime;

public abstract class ConcertObservable  extends EvenementObservableImplementation {
    private String artiste;
    private String genreMusical;

    public ConcertObservable(String id, String nom, LocalDateTime date, String lieu,
                             int capaciteMax, String artiste, String genreMusical) {
        super(id, nom, date, lieu, capaciteMax);
        this.artiste = artiste;
        this.genreMusical = genreMusical;
    }

    @Override
    public void afficherDetails() {
        System.out.println("=== CONCERT OBSERVABLE ===");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Date: " + date);
        System.out.println("Lieu: " + lieu);
        System.out.println("Artiste: " + artiste);
        System.out.println("Genre: " + genreMusical);
        System.out.println("Participants: " + participants.size() + "/" + capaciteMax);
    }

    // Getters
    public String getArtiste() { return artiste; }
    public String getGenreMusical() { return genreMusical; }
}

