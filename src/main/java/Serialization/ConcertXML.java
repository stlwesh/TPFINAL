package Serialization;
import jakarta.xml.bind.annotation.*;

import java.time.LocalDateTime;

@XmlRootElement(name = "concert")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConcertXML extends EvenementXML {

    @XmlElement
    private String artiste;

    @XmlElement
    private String genreMusical;

    public ConcertXML() {}

    public ConcertXML(String id, String nom, LocalDateTime date, String lieu,
                      int capaciteMax, String artiste, String genreMusical) {
        super(id, nom, date, lieu, capaciteMax);
        this.artiste = artiste;
        this.genreMusical = genreMusical;
    }

    public String getArtiste() { return artiste; }
    public void setArtiste(String artiste) { this.artiste = artiste; }

    public String getGenreMusical() { return genreMusical; }
    public void setGenreMusical(String genreMusical) { this.genreMusical = genreMusical; }
}
