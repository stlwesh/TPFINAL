package Serialization;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement(name = "evenement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ConferenceXML.class, ConcertXML.class})
public abstract class EvenementXML {

    @XmlAttribute
    protected String id;

    @XmlElement
    protected String nom;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    protected LocalDateTime date;

    @XmlElement
    protected String lieu;

    @XmlElement
    protected int capaciteMax;

    @XmlElementWrapper(name = "participants")
    @XmlElement(name = "participant")
    protected List<ParticipantXML> participants;

    // Constructeurs
    public EvenementXML() {}

    public EvenementXML(String id, String nom, LocalDateTime date, String lieu, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        this.participants = new ArrayList<>();
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public int getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }

    public List<ParticipantXML> getParticipants() { return participants; }
    public void setParticipants(List<ParticipantXML> participants) { this.participants = participants; }
}
